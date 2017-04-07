package com.hd.waveview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.hd.studyandtest.R;

/**
 * Created by hudong on 2016/12/30.
 */
public class CustomView extends View {
    private static final String TAG = "myview";
    private int x;
    private int y;
    private float r;
    private boolean isvisible=false;
    private Context context;
    public CustomView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs, R.styleable.custom_view);
        x=mTypedArray.getInt(R.styleable.custom_view_x,0);
        y=mTypedArray.getInt(R.styleable.custom_view_y,0);
        r=mTypedArray.getFloat(R.styleable.custom_view_r,0);

        Log.i(TAG, "myview: x="+x+",y="+y+",r="+r);

        init(context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray=context.obtainStyledAttributes(attrs,R.styleable.custom_view);
        x=mTypedArray.getInt(R.styleable.custom_view_x,0);
        y=mTypedArray.getInt(R.styleable.custom_view_y,0);
        r=mTypedArray.getFloat(R.styleable.custom_view_r,0);

        Log.i(TAG, "myview: x="+x+",y="+y+",r="+r);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG, "onMeasure: width="+getMeasuredWidth()+",height="+getMeasuredHeight());



    }

    int step=0;
    int begin=0;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8f);

        RectF mRectF=new RectF(x-r,y-r,x+r,y+r);
        mRectF=new RectF(x-3*r,y-3*r,x+3*r,y+3*r);




        //1
        //canvas.drawArc(mRectF,begin+=2,step+=2,true,paint);
        //2
       /* int slip=4;
        for(int i=0;i<slip/2;i++){
            canvas.drawArc(mRectF,begin+i*2*360/slip,360/slip,true,paint);
        }
        begin+=3;

        step=step%360;
        try {
            Thread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();*/
        //3
        /*canvas.drawArc(mRectF,0,90,true,paint);

        mRectF=new RectF(x-2*r,y-2*r,x+2*r,y+2*r);
        canvas.drawArc(mRectF,90,180,true,paint);

        mRectF=new RectF(x-3*r,y-3*r,x+3*r,y+3*r);
        canvas.drawArc(mRectF,180,270,true,paint);

        mRectF=new RectF(x-4*r,y-4*r,x+4*r,y+4*r);
        canvas.drawArc(mRectF,270,90,true,paint);*/



        Path path=new Path();
        /*path.lineTo(50,50);
        path.moveTo(0,0);
        path.lineTo(50,200);

        path.lineTo(50,50);*/
        path.reset();
        path.moveTo(step,50-step);
        int i=0;
        for(;i<4;i++){
            path.quadTo(50-100+100*i+step,i%2*100-step,100*(i+1)-100+step,50-step);
        }
        path.lineTo(100*i+step-100,200);

        path.lineTo(step-100,200);

        path.close();

        canvas.drawPath(path,paint);
/*

        Paint paint2=new Paint();
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.WHITE);
        paint2.setStrokeWidth(10);
        canvas.drawArc(new RectF(50,0,400,200),0,360,true,paint2);

        Paint circlePaint = new Paint();
        circlePaint.setStrokeWidth(5);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(175, 50, 50, circlePaint);
*/



        step=step%50+2;
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        invalidate();
    }
}
