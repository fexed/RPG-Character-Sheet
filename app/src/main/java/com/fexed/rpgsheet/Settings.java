package com.fexed.rpgsheet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

public class Settings extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    SharedPreferences state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(R.string.settings));

        TextView vertxt = findViewById(R.id.vertext);
        vertxt.setText(BuildConfig.VERSION_NAME);

        Button resetbtn = findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(this);

        SwitchCompat lastchar = findViewById(R.id.switchloadlastchar);
        lastchar.setChecked(state.getBoolean("loadlastchar", false));
        lastchar.setOnCheckedChangeListener(this);

        SwitchCompat thresholds5e = findViewById(R.id.switch5eexp);
        thresholds5e.setChecked(state.getBoolean("5ethresholds", true));
        thresholds5e.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.resetbtn) {
            final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
            final TextView msg = new TextView(view.getContext());
            msg.setText(getString(R.string.resetconfirm));
            alert.setView(msg);
            alert.setNegativeButton(view.getContext().getString(R.string.annulla), null);
            final AlertDialog alertd = alert.create();
            alert.setTitle(getString(R.string.areusure));
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    state.edit().clear().apply();
                    alertd.dismiss();
                }
            });
            alert.show();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.switchloadlastchar) {
            state.edit().putBoolean("loadlastchar", b).apply();
        } else if (compoundButton.getId() == R.id.switch5eexp) {
            state.edit().putBoolean("5ethresholds", b).apply();
        }
    }
}
