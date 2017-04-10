package com.hd.waveview;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hd.studyandtest.R;

public class ActivityWave extends AppCompatActivity {
    LD_WaveView waveView;//方形
    LD_WaveView waveCircleView;//圆形
    private int progrees=0;//进度
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (progrees==100) progrees=0;
            Log.i("progress",progrees+"");
            waveView.setmProgress(progrees++);
            waveCircleView.setmProgress(progrees++);
            mHandler.sendEmptyMessageDelayed(0,100);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_wave);
        waveView= (LD_WaveView) findViewById(R.id.waveView);
        waveCircleView= (LD_WaveView) findViewById(R.id.waveViewCircle);
        mHandler.sendEmptyMessageDelayed(0,2);
        init();
    }
    private void init() {
        RelativeLayout layout=(RelativeLayout) findViewById(R.id.root);
        final DrawView view=new DrawView(this);
        view.setMinimumHeight(500);
        view.setMinimumWidth(300);
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(0);
    }
}
