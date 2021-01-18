package com.fikrimulyap.kelompok6;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import android.app.FragmentManager;

//import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ListView allMusicList; // listVIEW
    ArrayAdapter<String> musicArrayAdapter; // Adapter for music list
    String songs[]; // to storage song names;
    ArrayList<File> musics;
    ImageView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // casting listview
//        MyListAdapter adapter = new MyListAdapter(this, songs);

        allMusicList = findViewById(R.id.listView);

        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);
        ArrayList<Music> musicList = new ArrayList<>();
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform itemselectedlistener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),
                                Search.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:

                        return true;

                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(),
                                SettingActivity.class));
                        overridePendingTransition(0,0);
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
                    musicList.add(new Music(songs[i]));
                }
                MyListAdapter adapter = new MyListAdapter(getApplicationContext(),musicList);

                // here you are passing songs in the adapter;
                musicArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1, songs);
                //setting the adapter on listview

//                allMusicList.setAdapter(musicArrayAdapter);

                allMusicList.setAdapter(adapter);

                allMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // passing the intent to the playeractivity

                        startActivity(new Intent(MainActivity.this, MusicPlayerActivity.class)

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

        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        about = findViewById(R.id.btnAbout);
        about.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // load First Fragment
//                fragmentAbout fragment = new fragmentAbout();
//                fragmentTransaction.add(R.id.frame_layout, fragment);
//                fragmentTransaction.commit();

                fragmentAbout newFragment = new fragmentAbout();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.frame_layout, newFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
    }

    // creating an arraylist for music files available on sotrage

    private ArrayList<File> findMusicFiles (File file) {
        ArrayList<File> musicfileobject = new ArrayList<>();
        File [] files = file.listFiles();

        for (File currentFiles: files) {

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