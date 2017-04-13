package com.hd.navigationview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.hd.studyandtest.R;

public class ActivityNavigationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_navigation_view);
        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.my_drawerlayout);
        final NavigationView navigationView=(NavigationView)findViewById(R.id.my_navigation);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_info_details:
                        break;
                }
                return false;
            }
        });
        drawerLayout.openDrawer(navigationView);
    }

}
