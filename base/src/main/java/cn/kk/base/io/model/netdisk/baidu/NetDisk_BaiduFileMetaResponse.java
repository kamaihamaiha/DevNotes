package cn.kk.base.io.model.netdisk.baidu;

import java.util.List;

public class NetDisk_BaiduFileMetaResponse {

    public String errmsg;
    public int errno;
    public List<NetDisk_BaiduFileMetaInfo> list;
    public Names names;
    public String request_id;

    public static class Names {
    }

}
