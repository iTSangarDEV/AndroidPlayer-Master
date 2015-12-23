package br.android.simplemediacontroller.player.widgets;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.model.Track;
import br.android.simplemediacontroller.player.player.MediaPlayerController;
import br.android.simplemediacontroller.player.player.PlayerService;
import br.android.simplemediacontroller.player.player.interfaces.IService;
import br.android.simplemediacontroller.player.player.listeners.OnMediaChangedListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaErrorListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaLoadingListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaProgressListener;
import br.android.simplemediacontroller.player.player.listeners.OnServiceConnectionListener;

/**
 * Created by diogojayme on 12/18/15.
 */
public class PlayerController implements IService{

    private boolean bound;
    private PlayerService service;
    private MediaStore mediaStore;
    private MediaPlayerController mediaPlayerController;
    private OnServiceConnectionListener serviceConnectionListener;

    private OnMediaErrorListener mediaErrorListener;
    private OnMediaChangedListener mediaChangedListener;
    private OnMediaLoadingListener mediaLoadingListener;
    private OnMediaProgressListener mediaProgressListener;

    private List<Track> shuffledTracks;

    //--------------------------Constructor---------------------------------------

    public PlayerController(){}

    //--------------------------Service Connection Listener---------------------------------------

    @Override
    public void addOnServiceConnectionListener(OnServiceConnectionListener serviceConnectionListener) {
        this.serviceConnectionListener = serviceConnectionListener;
    }

    //--------------------------Media Player Controller Listeners---------------------------------------

    public void addOnMediaChangedListener(OnMediaChangedListener mediaChangedListener){
        this.mediaChangedListener = mediaChangedListener;
    }

    public void addOnMediaErrorListener(OnMediaErrorListener mediaErrorListener){
        this.mediaErrorListener = mediaErrorListener;
    }

    public void addOnProgressListener(OnMediaProgressListener mediaProgressListener){
        this.mediaProgressListener = mediaProgressListener;
    }

    public void addOnLoadingListener(OnMediaLoadingListener mediaLoadingListener){
        this.mediaLoadingListener = mediaLoadingListener;
    }

    public void unbindListeners() {
        assertService();
        service.getMediaController().unbindListeners();
    }

    //--------------------------Controllers---------------------------------------

    public void setMediaStore(MediaStore mediaStore){
        shuffledTracks = new ArrayList<>();
        this.mediaStore = mediaStore;
    }

    public MediaStore getMediaStore() {
        return this.mediaStore;
    }

    public void next(){
        assertService();
        service.getMediaController().next();
    }

    public void toggle(){
        assertService();
        service.getMediaController().toggle();
    }

    public void previous(){
        assertService();
        service.getMediaController().previous();
    }

    public void seekTo(int position){
        assertService();
        service.getMediaController().seekTo(position);
    }

    public void repeat(boolean repeat) {
        assertService();
        service.getMediaController().repeat();
    }

    public void shuffle(boolean shuffle) {
        assertService();
        service.getMediaController().shuffle(shuffle);
    }

    //--------------------------Notification---------------------------------------

    public void showNotification(Context context){
        assertService();
        service.showNotification(context);
    }


    public void removeNotification(){
        assertService();
        service.removeNotification();
    }

    //--------------------------Connection---------------------------------------

    public boolean isConnected(){
        return bound;
    }

    public void connect(Context context){
        Intent intent = new Intent(context, PlayerService.class);
        context.startService(intent);
        context.bindService(intent, playerConnection, PlayerService.BIND_AUTO_CREATE);
    }

    public  void disconnect(Context context){
        context.unbindService(playerConnection);
    }

    //---------------------------Service connection--------------------------------------

    private ServiceConnection playerConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            //ready to communicate with the player service
            //add data, bind listeners, do calls to mediaPlayerController instance
            service = ((PlayerService.PlayerBinder) iBinder).getService();
            mediaPlayerController = service.getMediaController();

            //bind the listeners for receive wherever you want (holders, activities, classes)
            mediaPlayerController.addOnMediaErrorListener(mediaErrorListener);
            mediaPlayerController.addOnProgressListener(mediaProgressListener);
            mediaPlayerController.addOnMediaChangedListener(mediaChangedListener);
            mediaPlayerController.addOnMediaLoadingListener(mediaLoadingListener);

            //add data(Album, Songs, Artists)
            mediaPlayerController.setMediaStore(mediaStore);

            if(serviceConnectionListener != null) {
                serviceConnectionListener.onServiceConnected(service);
            }

            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            //unbind your listeners before call callback to prevent
            // receive updates after service is disconnected
            mediaPlayerController.unbindListeners();
            //don't receive  updates in the view
            serviceConnectionListener.onServiceDisconnected();
        }
    };

    private void assertService(){
        if(service == null)
            throw new NullPointerException("You must connect to the service");
    }

}
