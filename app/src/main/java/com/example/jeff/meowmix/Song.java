package com.example.jeff.meowmix;

/** Basically the metadata of a song
 *
 */
public class Song {
    private String album;
    private String artist;
    private long id;
    private String title;


    public Song() {}

    public Song(String songAlbum, String songArtist, long songId, String songTitle) {
        album = songAlbum;
        artist = songArtist;
        id = songId;
        title = songTitle;
    }

    // setter methods
    public void setAlbum(String songAlbum) { album = songAlbum; }
    public void setArtist(String songArtist) { artist = songArtist; }
    public void setId(long songId) { id = songId; }
    public void setTitle(String songTitle) { title = songTitle; }

    // getter methods
    public String getAlbum() { return album; }
    public String getArtist() { return artist; }
    public long getId() { return id; }
    public String getTitle() { return title; }
}
