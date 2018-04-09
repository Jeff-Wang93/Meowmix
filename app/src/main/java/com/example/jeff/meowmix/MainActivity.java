package com.example.jeff.meowmix;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Song> songList = new ArrayList<Song>();
    private ArrayList<Album> albumList = new ArrayList<Album>();
    private GridView albumView;
    private ListView songView;

    // Check for required permissions dynamically because newer APIs can deny access anytime
    private final static String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private final static int REQUEST_CODE_PERMISSION = 1;
    ArrayList<String> missingPermissions = new ArrayList<>();

    // button variables
    Button albumButton;
    Button songButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          // set the toolbar as the app bar for activity
          Toolbar tabToolBar = (Toolbar) findViewById(R.id.main_tool_bar);
          setSupportActionBar(tabToolBar);

          checkPermissions();
          requestPermissions();

          albumButton = (Button) findViewById(R.id.album_button);
          songButton = (Button) findViewById(R.id.song_button);


          // don't try to do anything if the app doesn't have the necessary permissions
          if(missingPermissions.isEmpty()) {
              getSongList();
              getAlbumArtList();

              // avoid NullPointerExceptions for phones with no songs
              if (songList != null) {
                  alphabetizeSong(songList);
                  alphabetizeAlbum(albumList);

                  AlbumAdapter albumAdt = new AlbumAdapter(this, albumList);
                  albumView = (GridView)findViewById(R.id.show_album);
                  albumView.setAdapter(albumAdt);

                  final SongAdapter songAdt = new SongAdapter(this, songList);
                  songView = (ListView)findViewById(R.id.show_song);

                  songButton.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          songView.setAdapter(songAdt);
                      }
                  });


              }
          }
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
     *
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
     * into an arrayList. Dealing with individual songs here
     * 
     */
    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();

        // specify we want music store on the external SD card
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        // a little checking to make sure the app doesn't take any illegal actions
        if(musicCursor != null && musicCursor.moveToFirst()) {
            int idColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int titleColumn = musicCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);

            // iterate through each song_tab in the cursor and grab the necessary information
            do {
                long songId = musicCursor.getLong(idColumn);
                String songAlbum = musicCursor.getString(albumColumn);
                String songArtist = musicCursor.getString(artistColumn);
                String songTitle = musicCursor.getString(titleColumn);

                songList.add(new Song(songAlbum, songArtist, songId, songTitle));
            }
            while(musicCursor.moveToNext());
        }

        // ensure the cursor will close
        try { }
        finally { musicCursor.close(); }
    }

    /** Get the album information from the device. Very similar to getSongList. May figure out
     * way to have one function rather than 2
     *
     */
    public void getAlbumArtList() {
        ContentResolver musicResolver = getContentResolver();

        Uri albumUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        Cursor musicCursor = musicResolver.query(albumUri, null, null, null, null);

        if(musicCursor != null && musicCursor.moveToFirst()) {
            int albumColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int artistColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int albumArtColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int countColumn = musicCursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);

            // iterate through each song_tab in the cursor and grab the necessary information
            do {
                String albumTitle = musicCursor.getString(albumColumn);
                String albumArtist = musicCursor.getString(artistColumn);
                String albumArt = musicCursor.getString(albumArtColumn);
                String albumCount = musicCursor.getString(countColumn);

                Bitmap imageBm;
                imageBm = null;

                // don't allow bitmap to touch anything null
                if(albumArt != null)
                    imageBm = BitmapFactory.decodeFile(albumArt);

                albumList.add(new Album(imageBm, albumArtist, albumCount, albumTitle));
            }
            while(musicCursor.moveToNext());
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

    public void alphabetizeAlbum(ArrayList<Album> mediaList) {
        // assume we alphabetize based on media file title
        Collections.sort(mediaList, new Comparator<Album>() {
            public int compare(Album a, Album b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }

}
