package com.example.jeff.meowmix;

/** Basically the metadata of a song
 *
 */
public class Song {
    private String album;
    private String artist;
    private long id;
    private String title;

    public Song(String songAlbum, String songArtist, long songId, String songTitle) {
        album = songAlbum;
        artist = songArtist;
        id = songId;
        title = songTitle;
    }

    // getter methods
    public String getAlbum() { return album; }
    public String getArtist() { return artist; }
    public long getId() { return id; }
    public String getTitle() { return title; }
}
