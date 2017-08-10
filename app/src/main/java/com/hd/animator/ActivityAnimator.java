package com.hd.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.hd.studyandtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityAnimator extends AppCompatActivity {

    @Bind(R.id.mytext)
    TextView mytext;
    @Bind(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        ButterKnife.bind(this);
    }
    /**
     * @decrip:XML方式加载属性动画
     * @param：
     * @return:
    */
    private void animatorXml() {
        Animator animator=AnimatorInflater.loadAnimator(this,R.animator.animator);
        animator.setTarget(findViewById(R.id.mytext));
        animator.start();
    }

    /**
     * @decrip:不存在的属性也会有运行，只是没效果而已，几乎和valueAnimator ofFloat一样
     * @param：
     * @return:
     */
    public void rotateyAnimRuns(final View view) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "zhy", 1f, 0f, 1f);
        anim.setDuration(5000);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float cVal = (Float) animation.getAnimatedValue();
                view.setAlpha(cVal);
                view.setScaleX(cVal);
                view.setScaleY(cVal);
            }
        });
    }

    /**
     * @decrip:动画属性组合的使用方法
     * @param：
     * @return:
     */
    public void propertyValuesHolder(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f, 0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f, 0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f, 0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(1000).start();
    }

    /**
     * @decrip:属性动画，代码基本实现，并实现动画顺序组合
     * @param：
     * @return:
     */
    private void textAnimator() {
        TextView textview = (TextView) findViewById(R.id.mytext);
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(textview, "translationX", -200f, 0f);
        moveIn.setDuration(500);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(textview, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(textview, "alpha", 1f, 0f, 1f);

        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(2000);
        animSet.start();
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
    }

    /**
     * @decrip:基本的属性动画操作并验证属性动画是真的改变了位置
     * @param：
     * @return:
     */
    private void textObjectAnimator() {
        TextView textView = (TextView) findViewById(R.id.mytext);
//        float curTranslationX0 = textView.getTranslationX();
//        ObjectAnimator animator30 = ObjectAnimator.ofFloat(textView, "translationX", curTranslationX0, 500f);
//        animator30.setDuration(3000);
//        animator30.start();
//
//        float curTranslationY0 = textView.getTranslationY();
//        ObjectAnimator animator40 = ObjectAnimator.ofFloat(textView, "translationY", curTranslationY0, 500f);
//        animator40.start();
//
//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "alpha", 1f, 0f, 1f);
//        animator.setDuration(5000);
//        animator.start();
//
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(textView, "rotation", 0f, -360f);
//        animator2.setDuration(5000);
//        animator2.start();
//
//        float curTranslationX = textView.getTranslationX();
//        ObjectAnimator animator3 = ObjectAnimator.ofFloat(textView, "translationX", curTranslationX, -50f, curTranslationX);
//        animator3.setDuration(5000);
//        animator3.start();

        float curTranslationY = textView.getTranslationY();
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(textView, "translationY", curTranslationY, 500f, curTranslationY);
        animator4.setDuration(5000);
        animator4.setInterpolator(new AccelerateInterpolator());//设置先慢后快
        animator4.start();
    }

    /**
     * @decrip:测试值动画，主要验证动画改变时中间数据的的变化
     * @param：
     * @return:
     */
    private void testValueAnimator() {
        //ValueAnimator anim = ValueAnimator.ofFloat(0f, 1f);
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(10000);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //float currentValue = (float) animation.getAnimatedValue();
                int currentValue = (Integer) animation.getAnimatedValue();
                Log.d("TAG", "cuurent value is " + currentValue);
            }
        });
        anim.start();
    }

    @OnClick({R.id.mytext, R.id.button2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mytext:
                break;
            case R.id.button2:
                //testValueAnimator();
                textObjectAnimator();
                //textAnimator();
                //rotateyAnimRuns(findViewById(R.id.mytext));
                //propertyValuesHolder(findViewById(R.id.mytext));
                //animatorXml();
                break;
        }
    }
}
