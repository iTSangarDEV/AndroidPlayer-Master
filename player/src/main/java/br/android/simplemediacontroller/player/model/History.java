package br.android.simplemediacontroller.player.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

/**
 * Created by diogojayme on 12/17/15.
 */
@Table(name = "history")
public abstract class History extends Model{

    @Column(name = "album_id")
    private long albumId;

    @Column(name = "artist_id")
    private long artistId;

    @Column(name = "song_id")
    private long songId;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "artist_name")
    private String artistName;

    @Column(name = "song_name")
    private String songName;

    @Column(name = "position")
    private int position;

    @Column(name = "duration")
    private int duration;

    @Column(name = "last_day_played")
    private Date lastDate;


}
