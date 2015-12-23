package br.android.simplemediacontroller.player.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by diogojayme on 12/17/15.
 */
@Table(name = "album")
public class Album extends Model {

    //column name
    public static final String COLUMN_ID = "album_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_ARTIST_ID = "artist_id";

    public static final String SIZE_500x500 = "500x500";
    public static final String SIZE_300x300 = "300x300";
    public static final String SIZE_250x250= "250x250";

    /***
     * Constructor
     */

    public Album(){}

    @Column(name = COLUMN_ID, unique = true)
    private long albumId;

    @Column(name = COLUMN_NAME)
    private String name;

    @Column(name = COLUMN_IMAGE)
    private String image;

    @Column(name = COLUMN_ARTIST_ID)
    private long artistId;

    /***
     * getters and setters
     */

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage(String size) {
        return image + "/" + size;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


