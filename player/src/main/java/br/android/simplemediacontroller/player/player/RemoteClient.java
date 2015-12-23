package br.android.simplemediacontroller.player.player;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RemoteControlClient;

import br.android.simplemediacontroller.player.notification.NotificationReceiver;


/**
 * Created by diogojayme on 7/17/15.
 */
 class RemoteClient {
    ComponentName remoteComponentName;
    RemoteControlClient remoteControlClient;

    @SuppressLint("NewApi")
    public void registerRemoteClient(Context context){
        /**
         * @param context
         * @param notificationReceiver
         *
         * */
        remoteComponentName = new ComponentName(context, new NotificationReceiver().ComponentName());

        try {
            if(remoteControlClient == null) {
                Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                mediaButtonIntent.setComponent(remoteComponentName);
                PendingIntent mediaPendingIntent = PendingIntent.getBroadcast(context, 0, mediaButtonIntent, 0);
                remoteControlClient = new RemoteControlClient(mediaPendingIntent);
            }

            remoteControlClient.setTransportControlFlags(
                    RemoteControlClient.FLAG_KEY_MEDIA_PLAY |
                            RemoteControlClient.FLAG_KEY_MEDIA_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_PLAY_PAUSE |
                            RemoteControlClient.FLAG_KEY_MEDIA_STOP |
                            RemoteControlClient.FLAG_KEY_MEDIA_PREVIOUS |
                            RemoteControlClient.FLAG_KEY_MEDIA_NEXT);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
