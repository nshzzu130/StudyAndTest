package com.hd.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.widget.Toast;

import com.hd.studyandtest.R;

public class ActivityPreference extends PreferenceActivity {
    private static final String TAG = "ActivityPreference";
    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.activity_activity_preference);
        mContext=this;
/*
        PreferenceScreen preferenceScreen = getPreferenceManager()
                .createPreferenceScreen(this);
        setPreferenceScreen(preferenceScreen);
        CheckBoxPreference checkBoxPreference = new CheckBoxPreference(this);
        checkBoxPreference.setKey("TestKey");
        checkBoxPreference.setTitle("TestTitle");
        checkBoxPreference.setSummary("TestSummary");
        checkBoxPreference.setSummaryOff("TestSummaryOff");
        checkBoxPreference.setSummaryOn("TestSummaryOn");
        checkBoxPreference.setChecked(false);
        preferenceScreen.addPreference(checkBoxPreference);*/

        //CheckBoxPreference组件
        CheckBoxPreference mCheckbox0 = (CheckBoxPreference) findPreference("show_lijinwei_pref");
        mCheckbox0.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //这里可以监听到这个CheckBox 的点击事件
                Log.i(TAG, "onPreferenceClick: show_lijinwei_pref");
                return true;
            }
        });
        mCheckbox0.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference arg0, Object newValue) {
                //这里可以监听到checkBox中值是否改变了
                //并且可以拿到新改变的值
                Toast.makeText(mContext, "checkBox_0改变的值为" + String.valueOf(newValue), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onPreferenceChange: show_lijinwei_pref");
                return true;
            }
        });
        CheckBoxPreference mCheckbox1 = (CheckBoxPreference) findPreference("show_chenjiakuan_pref");
        mCheckbox1.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                //这里可以监听到这个CheckBox 的点击事件
                Log.i(TAG, "onPreferenceClick: show_chenjiakuan_pref");
                return true;
            }
        });
        mCheckbox1.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference arg0, Object newValue) {
                //这里可以监听到checkBox中值是否改变了
                //并且可以拿到新改变的值
                Toast.makeText(mContext, "checkBox_1改变的值为" + String.valueOf(newValue), Toast.LENGTH_LONG).show();
                Log.i(TAG, "onPreferenceChange: show_chenjiakuan_pref");
                return true;
            }
        });
        Log.i(TAG, "onCreate: value="+getSharedPreferences("com.hd.studyandtest_preferences",MODE_PRIVATE).getBoolean("show_lijinwei_pref",true));

        /*haredPreferences spf=getSharedPreferences("sf",MODE_PRIVATE);
        spf.edit().putBoolean("test",false).commit();*/

    }
}
