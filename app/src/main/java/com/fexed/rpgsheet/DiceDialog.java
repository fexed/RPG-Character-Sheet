package com.fexed.rpgsheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class DiceDialog extends Dialog implements View.OnClickListener, View.OnLongClickListener {

    public Activity c;
    private TextView outtxt;
    private TextView histtxt;
    private Random rnd;
    private ArrayList<Integer> rolls;

    public DiceDialog(Activity a) {
        super(a);
        this.c = a;
        rnd = new Random(System.currentTimeMillis());
        rolls = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dicedialog);
        Button d20btn = findViewById(R.id.D20);
        d20btn.setOnClickListener(this);
        d20btn.setOnLongClickListener(this);
        Button d100btn = findViewById(R.id.D100);
        d100btn.setOnClickListener(this);
        d100btn.setOnLongClickListener(this);
        Button d12btn = findViewById(R.id.D12);
        d12btn.setOnClickListener(this);
        d12btn.setOnLongClickListener(this);
        Button d10btn = findViewById(R.id.D10);
        d10btn.setOnClickListener(this);
        d10btn.setOnLongClickListener(this);
        Button d8btn = findViewById(R.id.D8);
        d8btn.setOnClickListener(this);
        d8btn.setOnLongClickListener(this);
        Button d6btn = findViewById(R.id.D6);
        d6btn.setOnClickListener(this);
        d6btn.setOnLongClickListener(this);
        Button d4btn = findViewById(R.id.D4);
        d4btn.setOnClickListener(this);
        d4btn.setOnLongClickListener(this);
        Button coinbtn = findViewById(R.id.coin);
        coinbtn.setOnClickListener(this);

        outtxt = findViewById(R.id.diceRollResutlTxtV);
        histtxt = findViewById(R.id.diceRollHistoryTxtV);
    }

    @Override
    public void onClick(View v) {
        int max = 1;
        switch (v.getId()) {
            case R.id.D20:
                max = 20;
                break;
            case R.id.D100:
                max = 100;
                break;
            case R.id.D12:
                max = 12;
                break;
            case R.id.D10:
                max = 10;
                break;
            case R.id.D8:
                max = 8;
                break;
            case R.id.D6:
                max = 6;
                break;
            case R.id.D4:
                max = 4;
                break;
            case R.id.coin:
                max = -1;
                break;
        }

        if (max != -1) {
            String suff = "1D" + max;
            int result = rnd.nextInt(max) + 1;
            rolls.add(result);
            suff += " = " + result;
            outtxt.setText(suff);
            StringBuilder str = new StringBuilder();
            int total = rolls.get(0);
            str.append(rolls.get(0));
            for (int n : rolls.subList(1, rolls.size())) {
                str.append("+");
                str.append(n);
                total += n;
            }
            str.append("= ").append(total);
            histtxt.setText(str.toString());
        } else {
            if (rnd.nextBoolean()) outtxt.setText("Testa");
            else outtxt.setText("Croce");
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int max = 1;
        switch (v.getId()) {
            case R.id.D20:
                max = 20;
                break;
            case R.id.D100:
                max = 100;
                break;
            case R.id.D12:
                max = 12;
                break;
            case R.id.D10:
                max = 10;
                break;
            case R.id.D8:
                max = 8;
                break;
            case R.id.D6:
                max = 6;
                break;
            case R.id.D4:
                max = 4;
                break;
            case R.id.coin:
                max = -1;
                break;
        }

        if (max != -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Quanti D" + max + " lanciare?");
            final EditText input = new EditText(this.c);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            final int finalMax = max;
            builder.setPositiveButton("Lancia", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int howMany = Integer.parseInt(input.getText().toString());
                    String suff = howMany + "D" + finalMax;
                    int result;
                    int totalResult = 0;
                    for (int j = 0; j < howMany; j++) {
                        result = rnd.nextInt(finalMax) + 1;
                        rolls.add(result);
                        totalResult += result;
                    }
                    suff += " = " + totalResult;
                    outtxt.setText(suff);
                    StringBuilder str = new StringBuilder();
                    int total = rolls.get(0);
                    str.append(rolls.get(0));
                    for (int n : rolls.subList(1, rolls.size())) {
                        str.append("+");
                        str.append(n);
                        total += n;
                    }
                    str.append("= ").append(total);
                    histtxt.setText(str.toString());
                }
            });
            builder.show();
        } else {
            if (rnd.nextBoolean()) outtxt.setText("Testa");
            else outtxt.setText("Croce");
        }
        return true;
    }
}
