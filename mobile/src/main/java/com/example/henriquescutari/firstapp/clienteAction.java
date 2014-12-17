package com.example.henriquescutari.firstapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by henriquescutari on 12/14/14.
 */
public class clienteAction implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleClient;
    MainActivity _pActivity = null;

    public void clientAction(MainActivity pActivity){
        _pActivity = pActivity;

        googleClient = new GoogleApiClient.Builder(pActivity)
                .addApi(Wearable.API)
                .addConnectionCallbacks(pActivity)
                .addOnConnectionFailedListener(pActivity)
                .build();
    }

    public void onStart() {
        googleClient.connect();
    }

    public void onConnected() {
        String message = "Hello wearable\n Via the data layer";
        SetMenssagem(message);
    }


    public void SetMenssagem(String mensagem){
        new SendToDataLayerThread("/message_path", mensagem).start();
    }

    public void onStop() {
        if (null != googleClient && googleClient.isConnected()) {
            googleClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        onConnected();
    }

    // Placeholders for required connection callbacks
    @Override
    public void onConnectionSuspended(int cause) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    class SendToDataLayerThread extends Thread {
        String path;
        String message;

        // Constructor to send a message to the data layer
        SendToDataLayerThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "Message: {" + message + "} sent to: " + node.getDisplayName());
                }
                else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send Message");
                }
            }
        }
    }
}
