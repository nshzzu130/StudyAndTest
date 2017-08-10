package com.hd.imei;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hd.studyandtest.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityImei extends AppCompatActivity {

    @Bind(R.id.button)
    Button button;
    @Bind(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imei);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.button, R.id.textView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                textView.setText(((TelephonyManager) getSystemService(TELEPHONY_SERVICE)) .getDeviceId());
                break;
            case R.id.textView:
                break;
        }
    }
}
