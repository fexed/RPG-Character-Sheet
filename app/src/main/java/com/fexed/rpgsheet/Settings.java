package com.fexed.rpgsheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        final SharedPreferences state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(R.string.settings));
        TextView vertxt = findViewById(R.id.vertext);

        vertxt.setText(BuildConfig.VERSION_NAME + "_" + BuildConfig.VERSION_CODE);
    }
}
