package com.example.administrator.gobtinterfaceconnectiontest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends Activity {
    private final String socket_url = "";
    private final String TAG = "aaa";
    private Socket mSocket = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    {
        try {
            System.out.println("begin zezezezeze");
            mSocket = IO.socket(socket_url);

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //on
        mSocket.connect();
        mSocket.on("getFastFoodList",listener);
        mSocket.on("getStretch", listener);

        //emit
        //SocketIoUtil.emitObject(mSocket);

        Map<String, Map<String, Object>> map = new HashMap<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("areaID", 2);
        map.put("getStretch", map1);
        map.put("getFastFoodList",null);
        try {
            //SocketIoUtil.emitObject(mSocket,"getFastFoodList");
            SocketIoUtil.emitObject(mSocket, map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //SocketIoUtil.emitObject("getSretch",map1);
        //  emitObject();
    }


    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject object = (JSONObject) args[0];
                    try {
                        System.out.println(TAG + object.toString());
                        Dialog alertDialog = new AlertDialog.Builder(MainActivity.this).
                                setTitle("title").
                                setMessage(object.toString()).
                                setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).
                                setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                    }
                                }).
                                create();
                        alertDialog.show();
                        int status = object.getInt("status");
                        if (status == 1) {
                            Log.e(TAG, object.toString());
                            //Toast.makeText(MainActivity.this, object.toString(), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    };


}
