package com.example.henriquescutari.firstapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private TextView mTextView;
    private Button btnTela;
    private Button btnTela2;
    ClientAction cliente = new ClientAction();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);

        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                Button btnTela2 = (Button) stub.findViewById(R.id.btnTela2);

                //Listener to send the message (it is just an example)
                btnTela2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cliente.sendMessage("Setado pelo wear ;D");
                    }
                });

                mTextView = (TextView) stub.findViewById(R.id.text);
                btnTela = (Button) stub.findViewById(R.id.btnTela);

                btnTela.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTextView.setText("Mudar o texto");
                    }
                });
            }
        });

        cliente.ClientAction(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cliente.onStart();
    }

    public void SetMessage(String message){
        // Display message in UI
        mTextView.setText(message);
    }

    @Override
    public void onConnectionSuspended(int i) {
        //Improve your code
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Improve your code
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

}
