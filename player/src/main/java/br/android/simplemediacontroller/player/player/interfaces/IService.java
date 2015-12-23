package br.android.simplemediacontroller.player.player.interfaces;

import android.content.Context;

import br.android.simplemediacontroller.player.player.listeners.OnServiceConnectionListener;

/**
 * Created by diogojayme on 12/22/15.
 */
public interface IService {

    void connect(Context context);
    void disconnect(Context context);
    boolean isConnected();

    void showNotification(Context context);
    void removeNotification();

    void addOnServiceConnectionListener(OnServiceConnectionListener serviceConnectionListener);
}
