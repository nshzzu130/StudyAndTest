package com.hd.svgpathview;

import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.eftimoff.androipathview.PathView;
import com.hd.studyandtest.R;
import com.mysql.jdbc.log.LogUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivitySvgPathView extends AppCompatActivity {

    @Bind(R.id.pathView)
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_path_view);
        ButterKnife.bind(this);
        int length=pathView.getWidth();
        int height=pathView.getHeight();

        final Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.lineTo(length / 4f, 0.0f);
        path.lineTo(length, height / 2.0f);
        path.lineTo(length / 4f, height);
        path.lineTo(0.0f, height);
        path.lineTo(length * 3f / 4f, height / 2f);
        path.lineTo(0.0f, 0.0f);
        path.close();

        pathView.setPath(path);



        pathView.getPathAnimator()
                .delay(100)
                .duration(500)
                .listenerStart(new AnimationListenerStart())
                .listenerEnd(new AnimationListenerEnd())
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    @OnClick(R.id.pathView)
    public void onViewClicked() {
    }

    private class AnimationListenerEnd implements PathView.AnimatorBuilder.ListenerEnd {
        @Override
        public void onAnimationEnd() {
            Log.i("123", "onAnimationEnd: ");
        }
    }

    private class AnimationListenerStart implements PathView.AnimatorBuilder.ListenerStart {
        @Override
        public void onAnimationStart() {
            Log.i("123", "onAnimationStart: ");
        }
    }
}
