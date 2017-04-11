package com.hd.getpackageinfo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hd.studyandtest.R;

import java.util.ArrayList;

public class ActivityGetPackageInfo extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_get_package_info);


    }
    @Override
    public void onClick(View v) {
        ArrayList<String> arrayActivities = new ArrayList<String>();
        PackageManager pm = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = pm.getPackageInfo("com.hd.studyandtest", PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ActivityInfo activitys[] = packageInfo.activities;
        if (activitys == null) {
            activitys = packageInfo.activities;
        }

        for (ActivityInfo info : activitys) {
            String activityname=info.name.substring(info.name.lastIndexOf(".")+1,info.name.length());
            arrayActivities.add(activityname);
            Log.v("test", activityname + "------------------");
        }
        Toast.makeText(this,arrayActivities.toString(),Toast.LENGTH_SHORT).show();
    }
    /**
     * 获得某个应用程序包的主Activity
     *
     * @param ctx
     * @return
     */
    public static String getLunchActivity(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        Intent it = pm.getLaunchIntentForPackage("com.hd.studyandtest");
        String className = it.getComponent().getClassName();
        System.out.println(className);
        return className;
    }
}
