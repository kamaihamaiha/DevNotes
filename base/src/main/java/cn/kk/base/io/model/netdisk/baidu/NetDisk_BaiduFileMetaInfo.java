package cn.kk.base.io.model.netdisk.baidu;

public class NetDisk_BaiduFileMetaInfo {
    public int category;
    public int duration; // 单位:s
    public String filename;
    public long fs_id;
    public long sub_fs_id;
    public int isdir;
    public int local_ctime;
    public int local_mtime;
    public String md5;
    public long oper_id;
    public String path;
    // https://d.pcs.baidu.com/file/3c57a0a08i65bc220f1374867d963f23?fid=3436470027-250528-454049806267902&rt=pr&sign=FDtAERVK-DCb740ccc5511e5e8fedcff06b081203-9q7l%2FXjisC4WKj8TnqEvOER1K8s%3D&expires=8h&chkbd=0&chkv=3&dp-logid=1000905069906826169&dp-callid=0&dstime=1704424824&r=131689276&vuk=3436470027&origin_appid=42733013&file_type=0
    public String dlink; // 文件下载地址
    public int server_ctime;
    public int server_mtime;
    public long size;
    public NetDisk_BaiduFileThumbs thumbs;
}
