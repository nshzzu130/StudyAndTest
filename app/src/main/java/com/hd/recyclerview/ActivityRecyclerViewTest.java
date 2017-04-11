package com.hd.recyclerview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hd.actionbar.ActivityActionBar;
import com.hd.cardview.ActivityCardView;
import com.hd.coordinatorlayout.ActivityCoordinatorLayout;
import com.hd.gridlayout.ActivityGridLayout;
import com.hd.navigationview.ActivityNavigationView;
import com.hd.palette.ActivityTestPalette;
import com.hd.preference.ActivityPreference;
import com.hd.rxjavaandretrofit.ActivityRxjavaTest;
import com.hd.studyandtest.R;
import com.hd.supportlibdemo.ActivitySupportLibDemo;
import com.hd.supportlibdemo.SubActivity;
import com.hd.waveview.ActivityWave;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecyclerViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_recycler_view_test);
        //final List<ListData> datas=new ArrayList<>();
        final List<ListData> datas=getActivitys();
        /*datas.add(new ListData("actionbar", ActivityActionBar.class));
        datas.add(new ListData("cardview", ActivityCardView.class));
        datas.add(new ListData("coordinatorlayout", ActivityCoordinatorLayout.class));
        datas.add(new ListData("gridlayout", ActivityGridLayout.class));
        datas.add(new ListData("navigationview", ActivityNavigationView.class));
        datas.add(new ListData("palette", ActivityTestPalette.class));
        datas.add(new ListData("preference", ActivityPreference.class));
        datas.add(new ListData("rxjavaandretrofit", ActivityRxjavaTest.class));
        datas.add(new ListData("sppportlibdemo", ActivitySupportLibDemo.class));
        datas.add(new ListData("waveview", ActivityWave.class));*/

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.myrecyclerview);
        LinearLayoutManager layoutmanager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //StaggeredGridLayoutManager layoutmanager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//2列
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(new XRecyclerViewAdapter<ListData,XRecyclerViewAdapter.XViewHolder>(this,R.layout.customholder,datas) {
            @Override
            public void BindData(XRecyclerViewAdapter.XViewHolder holder, ListData s) {
                holder.setText(R.id.custom_text,s.name);
                //holder.setText(R.id.custom_btn,s.age+"");
            }

            @Override
            public void onItemClick(ViewGroup parent, View v, ListData s, int position) {
                Log.e("123", "onItemClick: position="+position+",s.name="+s.name+",s.age="+s.classname);
                Intent intent= new Intent(ActivityRecyclerViewTest.this,datas.get(position).classname);
                startActivity(intent);
            }
        });
    }
    private List<ListData>getActivitys(){
        ArrayList<ListData> arrayActivities = new ArrayList<>();
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
            String activityname = info.name;
            if(activityname.equals("com.hd.supportlibdemo.SubActivity")
                    ||activityname.equals("com.hd.studyandtest.MainActivity")
                    ||activityname.equals("com.hd.annotation.ActivityPerson")){
                continue;
            }
            try {
                Class<?> classname=Class.forName(activityname);

                activityname=activityname.substring(info.name.lastIndexOf(".") + 1, info.name.length());
                arrayActivities.add(new ListData(activityname,classname));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Log.v("test", activityname + "------------------");
        }
        return arrayActivities;
    }
    class ListData{
        public ListData(String name,Class<?> classname){
            this.name=name;
            this.classname=classname;
        }
        public String name;
        public Class<?> classname;
    }
}
