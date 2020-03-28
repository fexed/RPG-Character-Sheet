package com.fexed.rpgsheet;

import android.os.AsyncTask;

public class ReducePF extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
            while(CharacterActivity.plusPressed) {
                try { Thread.sleep(250); } catch (InterruptedException ignored) {}

            }
        return null;
    }
}
