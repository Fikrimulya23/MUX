package com.fikrimulyap.kelompok6;

import android.app.Activity;
import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyListAdapter extends ArrayAdapter<Music> {

//    private final Activity context;
//    private final String[] songs;
//
//    public MyListAdapter(Activity context, String[] songs) {
//        super(context, R.layout.item_song, songs);
//
//        this.context=context;
//        this.songs = songs;
//
//    }
    private Context mContext;
    private List<Music> musicList = new ArrayList<>();

    public MyListAdapter(@NonNull Context context, ArrayList<Music> list) {
        super(context , 0,list);
        mContext = context;
        musicList = list;
    }


//    public MyListAdapter(@NonNull Context context,ArrayList<Music> list) {
//        super(context, list);
//        mContext = context;
//        musicList = list;
//    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item_song,parent,false);

        Music currentMusic = musicList.get(position);

        TextView textTitle = (TextView) listItem.findViewById(R.id.tvTitle);
        textTitle.setText(currentMusic.getmTitle());

        return listItem;
    }

//    public View getView(int position, View view, ViewGroup parent) {
//        LayoutInflater inflater = context.getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.item_song, parent,true);
//        Music currentMusic = songs.get(position);
//
//        TextView titleText = (TextView) rowView.findViewById(R.id.tvTitle);
//        titleText.setText(currentMusic.getmTitle());
//
//        titleText.setText(songs[position]);
//        return rowView;
//
//    };
}
