package br.android.simplemediacontroller.player.model.dao;

import android.util.Log;

import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import br.android.simplemediacontroller.player.model.Artist;

/**
 * Created by diogojayme on 12/17/15.
 */

@Table(name = "artist")
public class ArtistDAO extends Artist {
    public static final String TAG = "DEBUG";

    public static void saveAndReplace(Artist artist){
        deleteAll();
        long id = artist.save();
        Log.d(TAG, id > 0 ? "ARTIST ADDED" : "ARTIST NOT ADDED");
    }

    public static Artist getArtist(){
        Artist artist =   new Select().from(Artist.class).executeSingle();
        Log.d(TAG, artist != null ? "HIT ARTIST" : "NO ARTIST FOUNDED");
        return artist;
    }

    public static void deleteAll(){
        new Delete().from(Artist.class).execute();//delete all before insert (keep 1 row)
    }
}
