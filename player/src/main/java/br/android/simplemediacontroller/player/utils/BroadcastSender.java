package br.android.simplemediacontroller.player.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import br.android.simplemediacontroller.player.notification.NotificationReceiver;


/**
 * Created by diogojayme on 7/16/15.
 */
public class BroadcastSender {
    Intent intent;
    Context context;

    public static BroadcastSender sender;

    public static BroadcastSender getInstance(){
        if(sender == null){
            sender = new BroadcastSender();
        }

        return sender;
    }

    public BroadcastSender(){
        intent = new Intent();
    }

    public BroadcastSender from(Context context){
        this.context = context;
        return this;
    }

    public BroadcastSender toReceiver(String receiver){
        this.intent.setAction(receiver);
        return  this;
    }

    //[NETX,PREV,PAUSE,PLAY, PROGRESS...]
    public BroadcastSender filter(String filter){
        intent.putExtra(NotificationReceiver.BROADCAST_FILTER, filter);
        return this;
    }

    public  BroadcastSender extras(HashMap<String, Object> additional){

        if(additional == null){
            throw new NullPointerException("write() additional is null");
        }

        Iterator iterator = additional.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();

            Object value = pair.getValue();
            String key = pair.getKey().toString(); //aways string

            if(value instanceof Boolean){
                intent.putExtra(key, (Boolean) value);
            }else if(value instanceof Integer){
                intent.putExtra(key, (Integer) value);
            }else if(value instanceof String){
                intent.putExtra(key, (String) value);
            }

            iterator.remove(); // avoids a ConcurrentModificationException
        }

        return this;
    }

    public  BroadcastSender extras(String key, Object data){

        if(data instanceof Boolean){
            intent.putExtra(key, (Boolean) data);
        }else if(data instanceof Integer){
            intent.putExtra(key, (Integer) data);
        }else if(data instanceof String){
            intent.putExtra(key, (String) data);
        }

        return this;
    }

    public void send(){
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
