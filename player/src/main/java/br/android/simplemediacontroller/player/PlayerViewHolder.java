package br.android.simplemediacontroller.player;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import br.android.simplemediacontroller.R;
import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.player.listeners.OnMediaChangedListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaErrorListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaLoadingListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaProgressListener;
import br.android.simplemediacontroller.player.utils.TimerConverter;
import br.android.simplemediacontroller.player.widgets.PlayerController;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by diogojayme on 12/18/15.
 */
public class PlayerViewHolder implements
        OnMediaProgressListener,
        OnMediaErrorListener,
        OnMediaChangedListener,
        SeekBar.OnSeekBarChangeListener, OnMediaLoadingListener {

    View view;
    @Bind(R.id.song) TextView song;
    @Bind(R.id.album) TextView album;
    @Bind(R.id.artist) TextView artist;
    @Bind(R.id.my_custom_seek_bar) SeekBar seekBar;
    @Bind(R.id.duration) TextView duration;
    @Bind(R.id.progress) TextView progress;
    @Bind(R.id.player_loading) ProgressBar loading;

    PlayerController playerController;

    public PlayerViewHolder(View view) {
        ButterKnife.bind(this, view);
        setup(view.getContext());
        this.view = view;
        seekBar.setOnSeekBarChangeListener(this);
    }

    private void setup(Context context){
        playerController = new PlayerController();
        playerController.addOnMediaChangedListener(this);
        playerController.addOnProgressListener(this);
        playerController.addOnMediaErrorListener(this);
        playerController.addOnLoadingListener(this);
        playerController.connect(context);
    }

    public void setMediaStore(MediaStore mediaStore){
        playerController.setMediaStore(mediaStore);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.player_notification_control_next) public void onNextClick(View view){
        playerController.next();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.player_notification_control_play) public void onPlayPauseClick(View view){
        playerController.toggle();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.player_notification_control_previous) public void onPreviousClick(View view){
        playerController.previous();
    }

    @Override
    public void onProgress(int i) {
        seekBar.setProgress(i);
        progress.setText(TimerConverter.progressToTime(i));
    }

    @Override
    public void onDuration(int i) {
        seekBar.setMax(i);
        duration.setText(TimerConverter.progressToTime(i));
    }

    @Override
    public void onMediaError(String error) {

    }

    @Override
    public void onMediaChanged(MediaStore mediaStore, int songPosition, boolean playing) {
        album.setText(mediaStore.getAlbum().getName());
        artist.setText(mediaStore.getArtist().getName());
        song.setText(mediaStore.getTracks().get(songPosition).getName());
        ((ImageView) view.findViewById(R.id.player_notification_control_play)).setImageResource(playing ? R.drawable.ic_notifplayer_pause : R.drawable.ic_play_circle_filled_white_48dp);
        //update your view when player was changed their state
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser){
            seekBar.setProgress(progress);
            playerController.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onMediaLoading(boolean b) {
        loading.setVisibility(b ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.player_notification_control_play).setVisibility(b ? View.GONE : View.VISIBLE);

        seekBar.setEnabled(!b);
        view.findViewById(R.id.player_notification_control_next).setEnabled(!b);
        view.findViewById(R.id.player_notification_control_previous).setEnabled(!b);
    }
}

