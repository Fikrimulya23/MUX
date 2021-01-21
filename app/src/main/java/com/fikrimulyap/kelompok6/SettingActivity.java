package com.fikrimulyap.kelompok6;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    Switch aSwitch;
    savesharepref pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch=findViewById(R.id.switch1);
        pref = new savesharepref(this);

        if(pref.getstate() ==true){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

        if (pref.getstate()==true){
            aSwitch.setChecked(true);
        }

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pref.setsstate(true);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }else{
                    pref.setsstate(false);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });

        Button about = findViewById(R.id.about);
        about.setOnClickListener(this);


        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.settings);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),
                                Search.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;

                    case R.id.settings:
                        return true;
                }

                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about:
                Intent moveIntent = new Intent(SettingActivity.this, About.class);
                startActivity(moveIntent);
                break;
        }
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_library);
//
//        //initialize and assign variable
//        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
//
//        //set home selected
//        bottomNavigationView.setSelectedItemId(R.id.settings);
//
//        //perform itemselectedlistener
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.home:
//                        startActivity(new Intent(getApplicationContext(),
//                                MainActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.search:
//                        startActivity(new Intent(getApplicationContext(),
//                                Search.class));
//                        overridePendingTransition(0,0);
//                        return true;
//
//                    case R.id.settings:
//                        return true;
//                }
//
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void onClick(View v) {
//
//    }
}
