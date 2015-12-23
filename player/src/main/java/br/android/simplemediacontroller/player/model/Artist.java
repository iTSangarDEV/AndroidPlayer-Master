package br.android.simplemediacontroller.player.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by diogojayme on 12/17/15.
 */
@Table(name = "artist")
public class Artist extends Model{

    public static final String COLUMN_ID = "artist_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";

    /***
     * Constructor
     */
    public Artist(){}

    @Column(name = COLUMN_ID, unique = true)
    public long idArtist;

    @Column(name = COLUMN_NAME)
    public String name;

    @Column(name = COLUMN_IMAGE)
    public String image;

    public long getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(long idArtist) {
        this.idArtist = idArtist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
