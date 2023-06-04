package com.thanh.blindhelper;

import android.media.MediaPlayer;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification == null) {
            return;
        }
        Log.d("FIREBASE", "From: " + remoteMessage.getFrom());
        Log.d("FIREBASE", "Message Notification Body: " + notification.getBody());

//        String url = notification.getBody();
        String url = "https://res.cloudinary.com/dbk0cmzcb/video/upload/v1685527255/ihmmbsxyog77ugk3v96z.mp3";
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.pause();
        mp.reset();
        mp.release();
        mp = null;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
