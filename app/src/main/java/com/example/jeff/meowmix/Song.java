package com.example.jeff.meowmix;

public class Song {
    private String album;
    private String artist;
    private String genre;
    private long id;
    private String title;

    public Song(String songAlbum, String songArtist, String songGenre, long songId, String
            songTitle) {
        album = songAlbum;
        artist = songArtist;
        genre = songGenre;
        id = songId;
        title = songTitle;
    }

    // getter methods
    public String getAlbum() { return album; }
    public String getArtist() { return artist; }
    public String getGenre() { return genre; }
    public long getId() { return id; }
    public String getTitle() { return title; }
}
