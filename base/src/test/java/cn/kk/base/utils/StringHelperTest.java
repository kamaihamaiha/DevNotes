package cn.kk.base.utils;


import org.junit.Assert;
import org.junit.Test;

public class StringHelperTest {

    @Test
    public void testParse(){
        String testUrl = "https://openapi.baidu.com/oauth/2.0/login_success#expires_in=2592000&access_token=123.4177eb716eec044b1278d92268391b91.Y3lc2VTqNsoWKgJ4c-8M4Sq1jqOQDt6XtqA0jfD.UjUzlw&session_secret=&session_key=&scope=basic+netdisk";

        String params = StringHelper.parseParamsFromUrl(testUrl);
        Assert.assertEquals("2592000", params);
    }
}