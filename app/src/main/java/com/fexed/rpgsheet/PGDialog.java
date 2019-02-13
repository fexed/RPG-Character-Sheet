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

public class PGDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public SharedPreferences state;
    public Button yes;

    public PGDialog(Activity a, SharedPreferences state) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.state = state;
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
        switch (v.getId()) {
            case R.id.Okinputbtn:
                EditText pgnameinput = findViewById(R.id.pgnameinput);
                EditText pgclassinput = findViewById(R.id.pgclassinput);
                EditText pglvinput = findViewById(R.id.pglvinput);

                if (TextUtils.isEmpty(pgnameinput.getText().toString())) {
                    pgnameinput.setError("Il nome del PG non può essere vuoto");
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), "Il nome del PG non può essere vuoto", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PGDialog.this.show();
                        }
                    });
                    break;
                } else if (TextUtils.isEmpty(pgclassinput.getText().toString())) {
                    pgclassinput.setError("La classe del PG non può essere vuoto");
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), "La classe del PG non può essere vuota", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PGDialog.this.show();
                        }
                    });
                    break;
                } else if (TextUtils.isEmpty(pglvinput.getText().toString())) {
                    pglvinput.setError("Il livello del PG non può essere vuoto");
                    this.dismiss();
                    Toast.makeText(this.c.getApplicationContext(), "Il livello del PG non può essere vuoto", Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            PGDialog.this.show();
                        }
                    });
                    break;
                } else {
                    /*Assert.assertTrue("ERRORE input non valdio", !(pgnameinput.getText().toString().equals("")));
                    Assert.assertNotNull("ERRORE input nullo", pgnameinput.getText().toString());
                    Assert.assertTrue("ERRORE input non valdio", !(pgclassinput.getText().toString().equals("")));
                    Assert.assertNotNull("ERRORE input nullo", pgclassinput.getText().toString());
                    Assert.assertTrue("ERRORE input non valdio", !(pglvinput.getText().toString().equals("")));
                    Assert.assertNotNull("ERRORE input nullo", pglvinput.getText().toString());*/
                    int lv = Integer.parseInt(pglvinput.getText().toString());
                    if (lv <= 0) lv = 1;
                    state.edit().putString("pgname", pgnameinput.getText().toString()).apply();
                    state.edit().putString("pgclass", pgclassinput.getText().toString()).apply();
                    state.edit().putInt("pglv", lv).apply();
                    state.edit().commit();

                    TextView pgnametxt = c.findViewById(R.id.pgnametxt);
                    TextView pgclasstxt = c.findViewById(R.id.pgclasstxt);
                    TextView pglvtxt = c.findViewById(R.id.pglvtxt);

                    pgnametxt.setText(state.getString("pgname", "errore"));
                    pgclasstxt.setText(state.getString("pgclass", "errore"));
                    pglvtxt.setText(state.getInt("pglv", 0) + "");

                    this.dismiss();
                    break;
                }
            default:
                break;
        }
        dismiss();
    }
}
