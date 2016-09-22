package com.example.administrator.gobtinterfaceconnectiontest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.socket.client.Socket;

/**
 * Created by Administrator on 2016/9/22.
 */
public class SocketIoUtil {
    private final static String TAG = "socketio";

    public static void emitObject(Socket mSocket, String socketname, Map<String, Object> map) throws Exception {
        if (map != null) {
            JSONObject emitObj = new JSONObject();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                emitObj.put(entry.getKey(), entry.getValue());
            }
            mSocket.emit(socketname, emitObj);
        }
    }

    public static void emitObject(Socket mSocket, Map<String, Map<String, Object>> map) throws Exception {
        if (map != null) {
            JSONObject emitObj = new JSONObject();
            for (Map.Entry<String, Map<String, Object>> entry : map.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if (entry.getValue() != null) {
                    for (Map.Entry<String, Object> entry2 : entry.getValue().entrySet()) {
                        emitObj.put(entry2.getKey(), entry2.getValue());
                    }
                }
                mSocket.emit(entry.getKey(), emitObj);
            }

        }
    }

    public static void emitObject(Socket mSocket, String socketname) {
        mSocket.emit(socketname, "");
    }

    /**
     * @param mSocket
     * @param socketname
     * @param map        多次 Map<String, Map<String, Object>> ||单次 Map<String, Object>
     * @param flag       是否为多次
     * @throws JSONException
     */
    public static void emitObject(Socket mSocket, String socketname, Map map, Boolean flag) throws JSONException {
        try {
            if (map != null) {
                JSONObject emitObj = new JSONObject();
                if (flag) {
                    Iterator<Map.Entry<String, Map<String, Object>>> entries = map.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, Map<String, Object>> entry = entries.next();
                        //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        Iterator<Map.Entry<String, Object>> entries2 = entry.getValue().entrySet().iterator();
                        while (entries2.hasNext()) {
                            Map.Entry<String, Object> entry2 = entries2.next();
                            // System.out.println("zzz" + entry2.getKey() + entry2.getValue());
                            emitObj.put(entry2.getKey(), entry2.getValue());
                        }
                    }
                } else {
                    Iterator<Map.Entry<String, Object>> entries = map.entrySet().iterator();
                    while (entries.hasNext()) {
                        Map.Entry<String, Object> entry = entries.next();
                        //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                        emitObj.put(entry.getKey(), entry.getValue());
                    }
                }
                mSocket.emit(socketname, emitObj);
            }
        } catch (ClassCastException e) {
            Log.e(TAG, "类型异常");
        } catch (Exception e) {
            Log.e(TAG, "其他异常");
        }

    }

}
