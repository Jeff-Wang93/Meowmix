package com.example.jeff.meowmix;

import android.graphics.Bitmap;

public class Album {
    private Bitmap art;
    private String artist;
    private String count;
    private String title;

    public Album(Bitmap albumArt, String albumArtist, String albumCount, String albumTitle) {
        art = albumArt;
        artist = albumArtist;
        count = albumCount;
        title = albumTitle;
    }

    public Bitmap getArt() {
        return art;
    }

    public String getArtist() {
        return artist;
    }

    public String getCount() {
        return count;
    }

    public String getTitle() {
        return title;
    }
}
