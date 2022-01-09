package com.fexed.rpgsheet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Random;

public class DiceDialog extends Dialog implements View.OnClickListener, View.OnLongClickListener {

    public Activity c;
    private TextView outtxt;
    private TextView histtxt;
    private TextView nattxt;
    private Random rnd;
    private ArrayList<Integer> rolls;
    private boolean roll = false;
    private int bonus;
    private int max;
    private int dices;
    private String text = "";

    public DiceDialog(Activity a, SharedPreferences state) {
        super(a);
        this.c = a;
        rnd = new Random(System.currentTimeMillis());
        rolls = new ArrayList<>();
        state.edit().putBoolean("diceroller", true).apply();
        Bundle bndl = new Bundle();
        bndl.putInt("launchtimes", state.getInt("launchn", -1));
        FirebaseAnalytics.getInstance(c).logEvent("DiceRoller", bndl);
    }

    public DiceDialog(Activity a, SharedPreferences state, int bonus, String text) {
        super(a);
        this.c = a;
        rnd = new Random(System.currentTimeMillis());
        rolls = new ArrayList<>();
        state.edit().putBoolean("diceroller", true).apply();
        Bundle bndl = new Bundle();
        bndl.putInt("launchtimes", state.getInt("launchn", -1));
        FirebaseAnalytics.getInstance(c).logEvent("DiceRoller", bndl);

        this.roll = true;
        this.bonus = bonus;
        this.text = text;
        this.max = 20;
        this.dices = 1;
    }

    public DiceDialog(Activity a, SharedPreferences state, int dices, int max, int bonus, String text) {
        super(a);
        this.c = a;
        rnd = new Random(System.currentTimeMillis());
        rolls = new ArrayList<>();
        state.edit().putBoolean("diceroller", true).apply();
        Bundle bndl = new Bundle();
        bndl.putInt("launchtimes", state.getInt("launchn", -1));
        FirebaseAnalytics.getInstance(c).logEvent("DiceRoller", bndl);

        this.roll = true;
        this.bonus = bonus;
        this.text = text;
        this.max = max;
        this.dices = dices;
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
        ImageButton coinbtn = findViewById(R.id.coin);
        coinbtn.setOnClickListener(this);
        ImageView dimage = findViewById(R.id.diceIcon);
        outtxt = findViewById(R.id.diceRollResutlTxtV);
        histtxt = findViewById(R.id.diceRollHistoryTxtV);
        nattxt = findViewById(R.id.nattxtv);

        nattxt.setText("");

        dimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rnd = new Random(System.currentTimeMillis());
                rolls = new ArrayList<>();
                roll = false;
                histtxt.setText("");
                nattxt.setText("");
                outtxt.setText(c.getString(R.string.dicerollhintchoose));
            }
        });

        if (roll) {
            String suff = dices + "D" + max;
            suff += (bonus < 0) ? " " + bonus : " + " + bonus;
            int result = 0, totalResult = 0;
            for (int j = 0; j < dices; j++) {
                result = rnd.nextInt(max) + 1;
                totalResult += result;
            }
            if (dices == 1 && max == 20 && (result == 1 || result == 20)) {
                nattxt.setText(c.getString(R.string.naturaldice, result + ""));
            }
            totalResult += bonus;
            String tmp = suff + " = " + totalResult;
            outtxt.setText(tmp);
            histtxt.setText(text);
        }
    }

    @Override
    public void onClick(View v) {
        int max = 1;
        int id = v.getId();
        if (id == R.id.D20) {
            max = 20;
        } else if (id == R.id.D100) {
            max = 100;
        } else if (id == R.id.D12) {
            max = 12;
        } else if (id == R.id.D10) {
            max = 10;
        } else if (id == R.id.D8) {
            max = 8;
        } else if (id == R.id.D6) {
            max = 6;
        } else if (id == R.id.D4) {
            max = 4;
        } else if (id == R.id.coin) {
            max = -1;
        }

        nattxt.setText("");
        if (max != -1) {
            String suff = "1D" + max;
            int result = rnd.nextInt(max) + 1;
            rolls.add(result);
            if (max == 20 && (result == 1 || result == 20)) {
                nattxt.setText(c.getString(R.string.naturaldice, result + ""));
            }
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
            if (rnd.nextBoolean()) outtxt.setText(this.getContext().getText(R.string.testa));
            else outtxt.setText(this.getContext().getText(R.string.croce));
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int max = 1;
        int id = v.getId();
        if (id == R.id.D20) {
            max = 20;
        } else if (id == R.id.D100) {
            max = 100;
        } else if (id == R.id.D12) {
            max = 12;
        } else if (id == R.id.D10) {
            max = 10;
        } else if (id == R.id.D8) {
            max = 8;
        } else if (id == R.id.D6) {
            max = 6;
        } else if (id == R.id.D4) {
            max = 4;
        } else if (id == R.id.coin) {
            max = -1;
        }

        if (max != -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle(this.getContext().getString(R.string.howmanydice, max));
            final EditText input = new EditText(this.c);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            final int finalMax = max;
            builder.setPositiveButton(R.string.roll , new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
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
                    } catch (Exception ex) {
                        input.setError(getContext().getString(R.string.numbererror));
                        Toast.makeText(c.getApplicationContext(), getContext().getString(R.string.numbererror), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();
        } else {
            if (rnd.nextBoolean()) outtxt.setText(R.string.testa);
            else outtxt.setText(R.string.croce);
        }
        return true;
    }
}
