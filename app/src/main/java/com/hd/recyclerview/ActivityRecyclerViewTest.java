package com.hd.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.hd.studyandtest.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityRecyclerViewTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_recycler_view_test);
        List<Student> datas=new ArrayList<>();

        datas.add(new Student("zhangsan",18));
        datas.add(new Student("lisi",18));
        datas.add(new Student("wangwu",18));
        datas.add(new Student("maliu",18));
        datas.add(new Student("zhaoqi",18));
        datas.add(new Student("maba",18));
        datas.add(new Student("hanxin",18));

        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.myrecyclerview);
        LinearLayoutManager layoutmanager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        //StaggeredGridLayoutManager layoutmanager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);//2列
        recyclerView.setLayoutManager(layoutmanager);
        recyclerView.setAdapter(new XRecyclerViewAdapter<Student,XRecyclerViewAdapter.XViewHolder>(this,R.layout.customholder,datas) {
            @Override
            public void BindData(XRecyclerViewAdapter.XViewHolder holder, Student s) {
                holder.setText(R.id.custom_text,s.name);
                holder.setText(R.id.custom_btn,s.age+"");
            }

            @Override
            public void onItemClick(ViewGroup parent, View v, Student s, int position) {
                Log.e("123", "onItemClick: position="+position+",s.name="+s.name+",s.age="+s.age);
            }
        });

    }
    class Student{
        public Student(String name,int age){
            this.name=name;
            this.age=age;
        }
        public String name;
        public int age;
    }
}
