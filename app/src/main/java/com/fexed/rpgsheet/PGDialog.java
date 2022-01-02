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
import android.widget.TextView;
import android.widget.Toast;

import com.fexed.rpgsheet.data.Character;

public class PGDialog extends Dialog implements android.view.View.OnClickListener {

    public CharacterActivity c;
    public Button yes;

    public PGDialog(CharacterActivity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pgedit);
        yes = findViewById(R.id.Okinputbtn);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Okinputbtn) {
            EditText pgnameinput = findViewById(R.id.pgnameinput);
            EditText pgclassinput = findViewById(R.id.pgclassinput);
            EditText pglvinput = findViewById(R.id.pglvinput);

            if (TextUtils.isEmpty(pgnameinput.getText().toString())) {
                pgnameinput.setError(getContext().getString(R.string.pgnameerror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.pgnameerror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PGDialog.this.show();
                    }
                });
            } else if (TextUtils.isEmpty(pgclassinput.getText().toString())) {
                pgclassinput.setError(getContext().getString(R.string.pgclasserror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.pgclasserror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PGDialog.this.show();
                    }
                });
            } else if (TextUtils.isEmpty(pglvinput.getText().toString())) {
                pglvinput.setError(getContext().getString(R.string.pglverror));
                this.dismiss();
                Toast.makeText(this.c.getApplicationContext(), getContext().getString(R.string.pglverror), Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PGDialog.this.show();
                    }
                });
            } else {
                c.character = new Character();
                int lv = Integer.parseInt(pglvinput.getText().toString());
                lv = (lv <= 0) ? 1 : lv;
                lv = Math.min(lv, 45);
                c.character.nome = pgnameinput.getText().toString();
                c.character.classe = pgclassinput.getText().toString();
                c.character.LV = lv;

                TextView pgnametxt = c.findViewById(R.id.pgnametxt);
                TextView pgclasstxt = c.findViewById(R.id.pgclasstxt);
                TextView pglvtxt = c.findViewById(R.id.pglvtxt);

                c.setTitle(c.character.nome);
                pgnametxt.setText(c.character.nome);
                pgclasstxt.setText(c.character.classe);
                String tmp = c.character.LV + "";
                pglvtxt.setText(tmp);

                c.saveSchedaPG();
                this.dismiss();
            }
        }
        dismiss();
    }
}
