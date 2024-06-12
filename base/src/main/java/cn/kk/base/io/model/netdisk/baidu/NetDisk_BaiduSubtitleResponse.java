package cn.kk.base.io.model.netdisk.baidu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频字幕
 */
public class NetDisk_BaiduSubtitleResponse {
    /**
     * #EXTM3U
     * #MEDIA:SUBTITLES
     * #EXT-X-MEDIA:TYPE=SUBTITLES,GROUP-ID="subs",NAME="国语风格特效双显.by.体重涨停",DEFAULT=YES,AUTOSELECT=YES,LANGUAGE="chi"
     * https://nv0.baidupcs.com/video/netdisk-embedded-subtitle/025389a22tfe15c15f365d03a8c1e565_77824_3_srt/489ca829bc867891d9aa01371ade99e9?app_id=42733013&csl=0&dp-logid=318100431300778633&fn=%E9%A9%B1%E9%AD%94%E8%AD%A6%E5%AF%9F.mkv&from_type=3&fsid=1012842632300030&iot_dev=0&iot_vip=0&iv=2&logid=318100431300778633&mtime=1700715019&ouk=3436470027&r=245709529&size=394952509&sta_cs=45923&sta_dt=video&sta_dx=376&time=1703780987&to=hsn00&tot=c2lp4&uo=cnc&uva=2862637690&vuk=3436470027&backhost=%5B%22xacu07.baidupcs.com%22%2C%22yqall07.baidupcs.com%22%5D&etag=489ca829bc867891d9aa01371ade99e9&fid=dcd1e8eeec5dbfca2200dbe7510e953d-3436470027&len=185653&range=0-185652&region=xian&resv4=&sign=BOUTRFPQV-F3530edecde9cd71b79378b290804a96-mjRkN9%252BI9s%252B9IIbe1E5p69pEgbM%253D&xcode=52add9a266b838c61b6c4a93e9dc1a193601a0ee280aa5ca141d90a19ede94e34b9fcb154dd93cdb07bbd472bc39f57e316128a2cdfcce4d&xv=6&need_suf=&pmk=&fpath=%23%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E6%B5%8B%E8%AF%95&by=my-streaming
     * #EXT-X-MEDIA:TYPE=SUBTITLES,GROUP-ID="subs",NAME="粤语风格特效双显.by.天羽",DEFAULT=NO,AUTOSELECT=NO,LANGUAGE="chi"
     * https://nv0.baidupcs.com/video/netdisk-embedded-subtitle/025389a22tfe15c15f365d03a8c1e565_77824_4_srt/d57dd7666450ae0cdde24d03f176feea?app_id=42733013&csl=0&dp-logid=318100431300778633&fn=%E9%A9%B1%E9%AD%94%E8%AD%A6%E5%AF%9F.mkv&from_type=3&fsid=1012842632300030&iot_dev=0&iot_vip=0&iv=2&logid=318100431300778633&mtime=1700715019&ouk=3436470027&r=245709529&size=394952509&sta_cs=45923&sta_dt=video&sta_dx=376&time=1703780987&to=hsn00&tot=c2lp4&uo=cnc&uva=2862637690&vuk=3436470027&etag=d57dd7666450ae0cdde24d03f176feea&fid=70e95ed187528b9e4bfd48b8149e26a9-3436470027&len=151256&range=0-151255&region=xian&resv4=&sign=BOUTRFPQV-F3530edecde9cd71b79378b290804a96-LeVPuCm3W44AUJ99Zxo%252BEW0iCoc%253D&xcode=52add9a266b838c62dadb9ccc03d11ca688ebc91ee0848808d451cafeead94d34596628c70a5c47407bbd472bc39f57e316128a2cdfcce4d&xv=6&need_suf=&pmk=&by=my-streaming
     * #EXT-X-MEDIA:TYPE=SUBTITLES,GROUP-ID="subs",NAME="国语风格简体中文",DEFAULT=NO,AUTOSELECT=NO,LANGUAGE="chi"
     * https://nv0.baidupcs.com/video/netdisk-embedded-subtitle/025389a22tfe15c15f365d03a8c1e565_77824_5_srt/b417e77ac42573427a0c91478e9924b1?app_id=42733013&csl=0&dp-logid=318100431300778633&fn=%E9%A9%B1%E9%AD%94%E8%AD%A6%E5%AF%9F.mkv&from_type=3&fsid=1012842632300030&iot_dev=0&iot_vip=0&iv=2&logid=318100431300778633&mtime=1700715019&ouk=3436470027&r=245709529&size=394952509&sta_cs=45923&sta_dt=video&sta_dx=376&time=1703780987&to=hsn00&tot=c2lp4&uo=cnc&uva=2862637690&vuk=3436470027&etag=b417e77ac42573427a0c91478e9924b1&fid=9eb1ad053f99176631072cc820494ea6-3436470027&len=60440&range=0-60439&region=xian&resv4=&sign=BOUTRFPQV-F3530edecde9cd71b79378b290804a96-tHfHNr1PPiFna0wWlIcBVa8i%252B2c%253D&xcode=52add9a266b838c6c7823ea2b701f2726c81827765830375309133e505103be18522c03556fec89a07bbd472bc39f57e316128a2cdfcce4d&xv=6&need_suf=&pmk=&by=my-streaming
     */
    public String original; // 返回结果

    public NetDisk_BaiduSubtitleResponse() {
    }

    public NetDisk_BaiduSubtitleResponse(String original) {
        this.original = original;
    }

    public List<SubtitleBean> parse(){
        return parse(original);
    }
    public List<SubtitleBean> parse(String data){
        List<SubtitleBean> list = new ArrayList<>();
        if(data == null || data.isEmpty()){
            return list;
        }
        String[] lines = data.split("\n");
        for(String line : lines){
            SubtitleBean bean = new SubtitleBean();
            if(line.startsWith("#EXT-X-MEDIA:TYPE=SUBTITLES")){
                String[] items = line.split(",");
                for(String item : items){
                    if(item.startsWith("NAME")){
                        bean.name = item.substring(item.indexOf("\"") + 1, item.lastIndexOf("\""));
                    }else if(item.startsWith("LANGUAGE")){
                        bean.language = item.substring(item.indexOf("\"") + 1, item.lastIndexOf("\""));
                    }else if(item.startsWith("GROUP-ID")){
                        bean.group_id = item.substring(item.indexOf("\"") + 1, item.lastIndexOf("\""));
                    }else if(item.startsWith("DEFAULT")){
                        bean.is_default = item.endsWith("YES");
                    }else if(item.startsWith("AUTOSELECT")){
                        bean.is_auto_select = item.endsWith("YES");
                    }
                }
                list.add(bean);
            } else if (line.startsWith("https://")){
                list.get(list.size() - 1).url = line;
            }
        }
        return list;
    }

    /**
     * name:
     * 从这里找 "简英","中英","英文","双显","双语"
     * 找不到就用列表的第一个
     */
    String[] RECOMMEND_NAMES = {"简英","中英","英文","双显","双语"};
    public SubtitleBean getRecommendSubtitle(){
        List<SubtitleBean> list = parse();
        if (list.isEmpty()) return null;

        for(SubtitleBean bean : list){
           boolean find = findRecommendTag(bean.name);
           if(find) return bean;
        }
        return list.get(0);
    }

    public boolean findRecommendTag(String name){
        for (String recommendName : RECOMMEND_NAMES) {
            if(name.contains(recommendName)){
                return true;
            }
        }
        return false;
    }

    public static class SubtitleBean {
        public String name;
        public String url;
        public String language;
        public String group_id;
        public boolean is_default;
        public boolean is_auto_select;

        public String getLoadSubtitleFilePath(String parentPath, String fileName){
            return parentPath + File.separator + fileName;
        }

        public String getFileName(){
            // 从url中获取文件名
            // https://nv0.baidupcs.com/video/netdisk-embedded-subtitle/025389a22tfe15c15f365d03a8c1e565_77824_11_srt/3718212376fbbd028d85cc0dd3d60612?app_id=42733013&csl=0&dp-logid=318100431300778633&fn=%E9%A9%B1%E9%AD%94%E8%AD%A6%E5%AF%9F.mkv&from_type=3&fsid=1012842632300030&iot_dev=0&iot_vip=0&iv=2&logid=318100431300778633&mtime=1700715019&ouk=3436470027&r=245709529&size=394952509&sta_cs=45923&sta_dt=video&sta_dx=376&time=1703780987&to=hsn00&tot=c2lp4&uo=cnc&uva=2862637690&vuk=3436470027&etag=3718212376fbbd028d85cc0dd3d60612&fid=21469e3f1ba03b45a6d8aaf6cd311e20-3436470027&len=89973&range=0-89972&region=xian&resv4=&sign=BOUTRFPQV-F3530edecde9cd71b79378b290804a96-8m0r%252FPRGf%252FgAfDTp2gDdLK8O4b4%253D&xcode=52add9a266b838c6c153a6854201accfb8ee6228a0debbe783c10751caa58d3ac87e53e1ffab9d4907bbd472bc39f57e316128a2cdfcce4d&xv=6&need_suf=&pmk=&by=my-streaming
            // parse url to get 3718212376fbbd028d85cc0dd3d60612
            if (url == null || url.isEmpty()) return "";
            int removeIndex = url.indexOf("?");
            url = url.substring(0, removeIndex);
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            return fileName;
        }
    }
}
