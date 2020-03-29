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

public class MeleeDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public SharedPreferences state;
    public Button yes;
    public TableRow newrow;
    public TableLayout rangedatks;

    public MeleeDialog(Activity a, SharedPreferences state, TableRow r, TableLayout t) {
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
        setContentView(R.layout.meleedialog);
        yes = findViewById(R.id.meleeokbtn);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.meleeokbtn:
                EditText meleename = findViewById(R.id.meleenameinput);
                EditText meleedamage = findViewById(R.id.meleedamageinput);

                if (TextUtils.isEmpty(meleename.getText().toString())) {
                    meleename.setError(getContext().getResources().getString(R.string.errorattackname));
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), getContext().getResources().getString(R.string.errorattackname), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MeleeDialog.this.show();
                        }
                    });
                    break;
                } else if (TextUtils.isEmpty(meleedamage.getText().toString())) {
                    meleedamage.setError(getContext().getResources().getString(R.string.errorattackdmg));
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), getContext().getResources().getString(R.string.errorattackdmg), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MeleeDialog.this.show();
                        }
                    });
                    break;
                } else {
                    TextView name = (TextView) newrow.findViewById(R.id.meleename);
                    TextView bonusrange = (TextView) newrow.findViewById(R.id.meleebonus);
                    TextView comprange = (TextView) newrow.findViewById(R.id.meleebonuscomp);
                    TextView damage = (TextView) newrow.findViewById(R.id.meleedamage);

                    Set<String> meleeset = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
                    final String element = meleename.getText().toString()+"%"+meleedamage.getText().toString();
                    meleeset.add(element);
                    state.edit().putStringSet("meleeatks", meleeset).apply();

                    Button removebtn = (Button) newrow.findViewById(R.id.removemelee);
                    removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            Set<String> meleeset = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
                            meleeset.remove(element);
                            state.edit().putStringSet("meleeatks", meleeset).apply();
                            rangedatks.removeView(newrow);
                            return true;
                        }
                    });

                    int bonus = CharacterActivity.mod(state.getInt("FOR", 10));
                    String suffix = (bonus >= 0) ? "+" : "";

                    name.setText(meleename.getText().toString());
                    bonusrange.setText(suffix + bonus);
                    comprange.setText("+" + CharacterActivity.prof[state.getInt("pglv", 1) - 1]);
                    damage.setText(meleedamage.getText().toString());

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
