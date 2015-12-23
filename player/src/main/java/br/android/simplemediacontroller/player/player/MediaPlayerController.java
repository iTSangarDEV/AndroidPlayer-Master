package br.android.simplemediacontroller.player.player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.webkit.URLUtil;

import java.io.File;
import java.io.IOException;

import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.model.Track;
import br.android.simplemediacontroller.player.player.interfaces.IPlayerController;
import br.android.simplemediacontroller.player.player.listeners.OnMediaChangedListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaErrorListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaLoadingListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaProgressListener;
import br.android.simplemediacontroller.player.player.listeners.OnServiceConnectionListener;
import br.android.simplemediacontroller.player.widgets.BaseMediaPlayerController;


/**
 * Created by diogojayme on 9/18/15.
 */
public class MediaPlayerController extends BaseMediaPlayerController implements
        IPlayerController,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnSeekCompleteListener {

    int position;
    boolean error;
    Context context;
    Handler handler;
    boolean shuffle;

    MediaStore mediaStore;
    MediaPlayer mediaPlayer;
    OnMediaErrorListener mediaErrorListener;
    OnMediaLoadingListener mediaLoadingListener;
    OnMediaChangedListener mediaChangedListener;
    OnMediaProgressListener mediaProgressListener;
    OnServiceConnectionListener onServiceConnectionListener;

    final int SECOND = 1000;
    PlaybackState state = PlaybackState.IDLE;

    //-----------------------Constructor--------------------------

    public MediaPlayerController(Context context){
        this.context = context;
        this.handler = new Handler();
        this.mediaPlayer = new MediaPlayer();
        this.mediaPlayer.setOnErrorListener(this);
        this.mediaPlayer.setOnPreparedListener(this);
        this.mediaPlayer.setOnCompletionListener(this);
        this.mediaPlayer.setOnSeekCompleteListener(this);
    }

    @Override
    public void addOnServiceConnectedListener(OnServiceConnectionListener onServiceConnectionListener) {
        this.onServiceConnectionListener = onServiceConnectionListener;
    }

    @Override
    public void addOnMediaChangedListener(OnMediaChangedListener mediaChangedListener){
        this.mediaChangedListener = mediaChangedListener;
    }

    @Override
    public void addOnMediaLoadingListener(OnMediaLoadingListener loadingListener) {
        this.mediaLoadingListener = loadingListener;
    }

    @Override
    public void addOnMediaErrorListener(OnMediaErrorListener mediaErrorListener){
        this.mediaErrorListener = mediaErrorListener;
    }

    @Override
    public void addOnProgressListener(OnMediaProgressListener mediaProgressListener){
        this.mediaProgressListener = mediaProgressListener;
    }

    @Override
    public void unbindListeners(){
        this.mediaErrorListener = null;
        this.mediaChangedListener = null;
        this.mediaProgressListener = null;
    }

    @Override
    public void setMediaStore(MediaStore mediaStore){
        this.mediaStore = mediaStore;
        notifyMediaChanged();
    }

    @Override
    public MediaStore getMediaStore(){
        return mediaStore;
    }

    @Override
    public boolean isMediaPlayerPlaying(){
        return mediaPlayer.isPlaying();
    }

    @Override
    public void repeat() {
        //TODO  finish implementation
    }

    @Override
    public void shuffle(boolean shuffle) {

//        if(shuffle){
//             Collections.shuffle(shuffledList);
//        }else{
//            //disable shuffle
//        }

        this.shuffle = shuffle;
    }

    @Override
    public boolean isShuffleEnabled(){
        return shuffle;
    }

    @Override
    public void resume(){
        mediaChangedListener.onMediaChanged(mediaStore, position, mediaPlayer.isPlaying());
    }

    @Override
    public boolean canResume(long id){
        return id == mediaStore.getTracks().get(position).getId();
    }

    @Override
    public void setCurrentTrackPosition(int position){
        this.position = position;
    }

    @Override
    public int getCurrentTrackPosition(){
        return this.position;
    }

    @Override
    public void toggle(){
        if(state == PlaybackState.PLAYING){
            pause();
        }else if(state == PlaybackState.PAUSED){
            play();
        }else{
            prepare();
        }
    }

    @Override
    public void play(){
        mediaPlayer.start();
        setState(PlaybackState.PLAYING);
        handler.post(progressRunnable);
        notifyMediaChanged();
    }

    @Override
    public void pause(){
        mediaPlayer.pause();
        setState(PlaybackState.PAUSED);
        handler.removeCallbacks(progressRunnable);
        notifyMediaChanged();
    }

    @Override
    public void next(){
        if(position == (mediaStore.getTracks().size() - 1)){
            this.position = 0;
        }else{
            this.position ++;
        }

        setState(PlaybackState.IDLE);
        prepare();
    }

    @Override
    public void previous(){
        if(position == 0){
            this.position = (mediaStore.getTracks().size() - 1);
        }else{
            this.position --;
        }

        setState(PlaybackState.IDLE);
        prepare();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void prepare(){
        notifyMediaLoading(true);
        mediaPlayer.reset();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Track track = mediaStore.getTracks().get(position);

        if(URLUtil.isValidUrl(track.getPath())) { //file path exists

            new FileReader(context, new FileReader.ReadFileCallback() {
                @Override
                public void onReadSuccess(File file) {
                    try {
                        mediaPlayer.setDataSource(file.toString());
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        mediaErrorListener.onMediaError("Invalid file::" + e.getMessage());
                    }

                    notifyMediaLoading(false);
                }

                @Override
                public void onReadFailed(String message) {

                }
            }).download(track.getPath(), track.getName(), Track.EXTENSION);

        }else if(URLUtil.isValidUrl(track.getUrl())){

            try {
                mediaPlayer.setDataSource(mediaStore.getTracks().get(position).getUrl());
                mediaPlayer.prepareAsync();
            }catch (IOException e) {
                mediaErrorListener.onMediaError("Invalid url::" + e.getMessage());
                notifyMediaLoading(false);
            }

        }else{
            throw new IllegalArgumentException("Invalid media player data source path");
        }

    }

    @Override
    public void seekTo(int position){
        mediaPlayer.seekTo(position * SECOND);
    }

    public Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {

            if(mediaPlayer.isPlaying()) {
                if(mediaProgressListener != null)
                    mediaProgressListener.onDuration(mediaPlayer.getDuration() / SECOND);

                if(mediaProgressListener != null)
                    mediaProgressListener.onProgress(mediaPlayer.getCurrentPosition() / SECOND);
            }

            handler.postDelayed(this, SECOND);
        }
    };

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(!error){
            next();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        notifyMediaLoading(false);
        play();
        error = false;
    }

    public void notifyMediaChanged(){
        if(mediaChangedListener != null)
            mediaChangedListener.onMediaChanged(mediaStore, position, mediaPlayer.isPlaying());
    }

    public void notifyMediaLoading(boolean loading){
        if(mediaLoadingListener != null)
            mediaLoadingListener.onMediaLoading(loading);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        error = true;
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        handler.removeCallbacks(progressRunnable);
        play();
    }

    /**
     * Get the current state of media my_custom_player_view
     * The state IDLE indicate when media my_custom_player_view is waiting for user action and the state that will
     * be executed is PLAYING.
     * The state PAUSED and PLAYING is when user executes a toggle button to start or stop the my_custom_player_view
     *
     * {@link MediaPlayerController.PlaybackState}
     * */
    public PlaybackState getState(){
        return state;
    }

    public void setState(PlaybackState state){
        this.state = state;
    }

    public enum PlaybackState{
        IDLE,
        PAUSED,
        PLAYING
    }

}
