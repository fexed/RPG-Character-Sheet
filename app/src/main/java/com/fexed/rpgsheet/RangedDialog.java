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

import com.fexed.rpgsheet.data.Character;
import com.fexed.rpgsheet.data.RangedWeapon;

import java.util.HashSet;
import java.util.Set;

public class RangedDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Character character;
    public Button yes;
    public TableRow newrow;
    public TableLayout rangedatks;

    public RangedDialog(Activity a, Character character, TableRow r, TableLayout t) {
        super(a);
        this.c = a;
        this.character = character;
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
        if (v.getId() == R.id.rangedokbtn) {
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
            } else {
                TextView name = (TextView) newrow.findViewById(R.id.rangedname);
                TextView range = (TextView) newrow.findViewById(R.id.range);
                TextView bonusrange = (TextView) newrow.findViewById(R.id.rangedbonus);
                TextView comprange = (TextView) newrow.findViewById(R.id.rangedbonuscomp);
                TextView damage = (TextView) newrow.findViewById(R.id.rangeddamage);

                final RangedWeapon weap = new RangedWeapon(rangedname.getText().toString(), rangedrange.getText().toString(), rangeddamage.getText().toString());
                character.armiranged.add(weap);

                Button removebtn = (Button) newrow.findViewById(R.id.removeranged);
                removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        character.armiranged.remove(weap);
                        rangedatks.removeView(newrow);
                        return true;
                    }
                });

                int bonus = CharacterActivity.mod(character.DEX);
                String suffix = (bonus >= 0) ? "+" : "";

                name.setText(rangedname.getText().toString());
                bonusrange.setText(suffix + bonus);
                comprange.setText("+" + CharacterActivity.prof(character.LV));
                range.setText(rangedrange.getText().toString());
                damage.setText(rangeddamage.getText().toString());

                rangedatks.addView(newrow);
                this.dismiss();
                ((CharacterActivity) c).saveSchedaPG();
            }
        }
        dismiss();
    }
}
