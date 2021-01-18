package com.fikrimulyap.kelompok6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class fragmentAbout extends Fragment {

    View view;
    Button btnBackFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about, container, false);
        // get the reference of Button
        btnBackFragment = (Button) view.findViewById(R.id.btnBackFragment);
        // perform setOnClickListener on first Button
        btnBackFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(fragmentAbout.this).commit();
            }
        });
        return view;
    }
}