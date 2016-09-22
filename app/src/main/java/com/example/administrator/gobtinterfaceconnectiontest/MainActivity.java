package com.example.administrator.gobtinterfaceconnectiontest;

import android.app.Activity;
import android.content.Context;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MainActivity extends Activity {
    private final String socket_url="";
    private final String TAG="aaa";
    private Socket mSocket = null;
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
        mSocket.on("getStretch", listener);
        //emit
        emitObject();
    }

    public void emitObject() {
        JSONObject emitObj = new JSONObject();
        try {
            emitObj.put("areaID", 2);
            System.out.println(TAG+emitObj.toString());
            mSocket.emit("getStretch", emitObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private Emitter.Listener listener = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject object = (JSONObject) args[0];
            try {
                System.out.println(TAG+object.toString());
                int status = object.getInt("status");
                if (status == 1) {
                    Log.e(TAG, object.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

//    private String getUUID() {
//        final TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
//        final String tmDevice, tmSerial, tmPhone, androidId;
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        return deviceUuid.toString();
//    }


}
