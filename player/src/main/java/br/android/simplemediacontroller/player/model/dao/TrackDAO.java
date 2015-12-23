package br.android.simplemediacontroller.player.model.dao;

import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import br.android.simplemediacontroller.player.model.Track;

/**
 * Created by diogojayme on 12/17/15.
 */
public class TrackDAO extends Track {
    public static final String TAG = "DEBUG";

    public static void saveAndReplace(List<Track> tracks){
        deleteAll();

        for (Track track : tracks) {
            long id = track.save();
            Log.d(TAG, id > 0 ? "SONG ADDED" : "NO SONGS ADDED");
        }
    }

    public static List<Track> getSongs(){
        List<Track> tracks = new Select().from(Track.class).execute();
        Log.d(TAG, tracks != null ? "HIT SONGS" : "NO SONGS FOUND");
        return tracks;
    }

    public static Track getSong(long id){
        Track track = new Select().from(Track.class).where(Track.COLUMN_ID + " = ", id).executeSingle();
        Log.d(TAG, track != null ? "HIT SONG" : "NO SONG FOUND");
        return track;
    }

    public static void deleteAll(){
        new Delete().from(Track.class).execute();//delete all before insert (keep 1 row)
    }

}
