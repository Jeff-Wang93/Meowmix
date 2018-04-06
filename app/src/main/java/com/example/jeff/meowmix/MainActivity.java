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
          //alphabetizeMedia(songList);
    }

    protected void checkPermissions() {
                                          // See which permissions the user hasn't accepted yet. Should add reasoning to each
                                          // permission as well
                                          for(String permission : REQUIRED_PERMISSIONS) {
                                          final int result = ContextCompat.checkSelfPermission(this, permission);

                                          if(result != PackageManager.PERMISSION_GRANTED)
                                          missingPermissions.add(permission);
                                          }
                                          }

    protected void requestPermissions() {
        // We're missing some permissions. Request them
        if(!missingPermissions.isEmpty()) {
            String[] permissionArray = new String[missingPermissions.size()];
            permissionArray = missingPermissions.toArray(permissionArray);

            ActivityCompat.requestPermissions(this, permissionArray,
                    REQUEST_CODE_PERMISSION);
        }
    }

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();

        // specify we want music store on the external SD card
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] proj = { MediaStore.Audio.Media._ID,
                          MediaStore.Audio.Media.ALBUM,
                          MediaStore.Audio.Media.ARTIST,
                          MediaStore.Audio.Media.IS_MUSIC,
                          MediaStore.Audio.Media.TITLE};

        Cursor musicCursor = musicResolver.query(musicUri,proj, null, null, null);

        // question for later: what's the point of proj above if we have to type all the
        // information again below?

        if(musicCursor != null && musicCursor.moveToFirst()) {
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int isMusicColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            // add our songs to the arrayList
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
    }

    public void alphabetizeMedia(ArrayList<Media> mediaList) {
        // assume we alphabetize based on media file title
        Collections.sort(mediaList, new Comparator<Media>() {
            @Override
            public int compare(Media a, Media b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }
}
