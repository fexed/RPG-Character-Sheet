package com.fexed.rpgsheet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

public class CharacterActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle saveBundle) {
        super.onCreate(saveBundle);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        // return true so that the menu pop up is opened
        return true;
    }
}
