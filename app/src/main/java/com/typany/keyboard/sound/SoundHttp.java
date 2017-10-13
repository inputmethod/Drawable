package com.typany.keyboard.sound;

import android.content.Context;
import android.util.Log;

import com.typany.http.DefaultRetryPolicy;
import com.typany.http.Request;
import com.typany.http.RequestQueue;
import com.typany.http.Response;
import com.typany.http.VolleyError;
import com.typany.http.toolbox.JsonObjectRequest;
import com.typany.http.toolbox.JsonRequest;
import com.typany.http.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangfeng on 2017/9/5.
 */

public class SoundHttp {
    private static final String TAG = SoundHttp.class.getSimpleName();

    public interface OnSoundRequestListener {
        void onLoaded(List<SoundSkinItem> itemList);
        void onError(String message);
    }

    private SoundHttp() {
        // utility class have no instance
    }

    // http://10.152.102.239:8080/api/soundres
    public static void requestSoundInfo(Context context, OnSoundRequestListener listener) {
        final String url = "http://10.152.102.239:8080/api/" + SoundUtils.requestSoundApiSuffix(context, 1);
        // todo: uncomment below final url
        //final String url = GlobalConfiguration.getBaseWebSiteUrl(context) + AlarmUtils.requestAlarmApiSuffix(context);
        requestSoundInfo(context, url, listener);
    }

    public static void requestSoundInfo(final Context context, String url, OnSoundRequestListener listener) {
        Log.i(TAG, "requestSoundInfo, request url=" + url);
        final WeakReference<OnSoundRequestListener> weakReference = null == listener ? null : new WeakReference<>(listener);
        RequestQueue requestQueue = Volley.getQuickQueue(context.getApplicationContext());
        JsonRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "requestSoundInfo onResponse, response: " + response.toString());
                        List<SoundSkinItem> itemList = parseSoundData(context, response);
                        onRequestSuccess(weakReference, itemList);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onRequestError(weakReference, error.getMessage());
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }

    private static void onRequestSuccess(WeakReference<OnSoundRequestListener> weakReference, List<SoundSkinItem> itemList) {
        Log.i(TAG, "onRequestSuccess, parsed item count : " + itemList.size());
        OnSoundRequestListener listener = null == weakReference ? null : weakReference.get();
        if (null != listener) {
            listener.onLoaded(itemList);
        }
    }

    private static void onRequestError(WeakReference<OnSoundRequestListener> weakReference, String message) {
        Log.e(TAG, "onRequestError, error message: " + message);
        OnSoundRequestListener listener = null == weakReference ? null : weakReference.get();
        if (null != listener) {
            listener.onError(message);
        }
    }

    /* sample data
    {
    "code": 200,
    "data": {
        "largeicon": "http://10.152.102.239:8080/media/notify/Test_for_Sticker_1504604093_xaYwDYk.png",
        "title": "Test for Sticker",
        "image": "http://10.152.102.239:8080/media/notify/Test_for_Sticker_1504604093.png",
        "smallicon": "http://10.152.102.239:8080/media/notify/Test_for_Sticker_1504604093_BONEvMD.png",
        "content": "this is content",
        "duration": 24,
        "id": 3
        }
    }
     */
    private static List<SoundSkinItem> parseSoundData(Context context, JSONObject response) {
        try {
            Log.d(TAG, "parseSoundData: " + response.toString());
            String code = response.getString("code");
            if ("200".equals(code)) {
                String baseResUrl = response.optString("baseResUrl");
                JSONArray strategies = response.optJSONArray("strategies");
                List<SoundSkinItem> soundList = SoundSkinItem.parseArray(strategies, baseResUrl);
                Log.i(TAG, "parseSoundData, baseResUrl:" + baseResUrl + soundList);
                return soundList;
            } else {
                Log.e(TAG, "parseSoundData, error code: " + code);
            }
        } catch (Exception error) {
            Log.d(TAG, "error " + error.getMessage());
        }

        return Collections.emptyList();
    }
}
