package cn.kk.base.io.model.netdisk;

public class NetDiskSearchSubtitleResponse {

    public String name;
    public String url;
    public int vote_score;
    public String videoname;
    public String release_site;

    @Override
    public String toString() {
        return "SearchSubtitleResponse{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", vote_score=" + vote_score +
                ", videoname='" + videoname + '\'' +
                ", release_site='" + release_site + '\'' +
                '}';
    }

    public static class WaitResponse {
        public String msg;
    }
}
