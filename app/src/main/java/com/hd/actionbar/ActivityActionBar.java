package com.hd.actionbar;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.hd.studyandtest.R;

public class ActivityActionBar extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_action_bar);


        Toolbar toolbar=(Toolbar)findViewById(R.id.myactionbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("MyActionTitle");
        actionBar.setSubtitle("subtitle");
        actionBar.setHomeAsUpIndicator(android.R.drawable.ic_delete);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }
}
