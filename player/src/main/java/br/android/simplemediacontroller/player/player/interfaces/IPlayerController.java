package br.android.simplemediacontroller.player.player.interfaces;

import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.player.listeners.OnMediaChangedListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaErrorListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaLoadingListener;
import br.android.simplemediacontroller.player.player.listeners.OnMediaProgressListener;
import br.android.simplemediacontroller.player.player.listeners.OnServiceConnectionListener;

/**
 * Created by diogojayme on 12/22/15.
 */
public interface IPlayerController {
    void toggle();
    void next();
    void play();
    void previous();
    void repeat();
    void seekTo(int position);
    void shuffle(boolean shuffle);
    boolean isShuffleEnabled();

    void resume();
    boolean canResume(long id);

    int getCurrentTrackPosition();
    void setCurrentTrackPosition(int position);

    boolean isMediaPlayerPlaying();

    void setMediaStore(MediaStore mediaStore);
    MediaStore getMediaStore();

    void addOnServiceConnectedListener(OnServiceConnectionListener listener);
    void addOnMediaErrorListener(OnMediaErrorListener errorListener);
    void addOnProgressListener(OnMediaProgressListener progressListener);
    void addOnMediaChangedListener(OnMediaChangedListener changedListener);
    void addOnMediaLoadingListener(OnMediaLoadingListener loadingListener);
    void unbindListeners();
}
