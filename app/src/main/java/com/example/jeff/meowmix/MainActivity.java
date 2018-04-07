package com.example.jeff.meowmix;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Song> songList;

    // Check for required permissions dynamically because newer APIs can deny access anytime
    private final static String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private final static int REQUEST_CODE_PERMISSION = 1;
    ArrayList<String> missingPermissions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          checkPermissions();
          requestPermissions();

          getSongList();
          
          // avoid NullPointerExceptions for phones with no songs
          if(songList != null)
            alphabetizeSong(songList);
    }

    /** Checks to see what permissions the app has access to
     * 
     */
    protected void checkPermissions() {
        // See which permissions the user hasn't accepted yet. Should add reasoning to each
        // permission as well
        for(String permission : REQUIRED_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);

            if(result != PackageManager.PERMISSION_GRANTED)
                missingPermissions.add(permission);
            }
    }

    /** If the app doesn't have certain permissions, ask for them
     *  @TODO: 4/6/2018 provide reasoning for each permission 
     */
    protected void requestPermissions() {
        // We're missing some permissions. Request them
        if(!missingPermissions.isEmpty()) {
            String[] permissionArray = new String[missingPermissions.size()];
            permissionArray = missingPermissions.toArray(permissionArray);

            ActivityCompat.requestPermissions(this, permissionArray,
                    REQUEST_CODE_PERMISSION);
        }
    }

    /** Query the device's external storage for any sound files labeled as music and store them 
     * into an arrayList.
     * 
     */
    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();

        // specify we want music store on the external SD card
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] proj = { MediaStore.Audio.Media._ID,
                          MediaStore.Audio.Media.ALBUM,
                          MediaStore.Audio.Media.ARTIST,
                          MediaStore.Audio.Media.IS_MUSIC,
                          MediaStore.Audio.Media.TITLE};

        // make a query to the device and grab the necessary information
        Cursor musicCursor = musicResolver.query(musicUri, proj, null, null, 
                null);

        // question for later: what's the point of proj above if we have to type all the
        // information again below?

        // a little checking to make sure the app doesn't take any illegal actions
        if(musicCursor != null && musicCursor.moveToFirst()) {
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int isMusicColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            // iterate through each song in the cursor and grab the necessary information
            do {
                long songId = musicCursor.getLong(idColumn);
                String songAlbum = musicCursor.getString(albumColumn);
                String songArtist = musicCursor.getString(artistColumn);
                int songIsMusic = musicCursor.getInt(isMusicColumn);
                String songTitle = musicCursor.getString(titleColumn);

                // dont add any sound files that aren't considered music
                if(songIsMusic == 1) {
                    songList.add(new Song(songAlbum, songArtist, songId, songTitle));
                }
            }
            while (musicCursor.moveToNext());
        }

        // ensure the cursor will close
        try { }
        finally { musicCursor.close(); }
    }

    /** Alphabetizes arrayLists containing Song objects
     * @param mediaList an arrayList containing Song objects to alphabetize
     */
    public void alphabetizeSong(ArrayList<Song> mediaList) {
        // assume we alphabetize based on media file title
        Collections.sort(mediaList, new Comparator<Song>() {
            @Override
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }
}
