package br.android.simplemediacontroller.player;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.activeandroid.ActiveAndroid;

import java.util.ArrayList;
import java.util.List;

import br.android.simplemediacontroller.R;
import br.android.simplemediacontroller.player.model.Album;
import br.android.simplemediacontroller.player.model.Artist;
import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.model.Track;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by diogojayme on 10/21/15.
 */
public class MainActivity extends Activity {
    PlayerViewHolder playerHolder;
    @Bind(R.id.container) RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        ActiveAndroid.initialize(this);
        View customView = LayoutInflater.from(this).inflate(R.layout.my_custom_player_view, null, false);
        playerHolder = new PlayerViewHolder(customView);
        MediaStore mediaStore = new MediaStore().create(getAlbum(), getArtist(), getSongs());
        playerHolder.setMediaStore(mediaStore);
        container.addView(customView);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public Album getAlbum(){
        Album album = new Album();
        album.setAlbumId(1);
        album.setName("300 days 300 nights");
        album.setImage("http://hw-img.datpiff.com/ma7f87db/Lil_Durk_300_Days_300_Nights-front.jpg");
        return album;
    }

    public Artist getArtist(){
        Artist artist = new Artist();
        artist.setIdArtist(1);
        artist.setName("Lil Durk");
        artist.setImage("http://thesource.com/wp-content/uploads/2015/06/durk.jpg");
        return artist;
    }

    public List<Track> getSongs(){
        List<Track> tracks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Track track = new Track();
            track.setIdSong(i);
            track.setAlbumId(1);
            track.setArtistId(1);
            track.setName(i + "Track ");
            track.setUrl("http://onlinekaraoke.tv/cache/files2/393607-hotline-bling-drake--1445874140.mp3");
            track.setPath("");
            track.setEncrypted(false);
            tracks.add(track);
        }

        return tracks;
    }
}
