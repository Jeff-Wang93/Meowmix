package com.example.jeff.meowmix;

/** Basically the metadata of a song
 *
 */
public class Song {
    private String album;
    private String artist;
    private long id;
    private String title;


    /** Constructor for a Song object
     * @param songAlbum The song's album name
     * @param songArtist The song's artist/artists name
     * @param songId The song's Id
     * @param songTitle The song's title
     */
    public Song(String songAlbum, String songArtist, long songId, String
            songTitle) {
        album = songAlbum;
        artist = songArtist;
        id = songId;
        title = songTitle;
    }

    /** Get the song's album name
     * @return A string containing the song's album name
     */
    public String getAlbum() { return album; }

    /** Get the song's artist name
     * @return A string containing the song's artist's name
     */
    public String getArtist() { return artist; }


    /** Get the song's Id
     * @return A long representing the song's Id
     */
    public long getId() { return id; }


    /** Get the song's title
     * @return A string containing the song's title
     */
    public String getTitle() { return title; }
}
