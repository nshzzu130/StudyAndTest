package com.hd.jdbc;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hd.studyandtest.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ActivityJDBC extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_jdbc);


    }
    Connection conn=null;
    Statement stmt=null;
    @Override
    public void onClick(View v) {
        Observable.just("com.mysql.jdbc.Driver").map(new Func1<String, ResultSet>() {
            @Override
            public ResultSet call(String classname) {
                //注册驱动
                try {
                    //注册驱动
                    //Class.forName("com.mysql.jdbc.Driver");
                    Class.forName(classname);
                    String url = "jdbc:mysql://60.205.213.50:3306/app";
                    //Connection conn = DriverManager.getConnection(url, "root", "4f5c1c94bc");
                    Connection conn = DriverManager.getConnection(url, "admin", "admin");
                    Statement stmt = conn.createStatement();
                    String sql = "select * from user";
                    ResultSet rs = stmt.executeQuery(sql);
                    return rs;
                } catch (ClassNotFoundException e) {
                    Log.v("yzy", "fail to connect!" + "  " + e.getMessage());
                } catch (SQLException e) {
                    e.printStackTrace();
                    Log.v("yzy", "fail to connect!" + "  " + e.getMessage());
                }
                return null;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ResultSet>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                try{
                    if(stmt!=null){
                        stmt.close();
                    }
                    if(conn!=null){
                        conn.close();
                    }
                }catch (SQLException sqle){
                    sqle.printStackTrace();
                }
            }

            @Override
            public void onNext(ResultSet rs){
                try {
                    while (rs.next()) {
                        Log.v("yzy", "field1-->" + rs.getString("account")+ "  field2-->" + rs.getString("name")+ "  field3-->" + rs.getString("password"));
                        Log.v("yzy", "field1-->" + rs.getString(1) + "  field2-->" + rs.getString(2)+ "  field3-->" + rs.getString(3));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }finally {
                    try{
                        if(stmt!=null){
                            stmt.close();
                        }
                        if(conn!=null){
                            conn.close();
                        }
                        if(rs!=null){
                            rs.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }
                Log.v("yzy", "success to connect!");
            }
        });


    }
}
