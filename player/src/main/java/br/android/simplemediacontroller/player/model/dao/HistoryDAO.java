package br.android.simplemediacontroller.player.model.dao;

import android.util.Log;

import com.activeandroid.query.Select;

import br.android.simplemediacontroller.player.model.History;
import br.android.simplemediacontroller.player.model.Track;

/**
 * Created by diogojayme on 12/17/15.
 */
public class HistoryDAO extends History {

    public static final String TAG = "DEBUG";

    public static void saveHistory(History history){
        long id = history.save();
        Log.d(TAG, id > 0 ? "HISTORY ADDED" : "NO HISTORY ADDED");
    }

    public static History getLastHistory(){
        History history = new Select().from(History.class).orderBy("id").executeSingle();
        Log.d(TAG, history != null ? "HIT HISTORY" : "NO HISTORY FOUNDED");
        return history;
    }

    public static Track getLastPlayedSong(){
        Track track = new Select().from(History.class).innerJoin(Track.class).on("History.song_id = Song.song_id").orderBy("History.id").executeSingle();
        Log.d(TAG, track != null ? "HIT SONG" : "NO SONG FOUNDED");
        return track;
    }
}
