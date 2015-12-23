package br.android.simplemediacontroller.player.player.listeners;

import br.android.simplemediacontroller.player.player.PlayerService;

/**
 * Created by diogojayme on 9/19/15.
 */
public interface OnServiceConnectionListener {
    void onServiceConnected(PlayerService service);
    void onServiceDisconnected();
}
