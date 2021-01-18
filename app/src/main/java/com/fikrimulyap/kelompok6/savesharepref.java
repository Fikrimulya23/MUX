package com.fikrimulyap.kelompok6;

import android.content.Context;
import android.content.SharedPreferences;

public class savesharepref {
    Context context;
    SharedPreferences sharedPreferences;

    public savesharepref (Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences ("apreferences", Context.MODE_PRIVATE);
    }

    public void setsstate (Boolean bo) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("editor",bo);
        editor.apply();
    }

    public boolean getstate (){
        return sharedPreferences.getBoolean("editor", false);
    }
}
