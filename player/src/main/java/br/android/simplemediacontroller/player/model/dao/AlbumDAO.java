package br.android.simplemediacontroller.player.model.dao;

import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import br.android.simplemediacontroller.player.model.Album;

/**
 * Created by diogojayme on 12/17/15.
 */
public class AlbumDAO extends Album {
    public static final String TAG = "DEBUG";

    public static void saveAndReplace(Album album){
        deleteAll();
        long id = album.save();
        Log.d(TAG, id > 0 ? "ALBUM ADDED" : "ALBUM NOT ADDED");
    }

    public static Album getAlbum(){
        Album album = new Select().from(Album.class).executeSingle();
        Log.d(TAG, album != null ? "HIT ALBUM" : "NO ALBUM FOUNDED");
        return album;
    }

    public static void deleteAll(){
        new Delete().from(Album.class).execute();//delete all before insert (keep 1 row)
    }

}
