package com.thanh.blindhelper;

import android.media.MediaPlayer;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class MyFirebaseMessagingService extends FirebaseMessagingService implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private final static String api = "http://192.168.43.136:5000/get-alert_sound?sound_name=";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification == null) {
            return;
        }
        Log.d("FIREBASE", "From: " + remoteMessage.getFrom());
        Log.d("FIREBASE", "Message Notification Body: " + notification.getBody());

        String fileName = notification.getBody();
//        String url = notification.getBody();
        String url = api + fileName;
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        Log.d("LOG RESPONSE", url);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                response -> {
                    Log.d("LOG RESPONSE", response);
                    try {
                        mediaPlayer.setDataSource(response);
                        mediaPlayer.prepareAsync();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                },
                error -> Log.e("LOG RESPONSE", error.toString())
        );
        requestQueue.add(request);

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
