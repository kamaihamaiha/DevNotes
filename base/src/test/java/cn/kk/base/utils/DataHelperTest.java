package cn.kk.base.utils;

import junit.framework.TestCase;
import java.util.Date;

public class DataHelperTest extends TestCase {

    public void testLocalizeMonthDay() {
        assertEquals("19 sept.", DateHelper.localizeMonthDay("es", new Date()));
    }
}