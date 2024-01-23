package cn.kk.base.io.model.netdisk.baidu;


import cn.kk.base.io.net.NetDiskBaiduManager;

public class NetDisk_BaiduSubtitleErrorResponse {
    /**
     * 31304	不支持文件类型
     * 31649	字幕不存在
     * 31024	没有访问权限
     * 31062	文件名无效，检查是否包含特殊字符
     * 31066	文件不存在
     * 31339	视频非法
     */
    public int error_code;
    public long request_id;
    public int errno;

    public String getMsg(){
       return NetDiskBaiduManager.INSTANCE.getNetDisk_BaiduErrorMsg(error_code);
    }
}
