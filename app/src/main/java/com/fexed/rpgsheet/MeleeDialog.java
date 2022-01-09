package com.fexed.rpgsheet;

import android.app.Activity;
import android.app.Dialog;
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
import com.fexed.rpgsheet.data.MeleeWeapon;

public class MeleeDialog extends Dialog implements View.OnClickListener {

    public Activity c;
    public Character character;
    public Button yes;
    public TableRow newrow;
    public TableLayout rangedatks;

    public MeleeDialog(Activity a, Character character, TableRow r, TableLayout t) {
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
        setContentView(R.layout.meleedialog);
        yes = findViewById(R.id.meleeokbtn);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.meleeokbtn) {
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
            } else {
                TextView name = newrow.findViewById(R.id.meleename);
                TextView bonusrange = newrow.findViewById(R.id.meleebonus);
                TextView comprange = newrow.findViewById(R.id.meleebonuscomp);
                TextView damage = newrow.findViewById(R.id.meleedamage);

                final MeleeWeapon weap = new MeleeWeapon(meleename.getText().toString(), meleedamage.getText().toString());
                character.armimelee.add(weap);

                Button removebtn = newrow.findViewById(R.id.removemelee);
                removebtn.setOnLongClickListener(view -> {
                    character.armimelee.remove(weap);
                    rangedatks.removeView(newrow);
                    return true;
                });
                removebtn.setOnClickListener(view -> {
                    Toast.makeText(c, c.getString(R.string.keeptoremove), Toast.LENGTH_SHORT).show();
                });

                int bonus = CharacterActivity.mod(character.FOR);
                String suffix = (bonus >= 0) ? "+" : "";

                name.setText(meleename.getText().toString());
                String tmp = suffix + bonus;
                bonusrange.setText(tmp);
                tmp = "+" + CharacterActivity.prof(character.LV);
                comprange.setText(tmp);
                damage.setText(meleedamage.getText().toString());

                rangedatks.addView(newrow);
                this.dismiss();
                ((CharacterActivity) c).saveSchedaPG();
            }
        }
        dismiss();
    }
}
