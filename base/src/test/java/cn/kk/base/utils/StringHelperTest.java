package cn.kk.base.utils;


import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StringHelperTest {

    @Test
    public void testParse(){
        String testUrl = "https://openapi.baidu.com/oauth/2.0/login_success#expires_in=2592000&access_token=123.4177eb716eec044b1278d92268391b91.Y3lc2VTqNsoWKgJ4c-8M4Sq1jqOQDt6XtqA0jfD.UjUzlw&session_secret=&session_key=&scope=basic+netdisk";

        String params = StringHelper.parseParamsFromUrl(testUrl);
        Assert.assertEquals("2592000", params);
    }

    @Test
    public void testNumFormat(){
        HashMap<Integer, String> nums = new HashMap<>();
        nums.put(1, "");
        nums.put(9, "");
        nums.put(10, "100+");
        nums.put(99, "100+");
        nums.put(100, "100+");
        nums.put(123, "100+");
        nums.put(1234, "1000+");
        nums.put(12345, "10000+");
        nums.put(123456, "12万");
        nums.put(128456, "13万");

        for (Map.Entry<Integer, String> entry : nums.entrySet()) {
            Assert.assertEquals(entry.getValue(), StringHelper.formatNum(entry.getKey()));
        }
    }
}