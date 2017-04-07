package com.hd.palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;

import com.hd.studyandtest.R;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ActivityTestPalette extends AppCompatActivity {
    Bitmap bitmap=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_test_palette);

        //ActionBar actionBar=getSupportActionBar();


        //final Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        Observable.just(R.mipmap.ic_launcher).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer integer) {
                bitmap=BitmapFactory.decodeResource(getResources(),integer);
                return bitmap;
            }
        }).map(new Func1<Bitmap, Palette>() {
            @Override
            public Palette call(Bitmap bitmap) {
                return Palette.generate(bitmap);
            }
        }).map(new Func1<Palette, Palette.Swatch>() {
            @Override
            public Palette.Swatch call(Palette palette) {
                Palette.generate(bitmap);
                // 提取完毕
                // 有活力的颜色
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                // 有活力的暗色
                Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                // 有活力的亮色
                Palette.Swatch lightVibrant = palette.getLightVibrantSwatch();
                // 柔和的颜色
                Palette.Swatch muted = palette.getMutedSwatch();
                // 柔和的暗色
                Palette.Swatch darkMuted = palette.getDarkMutedSwatch();
                // 柔和的亮色
                Palette.Swatch lightMuted = palette.getLightMutedSwatch();

                // 使用颜色
                // 修改Actionbar背景颜色
                //ActivityTestPalette.this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(vibrant.getRgb()));
                // 修改文字的颜色
                // 根据需求选择不同效果的颜色应用
                return vibrant;
            }
        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Palette.Swatch>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Palette.Swatch swatch) {
                ActivityTestPalette.this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
            }
        });

    }
}
