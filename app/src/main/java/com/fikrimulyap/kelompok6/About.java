package com.fikrimulyap.kelompok6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class About extends AppCompatActivity {

    savesharepref pref;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
//        overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);
        back  = findViewById(R.id.btnback);
        pref = new savesharepref(this);

        if(pref.getstate() ==true){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TODO Auto-generated method stub
//                Intent moveWithDataIntent = new Intent(MusicPlayerActivity.this, MainActivity.class);
//                moveWithDataIntent.putExtra(MusicPlayerActivity.musicPlayer);
//                startActivity(moveWithDataIntent);

                startActivity(new Intent(About.this, SettingActivity.class));
//                overridePendingTransition(R.anim.slide_in_top,R.anim.slide_out_bottom);

                finish();

                //menggunakan intent untuk berpindah ke activity sebelumnya
            }
        });
    }
}
