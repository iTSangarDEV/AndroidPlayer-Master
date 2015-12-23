package br.android.simplemediacontroller.player.utils;

import java.util.List;

import br.android.simplemediacontroller.player.model.Album;
import br.android.simplemediacontroller.player.model.Artist;
import br.android.simplemediacontroller.player.model.Track;

/**
 * Created by diogojayme on 12/17/15.
 */
public class Validation {

    public static void assertNull(List<Track> tracks){
        if(tracks == null) throw  new NullPointerException("List<Song> is null");

        for (Track track : tracks) {
            assertNull(track);
        }

    }

    public static void assertNull(Track track){
        if(track == null) throw new NullPointerException("Song is null");
    }

    public static void assertNull(Artist artist){
        if(artist == null) throw new NullPointerException("Artist is null");
    }

    public static void assertNull(Album album){
        if(album == null) throw new NullPointerException("Album is null");
    }
}
