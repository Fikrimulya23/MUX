package com.fikrimulyap.kelompok6;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;

public class Search extends AppCompatActivity {

    savesharepref pref;
    ListView allMusicList; // listVIEW
    ArrayAdapter<String> musicArrayAdapter; // Adapter for music list
    String songs[]; // to storage song names;
    ArrayList<File> musics;
    EditText edt_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        pref = new savesharepref(this);

        if(pref.getstate() ==true){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        else{
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        }




        // casting listview
        allMusicList = findViewById(R.id.listView);

        edt_search = (EditText) findViewById(R.id.edt_search);


        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
//        ArrayList<Music> musicList = new ArrayList<>();
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.search:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                MainActivity.class));
                        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),
                                SettingActivity.class));
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                        return true;
                }

                return false;
            }
        });

        // working on dexter permission
        Dexter.withActivity(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                // display music files on storage

                musics = findMusicFiles(Environment.getExternalStorageDirectory());
                songs = new String[musics.size()];
                for (int i = 0; i < musics.size(); i++) {
                    songs[i] = musics.get(i).getName();
//                    musicList.add(new Music(songs[i]));
                }
                //MyListAdapter adapter = new MyListAdapter(getApplicationContext(),musicList);

                // here you are passing songs in the adapter;
                //musicArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, songs);
                //setting the adapter on listview

                musicArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_song, R.id.tvTitle, songs);

                allMusicList.setAdapter(musicArrayAdapter);

                //search atas
                edt_search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                        // When user changed the Text
                        Search.this.musicArrayAdapter.getFilter().filter(cs);
                    }
                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                                  int arg3) {
                        // TODO Auto-generated method stub

                    }
                    @Override
                    public void afterTextChanged(Editable arg0) {
                        // TODO Auto-generated method stub
                    }
                });
//search bawah

                allMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // passing the intent to the playeractivity

                        startActivity(new Intent(Search.this, MusicPlayerActivity.class)

                                // we are storing all the songs in the key songlist
                                // we are storing the position of songs in key position
                                .putExtra("songsList", musics)
                                .putExtra("position", position));
                    }
                });

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                // asking for permission

                permissionToken.continuePermissionRequest();

            }
        }).check();

    }
// creating an arraylist for music files available on sotrage

    private ArrayList<File> findMusicFiles(File file) {
        ArrayList<File> musicfileobject = new ArrayList<>();
        File[] files = file.listFiles();

        for (File currentFiles : files) {

            if (currentFiles.isDirectory() && !currentFiles.isHidden()) {
                musicfileobject.addAll(findMusicFiles(currentFiles));
            } else {
                if (currentFiles.getName().endsWith(".mp3") || currentFiles.getName().endsWith(".mp4a") || currentFiles.getName().endsWith(".wav")) {
                    musicfileobject.add(currentFiles);
                }
            }
        }

        return musicfileobject;
    }
}