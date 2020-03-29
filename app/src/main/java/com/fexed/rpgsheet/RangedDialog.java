package com.fexed.rpgsheet;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

public class RangedDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public SharedPreferences state;
    public Button yes;
    public TableRow newrow;
    public TableLayout rangedatks;

    public RangedDialog(Activity a, SharedPreferences state, TableRow r, TableLayout t) {
        super(a);
        this.c = a;
        this.state = state;
        this.newrow = r;
        this.rangedatks = t;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.rangeddialog);
        yes = findViewById(R.id.rangedokbtn);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rangedokbtn:
                EditText rangedname = findViewById(R.id.rangednameinput);
                EditText rangedrange = findViewById(R.id.rangedrangeinput);
                EditText rangeddamage = findViewById(R.id.rangeddamageinput);

                if (TextUtils.isEmpty(rangedname.getText().toString())) {
                    rangedname.setError(getContext().getResources().getString(R.string.errorattackname));
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), getContext().getResources().getString(R.string.errorattackname), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RangedDialog.this.show();
                        }
                    });
                    break;
                } else if (TextUtils.isEmpty(rangedrange.getText().toString())) {
                    rangedrange.setError(getContext().getResources().getString(R.string.errorattackrng));
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), getContext().getResources().getString(R.string.errorattackrng), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RangedDialog.this.show();
                        }
                    });
                    break;
                } else if (TextUtils.isEmpty(rangeddamage.getText().toString())) {
                    rangeddamage.setError(getContext().getResources().getString(R.string.errorattackdmg));
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), getContext().getResources().getString(R.string.errorattackdmg), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RangedDialog.this.show();
                        }
                    });
                    break;
                } else {
                    TextView name = (TextView) newrow.findViewById(R.id.rangedname);
                    TextView range = (TextView) newrow.findViewById(R.id.range);
                    TextView bonusrange = (TextView) newrow.findViewById(R.id.rangedbonus);
                    TextView comprange = (TextView) newrow.findViewById(R.id.rangedbonuscomp);
                    TextView damage = (TextView) newrow.findViewById(R.id.rangeddamage);

                    Set<String> rangedset = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
                    final String element = rangedname.getText().toString()+"%"+rangedrange.getText().toString()+"%"+rangeddamage.getText().toString();
                    rangedset.add(element);
                    state.edit().putStringSet("rangedatks", rangedset).apply();

                    Button removebtn = (Button) newrow.findViewById(R.id.removeranged);
                    removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            Set<String> rangedset = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
                            rangedset.remove(element);
                            state.edit().putStringSet("rangedatks", rangedset).apply();
                            rangedatks.removeView(newrow);
                            return true;
                        }
                    });

                    int bonus = CharacterActivity.mod(state.getInt("DEX", 10));
                    String suffix = (bonus >= 0) ? "+" : "";

                    name.setText(rangedname.getText().toString());
                    bonusrange.setText(suffix + bonus);
                    comprange.setText("+" + CharacterActivity.prof[state.getInt("pglv", 1) - 1]);
                    range.setText(rangedrange.getText().toString());
                    damage.setText(rangeddamage.getText().toString());

                    rangedatks.addView(newrow);
                    this.dismiss();
                    break;
                }
            default:
                break;
        }
        dismiss();
    }
}
