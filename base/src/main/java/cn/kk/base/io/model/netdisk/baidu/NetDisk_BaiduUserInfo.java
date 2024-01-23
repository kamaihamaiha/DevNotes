package cn.kk.base.io.model.netdisk.baidu;

public class NetDisk_BaiduUserInfo {

    private static final int VIP_TYPE_NONE = 0;
    private static final int VIP_TYPE_NORMAL = 1;
    private static final int VIP_TYPE_SUPER = 2;
    public String avatar_url;
    public String baidu_name;
    public String errmsg;
    public String netdisk_name; // 网盘账号
    public String request_id;
    public int errno;
    public int vip_type; // 会员类型，0普通用户、1普通会员、2超级会员
    public long uk; // 用户ID

    public boolean isVip() {
        return vip_type == VIP_TYPE_NORMAL || vip_type == VIP_TYPE_SUPER;
    }

    public boolean isSuperVip() {
        return vip_type == VIP_TYPE_SUPER;
    }
}
