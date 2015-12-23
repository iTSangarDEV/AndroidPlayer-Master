package br.android.simplemediacontroller.player.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import br.android.simplemediacontroller.player.utils.BroadcastSender;

/**
 * Created by diogojayme on 9/28/15.
 */
public class NotificationReceiver extends BroadcastReceiver {
    public static final String SERVICE = "br.android.simplemediacontroller.SERVICE";

    public static String BROADCAST_FILTER = "br.android.simplemediacontroller.FILTER";
    public static String BROADCAST_ACTION_NEXT = "br.android.simplemediacontroller.NEXT";
    public static String BROADCAST_ACTION_STOP = "br.android.simplemediacontroller.STOP";
    public static String BROADCAST_ACTION_TOGGLE = "br.android.simplemediacontroller.TOGGLE";
    public static String BROADCAST_ACTION_PREVIOUS = "br.android.simplemediacontroller.PREVIOUS";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(BROADCAST_ACTION_NEXT)){
            BroadcastSender.getInstance()
                    .from(context)
                    .filter(BROADCAST_ACTION_NEXT)
                    .toReceiver(SERVICE)
                    .send();
        }else if(intent.getAction().equals(BROADCAST_ACTION_TOGGLE)){
            BroadcastSender.getInstance()
                    .from(context)
                    .filter(BROADCAST_ACTION_TOGGLE)
                    .toReceiver(SERVICE)
                    .send();
        }else if(intent.getAction().equals(BROADCAST_ACTION_PREVIOUS)){
            BroadcastSender.getInstance()
                    .from(context)
                    .filter(BROADCAST_ACTION_PREVIOUS)
                    .toReceiver(SERVICE)
                    .send();
        }else if(intent.getAction().equals(BROADCAST_ACTION_STOP)){
            BroadcastSender.getInstance()
                    .from(context)
                    .filter(BROADCAST_ACTION_STOP)
                    .toReceiver(SERVICE)
                    .send();
        }
    }

    public String ComponentName() {
        return this.getClass().getName();
    }
}
