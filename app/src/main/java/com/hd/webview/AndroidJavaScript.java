package com.hd.webview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by hudong on 2017/7/12.
 */
public class AndroidJavaScript {
    Context c;
    String[] qqpackage = new String[] { "com.tencent.mobileqq",
            "com.tencent.mobileqq.activity.SplashActivity" };
    String[] wxpackage = new String[] { "com.tencent.mm",
            "com.tencent.mm.ui.LauncherUI" };

    public AndroidJavaScript(Context c) {
        this.c = c;
    }

    @JavascriptInterface
    public void callPhone(final String telphone) {

//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
//                + telphone));
//        c.startActivity(intent);
        Toast.makeText(c,"tel:"+ telphone,Toast.LENGTH_LONG).show();

    }

    @JavascriptInterface
    public void callQQ(String qq) {
        // 实现调用电话号码

        if (!checkBrowser(qqpackage[0])) {

        } else {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName(qqpackage[0], qqpackage[1]);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            c.startActivity(intent);
        }

    }

    @JavascriptInterface
    public void callWeixin(String weixin) {

        if (!checkBrowser(wxpackage[0])) {

        } else {
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName(wxpackage[0], wxpackage[1]);
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            c.startActivity(intent);

        }

    }

    // 获取在webview上获取js生成的html的源码
    @JavascriptInterface
    public void getSource(String htmlstr) {
        // Log.e("html", htmlstr);
        // String path = c.getFilesDir().getAbsolutePath() + "/serve.html"; //
        // data/data目录

    }
    //检测包名的应用是否已经安装在手机
    public boolean checkBrowser(String packageName) {
//        if (packageName == null || "".equals(packageName))
//            return false;
//        try {
//            ApplicationInfo info = c.getPackageManager().getApplicationInfo(
//                    packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
        return false;
    }
}
