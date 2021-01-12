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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//import android.support.v7.app.AppCompatActivity;

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener {



    private static final int REQUEST_PERMISSION = 99;

    ArrayList<Song> songArrayList;
    ListView lvSongs;
    SongsAdapter songsAdapter;
//    SearchView search_view;
//    ArrayAdapter<Song> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        lvSongs = findViewById(R.id.lvSongs);

        songArrayList = new ArrayList<>();
//        search_view = (SearchView) findViewById(R.id.search_view);
        songsAdapter = new SongsAdapter(this, songArrayList);

//        adapter = new ArrayAdapter<Song>(getApplicationContext(),
//                R.layout.item_song, R.id.tvTitle, songArrayList);

        lvSongs.setAdapter(songsAdapter);
//        search_view.setOnQueryTextListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return;
        } else {
            getSongs();
        }
        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Song song = songArrayList.get(position);
                Intent openMusicPlayer = new Intent(Search.this,MusicPlayerActivity.class);
                openMusicPlayer.putExtra("song", song);
                startActivity(openMusicPlayer);
            }
        });

        //initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Navigation);

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
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.library:
                        startActivity(new Intent(getApplicationContext(),
                                Library.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });
    }
//there
//    @Override
//    public boolean onQueryTextChange(String newText){
//        adapter.getFilter().filter(newText);
//        return false;
//    }
//there
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSongs();
            }
        }
    }

    private void getSongs() {

        ContentResolver contentResolver = getContentResolver();
        Uri songUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);
        if (songCursor != null && songCursor.moveToFirst()) {

            int indexTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int indexArtist= songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int indexData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                String title = songCursor.getString(indexTitle);
                String artist = songCursor.getString(indexArtist);
                String path = songCursor.getString(indexData);
                songArrayList.add(new Song(title, artist, path));
            } while (songCursor.moveToNext());
        }

        songsAdapter.notifyDataSetChanged();
    }
}