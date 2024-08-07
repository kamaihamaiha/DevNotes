package cn.kk.base.io.model.netdisk;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

import cn.kk.base.io.ObjectCallback;
import cn.kk.base.io.model.netdisk.baidu.NetDisk_BaiduFileMetaInfo;
import cn.kk.base.io.model.netdisk.baidu.NetDisk_BaiduUserInfo;
import cn.kk.base.utils.ThreadHelper;
import okhttp3.Response;

public class NetDiskSubtitleSearchRequest {
    public NetDisk_BaiduFileMetaInfo file;
    public String keyword;
    public NetDisk_BaiduUserInfo user;

    public NetDiskSubtitleSearchRequest(String keyword , NetDisk_BaiduFileMetaInfo file, NetDisk_BaiduUserInfo user) {
        this.file = file;
        this.keyword = keyword;
        this.user = user;
    }

    public String getRequestData(){
       return new Gson().toJson(this);
    }

    public boolean isValid(String data) {
        try {
            Object json = new JSONTokener(data).nextValue();
            if (json instanceof JSONObject) return false;
            if (json instanceof JSONArray) return true;
        } catch (JSONException e) {
           return false;
        }
        return false;
    }


    private NetDiskSearchSubtitleResponse[] parseSubtitles(String json){
        try {
            NetDiskSearchSubtitleResponse[] subtitleResponseArray =  new Gson().fromJson(json, NetDiskSearchSubtitleResponse[].class);
            return subtitleResponseArray;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private NetDiskSearchSubtitleResponse.WaitResponse parseWaitResponse(String json) {
        try {
            NetDiskSearchSubtitleResponse.WaitResponse waitResponse = new Gson().fromJson(json, NetDiskSearchSubtitleResponse.WaitResponse.class);
            return waitResponse;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
