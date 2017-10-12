package com.typany.keyboard.sound;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yangfeng on 2017/10/12.
 */

/*
"baseResUrl": "http://10.152.102.239:8080/media/"
{
"sdu": "key_sounds/piano_1507721484.zip" ,
"fs": 215299 ,
"ws": "" ,
"ba": "False" ,
"ek": "" ,
"au": "" ,
"ppu": "key_sounds/sound_icon_l_1507721484.png" ,
"wp": "" ,
"id": 13 ,
"ads": "False" ,
"tt": "1" ,
"si": 13 ,
"sn": "piano" ,
"kt": "True" ,
"rm": "False"
}
 */
public class SoundSkinItem {
    private static final String TAG = SoundSkinItem.class.getSimpleName();

    private String sdu; // full download url
    private long fs; // file size
    private String ppu; // png format preview image url
    private int id; // sound resource identifier
    private String sn; // sound resource name

    public String getSdu() {
        return sdu;
    }

    public void setSdu(String sdu) {
        this.sdu = sdu;
    }

    public long getFs() {
        return fs;
    }

    public void setFs(long fs) {
        this.fs = fs;
    }

    public String getPpu() {
        return ppu;
    }

    public void setPpu(String ppu) {
        this.ppu = ppu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    private static String combineUrl(String baseResUrl, String suffix) {
        if (null != baseResUrl && baseResUrl.toUpperCase().startsWith("HTTP://")) {
            if (baseResUrl.endsWith("/") || suffix.startsWith("/")) {
                return baseResUrl + suffix;
            } else {
                return baseResUrl + "/" + suffix;
            }
        }

        Log.w(TAG, "combineUrl return null with baseResUrl: " + baseResUrl + ", suffix: " + suffix);
        return null;
    }
    public static SoundSkinItem parseItem(JSONObject jsonObject, String baseResUrl) {
        if (null != jsonObject) {
            SoundSkinItem item = new SoundSkinItem();
            item.sdu = combineUrl(baseResUrl, jsonObject.optString("sdu"));
            item.fs = jsonObject.optLong("fs");
            item.ppu = jsonObject.optString("ppu");
            item.id = jsonObject.optInt("id");
            item.sn = jsonObject.optString("sn");

            return item;
        }

        return null;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("name: ").append(sn).append(", id:").append(id).append(", file size:")
                .append(fs).append(",url:").append(sdu).append(", preview:").append(ppu);
        return builder.toString();
    }

    public static List<SoundSkinItem> parseArray(JSONArray strategies, String baseResUrl) {
        if (null == strategies || strategies.length() <= 0) {
            return Collections.emptyList();
        }

        List<SoundSkinItem> itemList = new ArrayList<>();
        if (null != strategies) {
            for (int i = 0; i < strategies.length(); i++) {
                itemList.add(parseItem(strategies.optJSONObject(i), baseResUrl));
            }
        }
        return itemList;
    }
}
