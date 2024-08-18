package cn.kk.base.utils;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.StringDef;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import cn.kk.base.BaseApp;
import cn.kk.base.UIHelper;

public class RomUtils {
    private static final String TAG = "RomUtils";

    public static final String ROM_MIUI = "MIUI";
    public static final String ROM_EMUI = "EMUI";
    public static final String ROM_HUAWEI = "HUAWEI";
    public static final String ROM_HONOR = "HONOR";
    public static final String ROM_VIVO = "VIVO";
    public static final String ROM_OPPO = "OPPO";
    public static final String ROM_FLYME = "FLYME";
    public static final String ROM_SMARTISAN = "SMARTISAN";
    public static final String ROM_QIKU = "QIKU";
    public static final String ROM_LETV = "LETV";
    public static final String ROM_LENOVO = "LENOVO";
    public static final String ROM_NUBIA = "NUBIA";
    public static final String ROM_ZTE = "ZTE";
    public static final String ROM_COOLPAD = "COOLPAD";
    public static final String ROM_UNKNOWN = "UNKNOWN";

    public static final int PERSSION_WINDOW_ALERT = 0;
    public static final int PERSSION_BACKGROUND_ACTIVITY = 1;
    public static final int PERSSION_SCREEN_LOCK = 2;
    public static final int PERSSION_SCREEN_LOCK_AND_BACKGROUND = 3;


    @StringDef({
            ROM_MIUI, ROM_EMUI, ROM_VIVO, ROM_OPPO, ROM_FLYME,
            ROM_SMARTISAN, ROM_QIKU, ROM_LETV, ROM_LENOVO, ROM_ZTE,
            ROM_COOLPAD, ROM_HONOR ,ROM_UNKNOWN
    })
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RomName {
    }

    private static final String SYSTEM_VERSION_MIUI = "ro.miui.ui.version.name";
    private static final String SYSTEM_VERSION_EMUI = "ro.build.version.emui";
    private static final String SYSTEM_VERSION_HONOR = "ro.build.version.magic";
    private static final String SYSTEM_VERSION_VIVO = "ro.vivo.os.version";
    private static final String SYSTEM_VERSION_OPPO = "ro.build.version.opporom";
    private static final String SYSTEM_VERSION_OPLUS = "ro.build.version.oplusrom";
    private static final String SYSTEM_VERSION_FLYME = "ro.build.display.id";
    private static final String SYSTEM_VERSION_SMARTISAN = "ro.smartisan.version";
    private static final String SYSTEM_VERSION_LETV = "ro.letv.eui";
    private static final String SYSTEM_VERSION_LENOVO = "ro.lenovo.lvp.version";
    private static final String SYSTEM_VERSION_DUOQIN = "ro.duoqin.build.version";
    private static final String SYSTEM_PRODUCT_MANUFACTURER = "ro.product.manufacturer";

    private static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            Log.e(TAG, "Unable to read sysprop " + propName, ex);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    Log.e(TAG, "Exception while closing InputStream", e);
                }
            }
        }
        return line;
    }

    @RomName
    public static String getRomName() {
        if (isMiuiRom()) {
            return ROM_MIUI;
        }
        if (isEMUIRom()) {
            return ROM_EMUI;
        }
        if (isHonorRom()) {
            return ROM_HONOR;
        }
        if (isVivoRom()) {
            return ROM_VIVO;
        }
        if (isOppoRom()) {
            return ROM_OPPO;
        }
        if (isMeizuRom()) {
            return ROM_FLYME;
        }
        if (isSmartisanRom()) {
            return ROM_SMARTISAN;
        }
        if (is360Rom()) {
            return ROM_QIKU;
        }
        if (isLetvRom()) {
            return ROM_LETV;
        }
        if (isLenovoRom()) {
            return ROM_LENOVO;
        }
        if (isZTERom()) {
            return ROM_ZTE;
        }
        if (isCoolPadRom()) {
            return ROM_COOLPAD;
        }
        return ROM_UNKNOWN;
    }

    public static boolean isMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_MIUI));
    }

    //华为联运标示

    public static String huaweiChannel;


    //华为墨水屏
    public static String huaweiInkChannel;
    public static boolean isHuaweiInkChannel(Context context)
    {
        if (huaweiInkChannel == null)
        {
            try {
                Class<?> pms = Class.forName("com.huawei.android.app.PackageManagerEx");
                Method method = pms.getDeclaredMethod("hasHwSystemFeature", String.class);
                Object result = method.invoke(pms, "com.huawei.hardware.screen.type.eink");
                if (Boolean.parseBoolean(result.toString()))
                {
                    huaweiInkChannel = "huawei_ink";
                }
            } catch (Exception e) {
                return false;
            }
        }
        return ("huawei_ink".equals(huaweiInkChannel));
    }


    public static String xiaomiChannel;

    public static boolean isMiuiWidgetSupported(Context context) {
        Uri uri =
                Uri.parse("content://com.miui.personalassistant.widget.external");
        boolean isMiuiWidgetSupported = false;
        try {
            Bundle bundle = context.getContentResolver().call(uri,
                    "isMiuiWidgetSupported", null, null);
            if (bundle != null) {
                isMiuiWidgetSupported = bundle.getBoolean("isMiuiWidgetSupported");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMiuiWidgetSupported;
    }

    public static boolean isHonorWidgetSupport() {
        return isHonorRom() && isMagicUI6_0();
    }

    public static boolean isEMUIRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_EMUI));
    }

    public static boolean isHonorRom() {
        return ROM_HONOR.equals(getSystemProperty(SYSTEM_PRODUCT_MANUFACTURER));
    }

    public static boolean isHuaweiDevice() {
        return Build.MANUFACTURER.equalsIgnoreCase(ROM_HUAWEI);
    }
    public static boolean isHonorPadDevice(){
       return isHonorDevice() && "tablet".equals(getSystemProperty("ro.build.characteristics"));
    }

    public static boolean isHonorNewDevice(){
        if (isHonorDevice() && !isHonorOldDevice()) return true; // 新荣耀产品: 无 HMS预装
        return false; // 21年-23年 荣耀产品: 有 HMS 预装
    }

    public static boolean isHonorDevice(){
        return Build.MANUFACTURER.equalsIgnoreCase(ROM_HONOR);
    }

    public static boolean isHonorOldDevice(){
        String emotionsOS = getSystemProperty(SYSTEM_VERSION_EMUI);
        if (!TextUtils.isEmpty(emotionsOS) && (emotionsOS.contains("MagicUI") || emotionsOS.contains("MagicOS"))) return true;
        return false;
    }

    /**
     * 荣耀折叠屏判断
     * @return
     */
    public static boolean isHonorFoldableDisplay(){
        try {
            Class<?> screenManager = Class.forName("com.hihonor.android.fsm.HwFoldScreenManagerEx");
            Method isFoldable = screenManager.getMethod("isFoldable");
            boolean foldableFalg = (boolean) isFoldable.invoke(screenManager);
            return foldableFalg;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 荣耀折叠屏是否展开
     * @return
     */
    public static boolean isHonorFoldableFullState(){
        try {
            Class<?> screenManager = Class.forName("com.hihonor.android.fsm.HwFoldScreenManagerEx");
            Method getDisplayMode = screenManager.getMethod("getDisplayMode");
            Field displayModeFull = screenManager.getDeclaredField("DISPLAY_MODE_FULL");
            boolean displayModeFlag = ((int)getDisplayMode.invoke(screenManager)) == displayModeFull.getInt(screenManager);
            return displayModeFlag;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @return 是否是鸿蒙系统
     */
    public static boolean isHarmonyOS() {
        return !TextUtils.isEmpty(getHarmonOSVersion());
    }
    public static boolean isHarmonyOS3(){
        if (!RomUtils.isHuaweiDevice()) return false;
        return getHarmonOSVersion().startsWith("3");
    }
    public static boolean isGEHarmonyOS3(){
        if (!RomUtils.isHuaweiDevice()) return false;
        String harmonOSVersion = getHarmonOSVersion();
        if(TextUtils.isEmpty(harmonOSVersion)) return false;
        char versionChar = harmonOSVersion.charAt(0);
        if (Character.isDigit(versionChar)) {
            int version = Integer.parseInt(String.valueOf(versionChar));
            return version >= 3;
        }
        return false;
    }
    public static String getHarmonOSVersion() {
        try {
            Class spClz = Class.forName("android.os.SystemProperties");
            Method method = spClz.getDeclaredMethod("get", String.class);
            String value = (String) method.invoke(spClz, "hw_sc.build.platform.version");
            if (TextUtils.isEmpty(value)) {
                return "";
            }
            return value;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isVivoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_VIVO));
    }

    public static boolean isOppoRom() {
        String oppoVersion = getSystemProperty(SYSTEM_VERSION_OPPO);
        return !TextUtils.isEmpty(oppoVersion) || ROM_OPPO.equalsIgnoreCase(getSystemProperty(SYSTEM_PRODUCT_MANUFACTURER));
    }

    public static boolean isOplusRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_OPLUS));
    }

    public static boolean isMeizuRom() {
        String meizuFlymeOSFlag = getSystemProperty(SYSTEM_VERSION_FLYME);
        return !TextUtils.isEmpty(meizuFlymeOSFlag) && meizuFlymeOSFlag.toUpperCase().contains(ROM_FLYME);
    }

    public static boolean isSmartisanRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_SMARTISAN));
    }

    public static boolean is360Rom() {
        String manufacturer = Build.MANUFACTURER;
        return !TextUtils.isEmpty(manufacturer) && manufacturer.toUpperCase().contains(ROM_QIKU);
    }

    public static boolean isLetvRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LETV));
    }

    public static boolean isLenovoRom() {
        return !TextUtils.isEmpty(getSystemProperty(SYSTEM_VERSION_LENOVO));
    }

    public static boolean isCoolPadRom() {
        String model = Build.MODEL;
        String fingerPrint = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(model) && model.toUpperCase().contains(ROM_COOLPAD))
                || (!TextUtils.isEmpty(fingerPrint) && fingerPrint.toUpperCase().contains(ROM_COOLPAD));
    }

    public static boolean isZTERom() {
        String manufacturer = Build.MANUFACTURER;
        String fingerPrint = Build.FINGERPRINT;
        return (!TextUtils.isEmpty(manufacturer) && (fingerPrint.toUpperCase().contains(ROM_NUBIA) || fingerPrint.toUpperCase().contains(ROM_ZTE)))
                || (!TextUtils.isEmpty(fingerPrint) && (fingerPrint.toUpperCase().contains(ROM_NUBIA) || fingerPrint.toUpperCase().contains(ROM_ZTE)));
    }

    public static String getDuoQinSystemVersion() {
        String sysVersion = getSystemProperty(SYSTEM_VERSION_DUOQIN);
        if (sysVersion == null) return "";
        return sysVersion;
    }

    public static String getAndroidSystemVersion() {
        return "Android " + Build.VERSION.RELEASE;
    }

    public static boolean isSamsung() {
        return "Samsung".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isDomesticSpecialRom() {
        return RomUtils.isMiuiRom()
                || RomUtils.isEMUIRom()
                || RomUtils.isMeizuRom()
                || RomUtils.is360Rom()
                || RomUtils.isOppoRom()
                || RomUtils.isVivoRom()
                || RomUtils.isLetvRom()
                || RomUtils.isZTERom()
                || RomUtils.isLenovoRom()
                || RomUtils.isCoolPadRom();
    }

    public static boolean needAdaptAndroid_13() {
        return isAndroid_13OrAbove() && BaseApp.application.getApplicationContext().getApplicationInfo().targetSdkVersion >= 33;
    }

    public static boolean isAndroid_13OrAbove() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU;
    }

    public static boolean isAndroid_14OrAbove() {
//        return Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
        return Build.VERSION.SDK_INT >= 34;
    }
    public static boolean needAdaptAndroid_14() {
        return isAndroid_14OrAbove() && BaseApp.application.getApplicationContext().getApplicationInfo().targetSdkVersion >= 34;
    }

    /**
     * 判断 MagicUI6.0
     * @return
     */
    public static boolean isMagicUI6_0(){
        try {
            String uiCode = getSystemProperty("ro.build.magic_api_level");
        if (!TextUtils.isEmpty(uiCode) && Integer.parseInt(uiCode) >= 33) return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 应用权限受阻跳转优化
     * 支持机型:
     * FindX7系列(怎么判断)	 ColorOS14.0.1及以上
     * 手机名称	            设备ID	型号	    分辨率	    安卓版本
     * OPPO Find X7 Ultra	210	    PHY110	1440x3168	14及以上
     * Find N3 Flip	        98	    PHT110	1080x2520	14及以上
     * Reno10 Pro+	        73	    PHU110	1240x2772	14及以上
     * @return
     */
    public static boolean supportOppoPermissionDirectly() {
        if (!Build.MANUFACTURER.equalsIgnoreCase(ROM_OPPO)) {
            return false;
        }

        String model = Build.MODEL;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) return false;
        if (model.equals("PHY110") ) return true;
        if (model.equals("PHT110")) return true;
        if (model.equals("PHU110")) return true;
        return false;
    }

    private static boolean isIntentAvailable(Intent intent, Context context) {
        return intent != null && context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }

    private static boolean startActivitySafely(Intent intent, Context context) {
        if (isIntentAvailable(intent, context)) {
            try {
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                return true;
            } catch (Exception ex) {
            }
            return false;
        } else {
            return false;
        }
    }

    private static String TOAST_HINT = "";

    private static void showAlertToast(Context context) {
        if (TextUtils.isEmpty(TOAST_HINT)) {
            TOAST_HINT = "jump_to_page_error";
        }
        UIHelper.INSTANCE.toast(TOAST_HINT, context);
    }

    private static void applyCoolpadPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.yulong.android.seccenter", "com.yulong.android.seccenter.dataprotection.ui.AppListActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyLenovoPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.lenovo.safecenter", "com.lenovo.safecenter.MainTab.LeSafeMainActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyZTEPermission(Context context) {
        Intent intent = new Intent();
        intent.setAction("com.zte.heartyservice.intent.action.startActivity.PERMISSION_SCANNER");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyLetvPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AppActivity");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyVivoPermission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyOppoPermission(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packageName", context.getPackageName());
        intent.setAction("com.oppo.safe");
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        if (!startActivitySafely(intent, context)) {
            intent.setAction("com.color.safecenter");
            intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
            if (!startActivitySafely(intent, context)) {
                intent.setAction("com.coloros.safecenter");
                intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
                if (!startActivitySafely(intent, context)) {
                    showAlertToast(context);
                }
            }
        }
    }

    private static void apply360Permission(Context context) {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$OverlaySettingsActivity");
        if (!startActivitySafely(intent, context)) {
            intent.setClassName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
            if (!startActivitySafely(intent, context)) {
                showAlertToast(context);
            }
        }
    }

    private static void applyMiuiPermission(Context context) {
        Intent intent = getMiUiBeforeAndroidMIntent(context);
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }
    //在需要通过 通知 提醒用户开启权限的地方使用
    private static Intent getMiUiBeforeAndroidMIntent(Context context){
        Intent intent = new Intent();
        intent.setAction("miui.intent.action.APP_PERM_EDITOR");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("extra_pkgname", context.getPackageName());
        return intent;
    }

    private static Intent getMiUiBeginAndroidMIntent(Context context){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
        return intent;
    }

    private static void applyMeizuPermission(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity");
        intent.putExtra("packageName", context.getPackageName());
        if (!startActivitySafely(intent, context)) {
            showAlertToast(context);
        }
    }

    private static void applyHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity");
            intent.setComponent(comp);
            if (!startActivitySafely(intent, context)) {
                comp = new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity");
                intent.setComponent(comp);
                context.startActivity(intent);
            }
        } catch (SecurityException e) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager",
                    "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.Android.settings", "com.android.settings.permission.TabItem");
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            showAlertToast(context);
        }
    }

    private static void applySmartisanPermission(Context context) {
        Intent intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS_NEW");
        intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
        intent.putExtra("index", 17); //有版本差异,不一定定位正确
        if (!startActivitySafely(intent, context)) {
            intent = new Intent("com.smartisanos.security.action.SWITCHED_PERMISSIONS");
            intent.setClassName("com.smartisanos.security", "com.smartisanos.security.SwitchedPermissions");
            intent.putExtra("permission", new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW});
            if (!startActivitySafely(intent, context)) {
                showAlertToast(context);
            }
        }
    }

    private static void applyCommonPermission(Context context) {
        try {
            Intent intent = getCommonIntent(context);
            startActivitySafely(intent, context);
        } catch (Exception e) {
            showAlertToast(context);
        }
    }

    public static Intent getCommonIntent(Context context){
        if (isMiuiRom()){
            return getMiUiBeginAndroidMIntent(context);
        }
        try {
            Class clazz = Settings.class;
            Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
            Intent intent = new Intent(field.get(null).toString());
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            return intent;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }

    public static void tryJumpToPermissonPage(Context context, int type){

        if (type == PERSSION_WINDOW_ALERT){
            alertPermission(context);
        }else if (isMiuiRom()){  //小米手机进入三种权限的方式一样， 其他手机还不确定
            applyMiuiPermission(context);
        }

    }

    private static void alertPermission(Context context){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            switch (RomUtils.getRomName()) {
                case RomUtils.ROM_MIUI:
                    applyMiuiPermission(context);
                    break;
                case RomUtils.ROM_EMUI:
                    applyHuaweiPermission(context);
                    break;
                case RomUtils.ROM_VIVO:
                    applyVivoPermission(context);
                    break;
                case RomUtils.ROM_OPPO:
                    applyOppoPermission(context);
                    break;
                case RomUtils.ROM_QIKU:
                    apply360Permission(context);
                    break;
                case RomUtils.ROM_SMARTISAN:
                    applySmartisanPermission(context);
                    break;
                case RomUtils.ROM_COOLPAD:
                    applyCoolpadPermission(context);
                    break;
                case RomUtils.ROM_ZTE:
                    applyZTEPermission(context);
                    break;
                case RomUtils.ROM_LENOVO:
                    applyLenovoPermission(context);
                    break;
                case RomUtils.ROM_LETV:
                    applyLetvPermission(context);
                    break;
                default:
                    UIHelper.INSTANCE.toast(TOAST_HINT, context);
                    break;
            }
        } else {
            if (RomUtils.isMeizuRom()) {
                applyMeizuPermission(context);
            } else {
                applyCommonPermission(context);
            }
        }
    }
    //兼容听力
    public static void tryJumpToPermissonPage(Context context) {
        tryJumpToPermissonPage(context, PERSSION_WINDOW_ALERT);
    }

}