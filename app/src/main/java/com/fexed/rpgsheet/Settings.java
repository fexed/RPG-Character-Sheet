package com.fexed.rpgsheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final SharedPreferences state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(R.string.settings));
    }
}
