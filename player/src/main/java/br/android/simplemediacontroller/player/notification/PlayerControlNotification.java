package br.android.simplemediacontroller.player.notification;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import br.android.simplemediacontroller.R;
import br.android.simplemediacontroller.player.model.Album;
import br.android.simplemediacontroller.player.model.Artist;
import br.android.simplemediacontroller.player.model.MediaStore;
import br.android.simplemediacontroller.player.model.Track;


/**
 * Created by diogojayme on 7/17/15.
 */
public class PlayerControlNotification {
    Context context;
    Notification notification;
    NotificationManager notifyManager;

    public static final int NOTIFICATION_PLAYER_ID = 9781211;

    public PlayerControlNotification(Context context){
        this.context = context;
        this.notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    RemoteViews bigView;
    RemoteViews smallView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ServiceCast")
    public Notification buildNotification(Class clazz){
        Intent notificationIntent = new Intent(context, clazz);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        smallView = new RemoteViews(context.getPackageName(), R.layout.player_notification_control);
        bigView = new RemoteViews(context.getPackageName(), R.layout.player_notification_control_big);
        addListeners(smallView);
        addListeners(bigView);

        notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_notification_icon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        notification.contentView = smallView;

        if(currentVersionSupportBigNotification()){
            notification.bigContentView = bigView;
        }

        return notification;
    }

    public static boolean currentVersionSupportBigNotification() {
        int sdkVersion = android.os.Build.VERSION.SDK_INT;
        return sdkVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN;
    }

    public void addListeners(RemoteViews view) {
        Intent next = new Intent(NotificationReceiver.BROADCAST_ACTION_NEXT);
        Intent play = new Intent(NotificationReceiver.BROADCAST_ACTION_TOGGLE);
        Intent stop = new Intent(NotificationReceiver.BROADCAST_ACTION_STOP);
        Intent previous = new Intent(NotificationReceiver.BROADCAST_ACTION_PREVIOUS);

        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.player_notification_control_previous, pPrevious);

        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.player_notification_control_next, pNext);

        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        view.setOnClickPendingIntent(R.id.player_notification_control_play, pPlay);

        if(currentVersionSupportBigNotification()) {
            PendingIntent pStop = PendingIntent.getBroadcast(context, 0, stop, PendingIntent.FLAG_UPDATE_CURRENT);
            view.setOnClickPendingIntent(R.id.player_notification_control_close, pStop);
        }
    }

    public void updateInfo(MediaStore mediaStore, int currentIndex){
        Album album = mediaStore.getAlbum();
        Artist artist = mediaStore.getArtist();
        Track track = mediaStore.getTracks().get(currentIndex);

        smallView.setTextViewText(R.id.player_notification_control_song, track.getName());
        smallView.setTextViewText(R.id.player_notification_control_artist, artist.getName());

        if (currentVersionSupportBigNotification()) {
            bigView.setTextViewText(R.id.player_notification_control_song, track.getName());
            bigView.setTextViewText(R.id.player_notification_control_album, album.getName());
            bigView.setTextViewText(R.id.player_notification_control_artist, artist.getName());
        }

        Picasso.with(context)
                .load(album.getImage(Album.SIZE_300x300))
                .into(mTarget);

        showNotification();
    }

    public void updatePlayPause(boolean isPlaying){
        smallView.setImageViewResource(R.id.player_notification_control_play, isPlaying ? R.drawable.ic_notifplayer_pause : R.drawable.ic_notifplayer_play);

        if(currentVersionSupportBigNotification()){
            bigView.setImageViewResource(R.id.player_notification_control_play,  isPlaying ? R.drawable.ic_notifplayer_pause : R.drawable.ic_notifplayer_play);
        }

        showNotification();
    }

    public Target mTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            smallView.setImageViewBitmap(R.id.player_notification_image, bitmap);

            if (currentVersionSupportBigNotification()) {
                bigView.setImageViewBitmap(R.id.player_notification_image, bitmap);
            }

            showNotification();
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
        }

        @Override
        public void onPrepareLoad(Drawable drawable) {
            smallView.setImageViewResource(R.id.player_notification_image, R.drawable.img_placeholder);

            if (currentVersionSupportBigNotification()) {
                bigView.setImageViewResource(R.id.player_notification_image, R.drawable.img_placeholder);
            }

            showNotification();
        }
    };

    private PlayerControlNotification showNotification(){
        notifyManager.notify(NOTIFICATION_PLAYER_ID, notification);
        return this;
    }

    public PlayerControlNotification dismiss(){
        notifyManager.cancel(NOTIFICATION_PLAYER_ID);
        return this;
    }
}