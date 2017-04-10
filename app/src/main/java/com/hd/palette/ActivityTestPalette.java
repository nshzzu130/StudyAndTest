package com.hd.palette;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hd.studyandtest.R;

import java.io.FileNotFoundException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ActivityTestPalette extends AppCompatActivity implements View.OnClickListener{
    final String  TAG ="ActivityTestPalette";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_test_palette);
        changeHeaderTitleBackground(R.mipmap.ic_launcher);
    }
    private void changeHeaderTitleBackground(final int imgid){
        Observable.just(imgid)
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        return BitmapFactory.decodeResource(getResources(),imgid);
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        changeHeaderTitleBackground(bitmap);
                    }
                });
    }
    private void changeHeaderTitleBackground(Bitmap bitmap){
        Observable.just(bitmap).map(new Func1<Bitmap, Palette>() {
            @Override
            public Palette call(Bitmap bitmap) {
                return Palette.generate(bitmap);
            }
        }).map(new Func1<Palette, Palette.Swatch>() {
            @Override
            public Palette.Swatch call(Palette palette) {
                //Palette.generate(bitmap);
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
                //ActivityTestPalette.this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
                findViewById(R.id.text).setBackgroundColor(swatch.getRgb());

            }
        });
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
        intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
        intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
        startActivityForResult(intent, 1);

        changeHeaderTitleBackground(R.mipmap.ic_launcher);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult: requestCode="+requestCode+",resultCode="+resultCode+",data="+data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                ImageView imageView = (ImageView) findViewById(R.id.myimagview_palette);
                /* 将Bitmap设定到ImageView */
                imageView.setImageBitmap(bitmap);
                changeHeaderTitleBackground(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }
}
