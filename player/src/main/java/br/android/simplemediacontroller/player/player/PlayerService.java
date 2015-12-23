package br.android.simplemediacontroller.player.player;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import br.android.simplemediacontroller.player.notification.NotificationReceiver;
import br.android.simplemediacontroller.player.notification.PlayerControlNotification;

/**
 * Created by diogojayme on 9/18/15.
 */
public class PlayerService extends Service {

    PlayerBinder binder = new PlayerBinder();
    MediaPlayerController mediaPlayerController;
    PlayerControlNotification playerControlNotification;

    @Override
    public void onCreate() {
        super.onCreate();
        new RemoteClient().registerRemoteClient(this);
        playerControlNotification = new PlayerControlNotification(this);
        mediaPlayerController = new MediaPlayerController(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(serviceReceiver, new IntentFilter(NotificationReceiver.SERVICE));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void showNotification(Context context){
        if(mediaPlayerController != null && mediaPlayerController.isMediaPlayerPlaying()) {
            startForeground(PlayerControlNotification.NOTIFICATION_PLAYER_ID, playerControlNotification.buildNotification(context.getClass()));
            playerControlNotification.updateInfo(mediaPlayerController.getMediaStore(), mediaPlayerController.getCurrentTrackPosition());//forces notification to update when it launched
            playerControlNotification.updatePlayPause(mediaPlayerController.isMediaPlayerPlaying());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(serviceReceiver);
    }

    public void removeNotification(){
        stopForeground(true);
    }

    @Nullable
    @Override public IBinder onBind(Intent intent) {
        return binder;
    }

    public class PlayerBinder extends Binder {
        public PlayerService getService(){
            return PlayerService.this;
        }
    }

    public MediaPlayerController getMediaController(){
        return mediaPlayerController;
    }

    public BroadcastReceiver serviceReceiver  = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(mediaPlayerController != null && intent.getExtras() != null) {
                String filter = intent.getExtras().getString(NotificationReceiver.BROADCAST_FILTER);

                if(filter != null){
                    if (filter.equals(NotificationReceiver.BROADCAST_ACTION_NEXT)) {

                        mediaPlayerController.next();
                        playerControlNotification.updateInfo(mediaPlayerController.getMediaStore(), mediaPlayerController.getCurrentTrackPosition());
                    } else if (filter.equals(NotificationReceiver.BROADCAST_ACTION_PREVIOUS)) {

                        mediaPlayerController.previous();
                        playerControlNotification.updateInfo(mediaPlayerController.getMediaStore(), mediaPlayerController.getCurrentTrackPosition());
                    } else if (filter.equals(NotificationReceiver.BROADCAST_ACTION_TOGGLE)) {

                        mediaPlayerController.toggle();
                        playerControlNotification.updateInfo(mediaPlayerController.getMediaStore(), mediaPlayerController.getCurrentTrackPosition());
                        playerControlNotification.updatePlayPause(mediaPlayerController.isMediaPlayerPlaying());
                    } else if (filter.equals(NotificationReceiver.BROADCAST_ACTION_STOP)) {

                        mediaPlayerController.pause();
                        stopForeground(true);
                    }
                }
            }
        }
    };
}
