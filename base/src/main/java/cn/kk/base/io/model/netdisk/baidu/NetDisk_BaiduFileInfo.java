package cn.kk.base.io.model.netdisk.baidu;

import android.text.TextUtils;

import cn.kk.base.utils.TimeHelper;

public class NetDisk_BaiduFileInfo {
    static final int FILE_TYPE_DIR = 1;
    static final int FILE_CATEGORY_VIDEO = 1;
    static final int FILE_CATEGORY_AUDIO = 2;

    static final String SUBTITLE_TYPE_LRC = ".lrc";
    static final String SUBTITLE_TYPE_SRT = ".srt";
    static final String SUBTITLE_TYPE_ASS = ".ass";


    public int category;
    public int date_taken;
    public String dlink;
    public String server_filename;
    public long fs_id;
    public int height;
    public int isdir;
    public String md5;
    public long oper_id;
    public String path;
    public int server_ctime;
    public int server_mtime;
    public long size;
    public NetDisk_BaiduFileThumbs thumbs;
    public int width;
    public String orientation;

    public String subtitleFilePath; // 网盘同级目录下，同名的字幕文件路径
    public long sub_fs_id; // 网盘同级目录下，同名的字幕文件 fsid
    public String subtitleFileDLink; // 网盘同级目录下，同名的字幕文件 下载地址


    public String getServerATime() {
        return TimeHelper.getTime(server_mtime * 1000L);
    }

    public boolean isDirTag() {
        return isdir == FILE_TYPE_DIR;
    }

    public boolean isVideoType() {
        return category == FILE_CATEGORY_VIDEO;
    }

    public boolean isAudioType() {
        return category == FILE_CATEGORY_AUDIO;
    }

    public boolean supportType() {
        return isDirTag() || category == FILE_CATEGORY_VIDEO || category == FILE_CATEGORY_AUDIO;
    }

    public boolean supportSubtitleType(){
        if (TextUtils.isEmpty(server_filename)) return false;
       return server_filename.endsWith(SUBTITLE_TYPE_LRC) || server_filename.endsWith(SUBTITLE_TYPE_SRT) || server_filename.endsWith(SUBTITLE_TYPE_ASS);
    }

    public boolean hasSubtitleFile(){
        return !TextUtils.isEmpty(subtitleFilePath);
    }

    public boolean isPlaying(String curPlayingArticleUUID) {
        return generateArticleUUID().equals(curPlayingArticleUUID);
    }

    /*public String getSubtitleFileSaveLocalPath(){ // 要保存到本地的字幕文件路径
       return IOUtils.getNetDiskFile() + File.separator + getFileName();
    }*/

    public String getFileName(){
        // server_filename hello.mkv -> hello
        if (TextUtils.isEmpty(server_filename)) return server_filename;
        int pointIndex = server_filename.lastIndexOf('.');
        if (pointIndex == -1) return server_filename;
        return server_filename.substring(0, pointIndex);
    }

    /**
     * 根据 fs_id 和资源类型生成文章uuid
     * @return
     */
    public String generateArticleUUID(){
        String numStr = String.valueOf(fs_id);
        String padded = "000000000000000000000000".substring(numStr.length()) + numStr; // 确保数字部分总共有 28 位，包括前面的零
        String prefix = isVideoType() ? "bdbd0101-" : "bdbd0202-";
        return prefix + padded.substring(0, 4) + "-" + padded.substring(4, 8) + "-" + padded.substring(8, 12) + "-" + padded.substring(12);
    }

    public String getHumanSize() {
        float kb = size / 1024f;
        float mb = kb / 1024f;
        float gb = mb / 1024f;
        float tb = gb / 1024f;
        float threadhold = 1.00f;
        String hmSize = "";
        if (tb >= threadhold) { 
            hmSize = String.format("%.2f TB", tb);
        } else if (gb >= threadhold) {
            hmSize = String.format("%.2f GB", gb);
        } else if (mb >= threadhold) {
            hmSize = String.format("%.2f MB", mb);
        } else if (kb >= threadhold) {
            hmSize = String.format("%.2f KB", kb);
        } else {
            hmSize = String.format("%d B", size);
        }

        return hmSize;
    }
}