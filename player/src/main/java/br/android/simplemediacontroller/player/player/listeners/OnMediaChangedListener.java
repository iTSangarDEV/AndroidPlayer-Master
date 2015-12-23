package br.android.simplemediacontroller.player.player.listeners;

import br.android.simplemediacontroller.player.model.MediaStore;

public interface OnMediaChangedListener {
    void onMediaChanged(MediaStore mediaStore, int songPosition, boolean isPlaying);
}