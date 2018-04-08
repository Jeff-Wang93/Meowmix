package com.example.jeff.meowmix;

public class Album {
    private String art;
    private String artist;
    private String count;
    private String title;

    public Album(String albumArt, String albumArtist, String albumCount, String albumTitle) {
        art = albumArt;
        artist = albumArtist;
        count = albumCount;
        title = albumTitle;
    }

    public String getArt() {
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
