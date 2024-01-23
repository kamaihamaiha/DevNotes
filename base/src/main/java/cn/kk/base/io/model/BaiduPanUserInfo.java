package cn.kk.base.io.model;

import com.google.gson.annotations.SerializedName;

public class BaiduPanUserInfo {

    @SerializedName("avatar_url")
    public String avatar_url;
    @SerializedName("baidu_name")
    public String baidu_name;
    @SerializedName("errmsg")
    public String errmsg;
    @SerializedName("errno")
    public Integer errno;
    @SerializedName("netdisk_name")
    public String netdisk_name;
    @SerializedName("request_id")
    public String request_id;
    @SerializedName("uk")
    public Integer uk;
    @SerializedName("vip_type")
    public Integer vip_type;

    public boolean isSuperVip(){
       return vip_type == 2;
    }

    public boolean isNormalVip(){
        return vip_type == 1;
    }

    public boolean isVip(){
        return vip_type != 0;
    }
}
