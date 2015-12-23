package br.android.simplemediacontroller.player.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by diogojayme on 12/17/15.
 */
@Table(name = "song")
public  class Track extends Model {

    public static final String  COLUMN_URL = "url";
    public static final String  COLUMN_NAME = "name";
    public static final String  COLUMN_PATH = "path";
    public static final String  COLUMN_ID = "song_id";
    public static final String  COLUMN_ENCRYPTED = "encrypted";
    public static final String  COLUMN_ARTIST_ID = "artist_id";
    public static final String  COLUMN_ALBUM_ID = "album_id";
    public static final String  COLUMN_POSITION = "position";

    public static final String EXTENSION = "mp3";
    /***
     * Constructor
     */
    public Track(){}

    @Column(name = COLUMN_ID, unique = true)
    private long idSong;

    @Column(name = COLUMN_NAME)
    private String name;

    //stream path
    @Column(name = COLUMN_URL)
    private String url;

    //local path
    @Column(name = COLUMN_PATH)
    private String path;

    //the file is saved encrypted
    @Column(name = COLUMN_ENCRYPTED)
    private boolean encrypted;

    @Column(name = COLUMN_ARTIST_ID)
    private long artistId;

    @Column(name = COLUMN_ALBUM_ID)
    private long albumId;

    @Column(name = COLUMN_POSITION)
    private int position;

    /***
     * getters and setters
     */

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getIdSong() {
        return idSong;
    }

    public void setIdSong(long idSong) {
        this.idSong = idSong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEncrypted() {
        return encrypted;
    }

    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }

    public long getArtistId() {
        return artistId;
    }

    public void setArtistId(long artistId) {
        this.artistId = artistId;
    }
}
