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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ActivityGetPackageInfo extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_get_package_info);


    }
    @Override
    public void onClick(View v) {
        PackageManager pm = getPackageManager();
        Observable.just(pm).map(new Func1<PackageManager, PackageInfo>() {
            @Override
            public PackageInfo call(PackageManager packageManager) {
                try {
                    return packageManager.getPackageInfo("com.hd.studyandtest", PackageManager.GET_ACTIVITIES);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }).map(new Func1<PackageInfo, ActivityInfo[]>() {
            @Override
            public ActivityInfo[] call(PackageInfo packageInfo) {
                return packageInfo.activities;
            }
        }).subscribe(new Action1<ActivityInfo[]>() {
            @Override
            public void call(ActivityInfo[] activityInfos) {
                Toast.makeText(ActivityGetPackageInfo.this, Arrays.asList(activityInfos).toString(),Toast.LENGTH_SHORT).show();
            }
        });


               /* .flatMap(new Func1<PackageInfo, Observable<ActivityInfo>>() {
            @Override
            public Observable<ActivityInfo> call(PackageInfo packageInfo) {
                return Observable.from(packageInfo.activities);
            }
        }).subscribe(new Action1<ActivityInfo>() {
            @Override
            public void call(ActivityInfo activityInfo) {
                String activityname=activityInfo.name.substring(activityInfo.name.lastIndexOf(".")+1,activityInfo.name.length());
                //arrayActivities.add(activityname);
                Log.v("test", activityname + "------------------");
                Toast.makeText(ActivityGetPackageInfo.this,"activityname:"+activityname,Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(ActivityGetPackageInfo.this,"end",Toast.LENGTH_SHORT).show();
*/
        /*ArrayList<String> arrayActivities = new ArrayList<String>();
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
        Toast.makeText(this,arrayActivities.toString(),Toast.LENGTH_SHORT).show();*/
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
