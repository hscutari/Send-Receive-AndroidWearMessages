package com.example.henriquescutari.firstapp;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by henriquescutari on 12/15/14.
 */
public class ListenerServiceMobile extends WearableListenerService {
    private static final String HELLO_WORLD_WEAR_PATH = "/hello-world-wear";
    private static final String OTHER_MESSAGE = "/message_path";

    public void onMessageReceived(MessageEvent messageEvent) {

        if (messageEvent.getPath().equals(HELLO_WORLD_WEAR_PATH)) {
            final String message = new String(messageEvent.getData());

            // Broadcast message to wearable activity for display
            Intent messageIntent = new Intent();
            messageIntent.setAction(Intent.ACTION_SEND);
            messageIntent.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(messageIntent);
        } else {
            super.onMessageReceived(messageEvent);
        }
    }
}
