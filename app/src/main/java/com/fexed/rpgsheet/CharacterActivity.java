package com.fexed.rpgsheet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.lang.Math.floor;

public class CharacterActivity extends AppCompatActivity {

    static SharedPreferences state;
    static int[] prof = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13};
    static boolean minusPressed = false;
    static boolean plusPressed = false;

    @Override
    protected void onCreate (Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.charactersheet);
        state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        preparaSchedaPG();
        initializeAds();
        Bundle bndl = new Bundle();
        bndl.putString("PG_Name", state.getString("pgname", "nonsettato"));
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, bndl);
        Log.d("NOTE", state.getString("notes", ""));
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent myIntent = new Intent(CharacterActivity.this, Settings.class);
            startActivity(myIntent);
        } else if (item.getItemId() == R.id.dice) {
            DiceDialog inputdialog = new DiceDialog(this);
            inputdialog.show();
        }
        return true;
    }

    private void preparaSchedaPG() {
        final TextView FOR = findViewById(R.id.FOR);
        final TextView FORmod = findViewById(R.id.FORmod);
        final TextView DEX = findViewById(R.id.DEX);
        final TextView DEXmod = findViewById(R.id.DEXmod);
        final TextView COS = findViewById(R.id.COS);
        final TextView COSmod = findViewById(R.id.COSmod);
        final TextView INT = findViewById(R.id.INT);
        final TextView INTmod = findViewById(R.id.INTmod);
        final TextView SAG = findViewById(R.id.SAG);
        final TextView SAGmod = findViewById(R.id.SAGmod);
        final TextView CAR = findViewById(R.id.CAR);
        final TextView CARmod = findViewById(R.id.CARmod);
        final TextView lvtxt = findViewById(R.id.pglvtxt);
        final TextView nametxt = findViewById(R.id.pgnametxt);
        final TextView classtxt = findViewById(R.id.pgclasstxt);
        final TextView proftxt = findViewById(R.id.proftxt);
        final TextView CA = findViewById(R.id.CA);
        final TextView PF = findViewById(R.id.PF);
        final TextView PFmax = findViewById(R.id.PFmax);
        final TextView XP = findViewById(R.id.pgxptxtv);
        final TextView abilitatalenti = findViewById(R.id.skillstitle);
        final ImageView abilitatalentiarrow = findViewById(R.id.dwna1);
        final TextView inventario = findViewById(R.id.invtitle);
        final ImageView inventarioarrow = findViewById(R.id.dwna3);
        final TextView background = findViewById(R.id.bgtitle);
        final ImageView backgroundarrow = findViewById(R.id.dwna4);
        final TextView attacchi = findViewById(R.id.atktitle);
        final ImageView attacchiarrow = findViewById(R.id.dwna2);
        final TextView spellatk = findViewById(R.id.spellatktxt);
        final TextView spellcd = findViewById(R.id.spellcdtxt);
        final TextView spellstat = findViewById(R.id.spelstatselection);
        final TextView spellmana = findViewById(R.id.manatxt);
        final Button PFplus = findViewById(R.id.pfplus);
        final Button PFminus = findViewById(R.id.pfminus);
        final Button addranged = findViewById(R.id.addrangedatk);
        final Button addmelee = findViewById(R.id.addmeleeatk);
        final Button spellapp = findViewById(R.id.spellappbtn);
        final Button addmanabtn = findViewById(R.id.addmana);
        final Button removemanabtn = findViewById(R.id.removemana);
        final Button addxpbtn = findViewById(R.id.addxpbtn);
        final EditText cantrip = findViewById(R.id.cantriplist);
        final EditText firstlv = findViewById(R.id.firstlist);
        final EditText secondlv = findViewById(R.id.secondlist);
        final EditText thirdlv = findViewById(R.id.thirdlist);
        final EditText fourthlv = findViewById(R.id.fourthlsit);
        final EditText fifthlv = findViewById(R.id.fifthlist);
        final EditText sixthlv = findViewById(R.id.sixthlist);
        final EditText seventhlv = findViewById(R.id.seventhlist);
        final EditText eighthlv = findViewById(R.id.eigththlist);
        final EditText ninthlv = findViewById(R.id.ninthlist);
        final EditText pluslv = findViewById(R.id.pluslist);
        final CheckBox inspirationtbn = findViewById(R.id.inspirationbtn);
        final TableLayout rangedatks = findViewById(R.id.rangedatks);
        final TableLayout meleeatks = findViewById(R.id.meleeatks);
        final RecyclerView inventoryView = findViewById(R.id.inventoryRecV);
        int pntfor; int modfor;
        int pntdex; int moddex;
        int pntcos; int modcos;
        int pntint; int modint;
        int pntsag; int modsag;
        int pntcar; int modcar;


        String pgname = state.getString("pgname", null);
        String tempstr;
        if (pgname == null) {
            PGDialog inputdialog = new PGDialog(this, state);
            inputdialog.show();
        } else {
            nametxt.setText(state.getString("pgname", "errore"));
            classtxt.setText(state.getString("pgclass", "errore"));
            int lv = Math.min(state.getInt("pglv", 1), 20);
            state.edit().putInt("pglv", lv).apply();
            tempstr = lv + "";
            lvtxt.setText(tempstr);
            tempstr = "+" + (prof[lv - 1]);
            proftxt.setText(tempstr);

            pntfor = state.getInt("FOR", 10);
            modfor = mod(pntfor);
            String suffix = (modfor >= 0) ? "+" : "";
            tempstr = "" + pntfor;
            FOR.setText(tempstr);
            tempstr = suffix + modfor;
            FORmod.setText(tempstr);

            pntdex = state.getInt("DEX", 10);
            moddex = mod(pntdex);
            suffix = (moddex >= 0) ? "+" : "";
            tempstr = "" + pntdex;
            DEX.setText(tempstr);
            tempstr = suffix + moddex;
            DEXmod.setText(tempstr);

            pntcos = state.getInt("COS", 10);
            modcos = mod(pntcos);
            suffix = (modcos >= 0) ? "+" : "";
            tempstr = "" + pntcos;
            COS.setText(tempstr);
            tempstr = suffix + modcos;
            COSmod.setText(tempstr);

            pntint = state.getInt("INT", 10);
            modint = mod(pntint);
            suffix = (modint >= 0) ? "+" : "";
            tempstr = "" + pntint;
            INT.setText(tempstr);
            tempstr = suffix + modint;
            INTmod.setText(tempstr);

            pntsag = state.getInt("SAG", 10);
            modsag = mod(pntsag);
            suffix = (modsag >= 0) ? "+" : "";
            tempstr = "" + pntsag;
            SAG.setText(tempstr);
            tempstr = suffix + modsag;
            SAGmod.setText(tempstr);

            pntcar = state.getInt("CAR", 10);
            modcar = mod(pntcar);
            suffix = (modcar >= 0) ? "+" : "";
            tempstr = "" + pntcar;
            CAR.setText(tempstr);
            tempstr = suffix + modcar;
            CARmod.setText(tempstr);

            int ca = state.getInt("CA", 10);
            tempstr = "" + ca;
            CA.setText(tempstr);

            int pf = state.getInt("PF", 0);
            tempstr = "" + pf;
            PF.setText(tempstr);

            int pfmax = state.getInt("PFMAX", 0);
            tempstr = "" + pfmax;
            PFmax.setText(tempstr);

        }

        String stat = state.getString("SPELLSTAT", "INT");
        spellstat.setText(stat);
        int lv = state.getInt("pglv", 1);
        int bonus = prof[lv-1] + mod(state.getInt(stat, 10));
        String suffix = (bonus < 0) ? "" : "+";
        tempstr = suffix + bonus;
        spellatk.setText(tempstr);
        tempstr = "" + (8 + bonus);
        spellcd.setText(tempstr);

        spellstat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(CharacterActivity.this);
                b.setTitle(getString(R.string.selectspellstat));
                String[] types = {getString(R.string.inte), getString(R.string.sag), getString(R.string.car)};
                b.setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String stat = "";
                        switch(which) {
                            case 0:
                                stat = getString(R.string.inte);
                                break;
                            case 1:
                                stat = getString(R.string.sag);
                                break;
                            case 2:
                                stat = getString(R.string.car);
                                break;
                        }
                        int lv = state.getInt("pglv", 1);
                        state.edit().putString("SPELLSTAT", stat).apply();
                        spellstat.setText(stat);
                        int bonus = prof[lv-1] + mod(state.getInt(stat, 10));
                        String suffix = (bonus < 0) ? "" : "+";
                        String tempstr;
                        tempstr = suffix + bonus;
                        spellatk.setText(tempstr);
                        tempstr = "" + (8 + bonus);
                        spellcd.setText(tempstr);
                    }

                });

                b.show();

            }
        });

        abilitatalenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout skilllyt = findViewById(R.id.skills);
                if (skilllyt.getVisibility() == View.VISIBLE) {
                    skilllyt.setVisibility(View.GONE);
                    abilitatalentiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    skilllyt.setVisibility(View.VISIBLE);
                    abilitatalentiarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });
        abilitatalentiarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout skilllyt = findViewById(R.id.skills);
                if (skilllyt.getVisibility() == View.VISIBLE) {
                    skilllyt.setVisibility(View.GONE);
                    abilitatalentiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    skilllyt.setVisibility(View.VISIBLE);
                    abilitatalentiarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });

        inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout invlyt = findViewById(R.id.inventory);
                if (invlyt.getVisibility() == View.VISIBLE) {
                    invlyt.setVisibility(View.GONE);
                    inventarioarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    invlyt.setVisibility(View.VISIBLE);
                    inventarioarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });
        inventarioarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout invlyt = findViewById(R.id.inventory);
                if (invlyt.getVisibility() == View.VISIBLE) {
                    invlyt.setVisibility(View.GONE);
                    inventarioarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    invlyt.setVisibility(View.VISIBLE);
                    inventarioarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });

        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout bglyt = findViewById(R.id.background);
                if (bglyt.getVisibility() == View.VISIBLE) {
                    bglyt.setVisibility(View.GONE);
                    backgroundarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    bglyt.setVisibility(View.VISIBLE);
                    backgroundarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });
        backgroundarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout bglyt = findViewById(R.id.background);
                if (bglyt.getVisibility() == View.VISIBLE) {
                    bglyt.setVisibility(View.GONE);
                    backgroundarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    bglyt.setVisibility(View.VISIBLE);
                    backgroundarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });

        attacchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout atklyt = findViewById(R.id.atk);
                if (atklyt.getVisibility() == View.VISIBLE) {
                    atklyt.setVisibility(View.GONE);
                    attacchiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    atklyt.setVisibility(View.VISIBLE);
                    attacchiarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });
        attacchiarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout atklyt = findViewById(R.id.atk);
                if (atklyt.getVisibility() == View.VISIBLE) {
                    atklyt.setVisibility(View.GONE);
                    attacchiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    atklyt.setVisibility(View.VISIBLE);
                    attacchiarrow.setImageResource(R.drawable.uparrow);
                }
            }
        });


        lvtxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertlevelof) + " " + state.getString("pgname", null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int lv = Integer.parseInt(input.getText().toString());
                        if (lv <= 0) lv = 1;
                        if (lv > 45) lv = 45;

                        String tempstr;
                        tempstr = lv + "";
                        lvtxt.setText(tempstr);
                        tempstr = "+" + prof[lv-1];
                        proftxt.setText(tempstr);
                        state.edit().putInt("pglv", lv).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        tempstr = "+" + prof[state.getInt("pglv",1)-1];
        proftxt.setText(tempstr);

        nametxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertnewnameof) + " " + state.getString("pgname", null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = input.getText().toString();

                        nametxt.setText(name);
                        state.edit().putString("pgname", name).apply();
                        dialog.cancel();
                        alertd.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        spellmana.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxpoints));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = input.getText().toString();
                        int mana = Integer.parseInt(name);
                        String tempstr = state.getInt("spellmana", 0) + "/" + name;
                        spellmana.setText(tempstr);
                        state.edit().putInt("spellmanamax", mana).apply();
                        dialog.cancel();
                        alertd.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });
        tempstr = state.getInt("spellmana", 0) + "/" + state.getInt("spellmanamax", 0);
        spellmana.setText(tempstr);

        addmanabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mana = state.getInt("spellmana", 0);
                int manamax = state.getInt("spellmanamax", 0);
                mana++;
                mana = Math.min(mana, manamax);
                state.edit().putInt("spellmana", mana).apply();
                String tempstr = mana + "/" + manamax;
                spellmana.setText(tempstr);
                saveSchedaPG();
            }
        });

        removemanabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int mana = state.getInt("spellmana", 0);
                mana--;
                mana = Math.max(mana, 0);
                state.edit().putInt("spellmana", mana).apply();
                String tempstr = mana + "/" + state.getInt("spellmanamax", 0);
                spellmana.setText(tempstr);
                saveSchedaPG();
            }
        });

        classtxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertnewclass) + " " + state.getString("pgname", null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String classs = input.getText().toString();

                        classtxt.setText(classs);
                        state.edit().putString("pgclass", classs).apply();
                        dialog.cancel();
                        alertd.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });

        CA.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                String tempstr = state.getInt("CA", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertca));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());

                        String tempstr = pnt + "";
                        CA.setText(tempstr);
                        state.edit().putInt("CA", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        PF.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                String tempstr = state.getInt("PF", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertpf));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        if (pnt > state.getInt("PFMAX", pnt)) {
                            Toast.makeText(CharacterActivity.this, getString(R.string.insertpferror), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                            alertd.dismiss();
                        }
                        else {
                            String tempstr = pnt + "";
                            PF.setText(tempstr);
                            state.edit().putInt("PF", pnt).apply();
                            dialog.cancel();
                            alertd.dismiss();
                            preparaSchedaPG();
                        }
                    }
                });
                alert.show();
                return true;
            }
        });

        PFplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pf = state.getInt("PF", 0);
                pf++;
                int pfmax = state.getInt("PFMAX", pf);
                if (pf > pfmax) pf = pfmax;
                state.edit().putInt("PF", pf).apply();
                String tempstr = pf + "";
                PF.setText(tempstr);
                saveSchedaPG();
            }
        });

        PFplus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                String tempstr = 0 + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.entercure));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int pf = state.getInt("PF", 0);

                        pf += pnt;

                        String tempstr = pf + "";
                        PF.setText(tempstr);
                        state.edit().putInt("PF", pf).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        PFminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pf = state.getInt("PF", 0);
                pf--;
                state.edit().putInt("PF", pf).apply();
                String tempstr = pf + "";
                PF.setText(tempstr);
                saveSchedaPG();
            }
        });

        PFminus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                String tempstr = 0 + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.enterdamage));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int pf = state.getInt("PF", 0);

                        pf -= pnt;
                        pf = Math.max(pf, 0);

                        String tempstr = pf + "";
                        PF.setText(tempstr);
                        state.edit().putInt("PF", pf).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        PFmax.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                String tempstr = state.getInt("PFMAX", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxpf));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());

                        String tempstr = pnt + "";
                        PFmax.setText(tempstr);
                        state.edit().putInt("PFMAX", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        FOR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.str));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        FOR.setText(tempstr);
                        tempstr = suffix + mod;
                        FORmod.setText(tempstr);
                        state.edit().putInt("FOR", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        DEX.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.dex));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        DEX.setText(pnt + "");
                        DEXmod.setText(suffix + mod);
                        state.edit().putInt("DEX", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        COS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.cos));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        COS.setText(pnt + "");
                        COSmod.setText(suffix + mod);
                        state.edit().putInt("COS", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        INT.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.inte));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        INT.setText(pnt + "");
                        INTmod.setText(suffix + mod);
                        state.edit().putInt("INT", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        SAG.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.sag));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        SAG.setText(pnt + "");
                        SAGmod.setText(suffix + mod);
                        state.edit().putInt("SAG", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        CAR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for CANCEL button here, or leave in blank
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.car));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        CAR.setText(pnt + "");
                        CARmod.setText(suffix + mod);
                        state.edit().putInt("CAR", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        final TextView tsfortxt = findViewById(R.id.TSFOR);
        final CheckBox comptsfor = findViewById(R.id.comptsfor);
        comptsfor.setChecked(state.getBoolean("comptsfor", false));
        int ts = mod(state.getInt("FOR", 10)) + ((comptsfor.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tsfortxt.setText(suffix + ts);
        comptsfor.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptsfor", comptsfor.isChecked()).apply();
                int ts = mod(state.getInt("FOR", 10)) + ((comptsfor.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tsfortxt.setText(suffix + ts);
            }
        });

        final TextView tsdextxt = findViewById(R.id.TSDEX);
        final CheckBox comptsdex = findViewById(R.id.comptsdex);
        comptsdex.setChecked(state.getBoolean("comptsdex", false));
        ts = mod((state.getInt("DEX", 10))) + ((comptsdex.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tsdextxt.setText(suffix + ts);
        comptsdex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptsdex", comptsdex.isChecked()).apply();
                int ts = mod((state.getInt("DEX", 10))) + ((comptsdex.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tsdextxt.setText(suffix + ts);
            }
        });

        final TextView tscostxt = findViewById(R.id.TSCOS);
        final CheckBox comptscos = findViewById(R.id.comptscos);
        comptscos.setChecked(state.getBoolean("comptscos", false));
        ts = mod((state.getInt("COS", 10))) + ((comptscos.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tscostxt.setText(suffix + ts);
        comptscos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptscos", comptscos.isChecked()).apply();
                int ts = mod((state.getInt("COS", 10))) + ((comptscos.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tscostxt.setText(suffix + ts);
            }
        });

        final TextView tsinttxt = findViewById(R.id.TSINT);
        final CheckBox comptsint = findViewById(R.id.comptsint);
        comptsint.setChecked(state.getBoolean("comptsint", false));
        ts = mod((state.getInt("INT", 10))) + ((comptsint.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tsinttxt.setText(suffix + ts);
        comptsint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptsint", comptsint.isChecked()).apply();
                int ts = mod((state.getInt("INT", 10))) + ((comptsint.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tsinttxt.setText(suffix + ts);
            }
        });

        final TextView tssagtxt = findViewById(R.id.TSSAG);
        final CheckBox comptssag = findViewById(R.id.comptssag);
        comptssag.setChecked(state.getBoolean("comptssag", false));
        ts = mod((state.getInt("SAG", 10))) + ((comptssag.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tssagtxt.setText(suffix + ts);
        comptssag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptssag", comptssag.isChecked()).apply();
                int ts = mod((state.getInt("SAG", 10))) + ((comptssag.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tssagtxt.setText(suffix + ts);
            }
        });

        final TextView tscartxt = findViewById(R.id.TSCAR);
        final CheckBox comptscar = findViewById(R.id.comptscar);
        comptscar.setChecked(state.getBoolean("comptscar", false));
        ts = mod((state.getInt("CAR", 10))) + ((comptscar.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tscartxt.setText(suffix + ts);
        comptscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lv = state.getInt("pglv", 1);
                state.edit().putBoolean("comptscar", comptscar.isChecked()).apply();
                int ts = mod((state.getInt("CAR", 10)))+ ((comptscar.isChecked()) ? prof[lv-1] : 0);
                String suffix = (ts >= 0) ? "+" : "";
                tscartxt.setText(suffix + ts);
            }
        });

        final TextView atletica = findViewById(R.id.atletica);
        final CheckBox compatletica = findViewById(R.id.compatletica);
        final CheckBox expatletica = findViewById(R.id.expatletica);
        compatletica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expatletica.setVisibility(View.VISIBLE);
                else expatletica.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compatletica", compatletica.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("FOR", 10))) + ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                atletica.setText(suffix + bonus);
            }
        });
        expatletica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expatletica", expatletica.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("FOR", 10)))+ ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                atletica.setText(suffix + bonus);
            }
        });
        compatletica.setChecked(state.getBoolean("compatletica", false));
        expatletica.setChecked(state.getBoolean("expatletica", false));
        bonus = mod((state.getInt("FOR", 10)))+ ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        atletica.setText(suffix + bonus);

        final TextView acrobazia = findViewById(R.id.acrobazia);
        final CheckBox compacrobazia = findViewById(R.id.compacrobazia);
        final CheckBox expacrobazia = findViewById(R.id.expacrobazia);
        compacrobazia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expacrobazia.setVisibility(View.VISIBLE);
                else expacrobazia.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compacrobazia", compacrobazia.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                acrobazia.setText(suffix + bonus);
            }
        });
        expacrobazia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expacrobazia", expacrobazia.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                acrobazia.setText(suffix + bonus);
            }
        });
        compacrobazia.setChecked(state.getBoolean("compacrobazia", false));
        expacrobazia.setChecked(state.getBoolean("expacrobazia", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        acrobazia.setText(suffix + bonus);

        final TextView furtivita = findViewById(R.id.furtivita);
        final CheckBox compfurtivita = findViewById(R.id.compfurtivita);
        final CheckBox expfurtivita = findViewById(R.id.expfurtivita);
        compfurtivita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expfurtivita.setVisibility(View.VISIBLE);
                else expfurtivita.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compfurtivita", compfurtivita.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                furtivita.setText(suffix + bonus);
            }
        });
        expfurtivita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expfurtivita", expfurtivita.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                furtivita.setText(suffix + bonus);
            }
        });
        compfurtivita.setChecked(state.getBoolean("compfurtivita", false));
        expfurtivita.setChecked(state.getBoolean("expfurtivita", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        furtivita.setText(suffix + bonus);

        final TextView rapiditadimano = findViewById(R.id.rapiditadimano);
        final CheckBox comprapiditadimano = findViewById(R.id.comprapiditadimano);
        final CheckBox exprapiditadimano = findViewById(R.id.exprapiditadimano);
        comprapiditadimano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) exprapiditadimano.setVisibility(View.VISIBLE);
                else exprapiditadimano.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("comprapiditadimano", comprapiditadimano.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                rapiditadimano.setText(suffix + bonus);
            }
        });
        exprapiditadimano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("exprapiditadimano", exprapiditadimano.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("DEX", 10)))+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                rapiditadimano.setText(suffix + bonus);
            }
        });
        comprapiditadimano.setChecked(state.getBoolean("comprapiditadimano", false));
        exprapiditadimano.setChecked(state.getBoolean("exprapiditadimano", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        rapiditadimano.setText(suffix + bonus);

        final TextView investigare = findViewById(R.id.investigare);
        final CheckBox compinvestigare = findViewById(R.id.compinvestigare);
        final CheckBox expinvestigare = findViewById(R.id.expinvestigare);
        compinvestigare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expinvestigare.setVisibility(View.VISIBLE);
                else expinvestigare.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compinvestigare", compinvestigare.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10)))+ ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                investigare.setText(suffix + bonus);
            }
        });
        expinvestigare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expinvestigare", expinvestigare.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                investigare.setText(suffix + bonus);
            }
        });
        compinvestigare.setChecked(state.getBoolean("compinvestigare", false));
        expinvestigare.setChecked(state.getBoolean("expinvestigare", false));
        bonus = mod((state.getInt("INT", 10))) + ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        investigare.setText(suffix + bonus);

        final TextView arcano = findViewById(R.id.arcano);
        final CheckBox comparcano = findViewById(R.id.comparcano);
        final CheckBox exparcano = findViewById(R.id.exparcano);
        comparcano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) exparcano.setVisibility(View.VISIBLE);
                else exparcano.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("comparcano", comparcano.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                arcano.setText(suffix + bonus);
            }
        });
        exparcano.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("exparcano", exparcano.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                arcano.setText(suffix + bonus);
            }
        });
        comparcano.setChecked(state.getBoolean("comparcano", false));
        exparcano.setChecked(state.getBoolean("exparcano", false));
        bonus = mod((state.getInt("INT", 10))) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        arcano.setText(suffix + bonus);

        final TextView storia = findViewById(R.id.storia);
        final CheckBox compstoria = findViewById(R.id.compstoria);
        final CheckBox expstoria = findViewById(R.id.expstoria);
        compstoria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expstoria.setVisibility(View.VISIBLE);
                else expstoria.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compstoria", compstoria.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                storia.setText(suffix + bonus);
            }
        });
        expstoria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expstoria", expstoria.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                storia.setText(suffix + bonus);
            }
        });
        compstoria.setChecked(state.getBoolean("compstoria", false));
        expstoria.setChecked(state.getBoolean("expstoria", false));
        bonus = mod((state.getInt("INT", 10))) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        storia.setText(suffix + bonus);

        final TextView religionefolklore = findViewById(R.id.religionefolklore);
        final CheckBox compreligionefolklore = findViewById(R.id.compreligionefolklore);
        final CheckBox expreligionefolklore = findViewById(R.id.expreligionefolklore);
        compreligionefolklore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expreligionefolklore.setVisibility(View.VISIBLE);
                else expreligionefolklore.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compreligionefolklore", compreligionefolklore.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                religionefolklore.setText(suffix + bonus);
            }
        });
        expreligionefolklore.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expreligionefolklore", expreligionefolklore.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                religionefolklore.setText(suffix + bonus);
            }
        });
        compreligionefolklore.setChecked(state.getBoolean("compreligionefolklore", false));
        expreligionefolklore.setChecked(state.getBoolean("expreligionefolklore", false));
        bonus = mod((state.getInt("INT", 10))) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        religionefolklore.setText(suffix + bonus);

        final TextView natura = findViewById(R.id.natura);
        final CheckBox compnatura = findViewById(R.id.compnatura);
        final CheckBox expnatura = findViewById(R.id.expnatura);
        compnatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expnatura.setVisibility(View.VISIBLE);
                else expnatura.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compnatura", compnatura.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                natura.setText(suffix + bonus);
            }
        });
        expnatura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expnatura", expnatura.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("INT", 10))) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                natura.setText(suffix + bonus);
            }
        });
        compnatura.setChecked(state.getBoolean("compnatura", false));
        expnatura.setChecked(state.getBoolean("expnatura", false));
        bonus = mod((state.getInt("INT", 10))) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        natura.setText(suffix + bonus);

        final TextView sopravvivenza = findViewById(R.id.sopravvivenza);
        final CheckBox compsopravvivenza = findViewById(R.id.compsopravvivenza);
        final CheckBox expsopravvivenza = findViewById(R.id.expsopravvivenza);
        compsopravvivenza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expsopravvivenza.setVisibility(View.VISIBLE);
                else expsopravvivenza.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compsopravvivenza", compsopravvivenza.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                sopravvivenza.setText(suffix + bonus);
            }
        });
        expsopravvivenza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expsopravvivenza", expsopravvivenza.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                sopravvivenza.setText(suffix + bonus);
            }
        });
        compsopravvivenza.setChecked(state.getBoolean("compsopravvivenza", false));
        expsopravvivenza.setChecked(state.getBoolean("expsopravvivenza", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        sopravvivenza.setText(suffix + bonus);

        final TextView medicina = findViewById(R.id.medicina);
        final CheckBox compmedicina = findViewById(R.id.compmedicina);
        final CheckBox expmedicina = findViewById(R.id.expmedicina);
        compmedicina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expmedicina.setVisibility(View.VISIBLE);
                else expmedicina.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compmedicina", compmedicina.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                medicina.setText(suffix + bonus);
            }
        });
        expmedicina.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expmedicina", expmedicina.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                medicina.setText(suffix + bonus);
            }
        });
        compmedicina.setChecked(state.getBoolean("compmedicina", false));
        expmedicina.setChecked(state.getBoolean("expmedicina", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        medicina.setText(suffix + bonus);

        final TextView percezione = findViewById(R.id.percezione);
        final CheckBox comppercezione = findViewById(R.id.comppercezione);
        final CheckBox exppercezione = findViewById(R.id.exppercezione);
        comppercezione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) exppercezione.setVisibility(View.VISIBLE);
                else exppercezione.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("comppercezione", comppercezione.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                percezione.setText(suffix + bonus);
            }
        });
        exppercezione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("exppercezione", exppercezione.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                percezione.setText(suffix + bonus);
            }
        });
        comppercezione.setChecked(state.getBoolean("comppercezione", false));
        exppercezione.setChecked(state.getBoolean("exppercezione", false));
        bonus = mod((state.getInt("SAG", 10))) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        percezione.setText(suffix + bonus);

        final TextView intuizione = findViewById(R.id.intuizione);
        final CheckBox compintuizione = findViewById(R.id.compintuizione);
        final CheckBox expintuizione = findViewById(R.id.expintuizione);
        compintuizione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expintuizione.setVisibility(View.VISIBLE);
                else expintuizione.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compintuizione", compintuizione.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intuizione.setText(suffix + bonus);
            }
        });
        expintuizione.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expintuizione", expintuizione.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("SAG", 10))) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intuizione.setText(suffix + bonus);
            }
        });
        compintuizione.setChecked(state.getBoolean("compintuizione", false));
        expintuizione.setChecked(state.getBoolean("expintuizione", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        intuizione.setText(suffix + bonus);

        final TextView intimidire = findViewById(R.id.intimidire);
        final CheckBox compintimidire = findViewById(R.id.compintimidire);
        final CheckBox expintimidire = findViewById(R.id.expintimidire);
        compintimidire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expintimidire.setVisibility(View.VISIBLE);
                else expintimidire.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compintimidire", compintimidire.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intimidire.setText(suffix + bonus);
            }
        });
        expintimidire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expintimidire", expintimidire.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intimidire.setText(suffix + bonus);
            }
        });
        compintimidire.setChecked(state.getBoolean("compintimidire", false));
        expintimidire.setChecked(state.getBoolean("expintimidire", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        intimidire.setText(suffix + bonus);

        final TextView ingannare = findViewById(R.id.ingannare);
        final CheckBox compingannare = findViewById(R.id.compingannare);
        final CheckBox expingannare = findViewById(R.id.expingannare);
        compingannare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expingannare.setVisibility(View.VISIBLE);
                else expingannare.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compingannare", compingannare.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                ingannare.setText(suffix + bonus);
            }
        });
        expingannare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expingannare", expingannare.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                ingannare.setText(suffix + bonus);
            }
        });
        compingannare.setChecked(state.getBoolean("compingannare", false));
        expingannare.setChecked(state.getBoolean("expingannare", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        ingannare.setText(suffix + bonus);

        final TextView intrattenere = findViewById(R.id.intrattenere);
        final CheckBox compintrattenere = findViewById(R.id.compintrattenere);
        final CheckBox expintrattenere = findViewById(R.id.expintrattenere);
        compintrattenere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) expintrattenere.setVisibility(View.VISIBLE);
                else expintrattenere.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("compintrattenere", compintrattenere.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intrattenere.setText(suffix + bonus);
            }
        });
        expintrattenere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("expintrattenere", expintrattenere.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                intrattenere.setText(suffix + bonus);
            }
        });
        compintrattenere.setChecked(state.getBoolean("compintrattenere", false));
        expintrattenere.setChecked(state.getBoolean("expintrattenere", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        intrattenere.setText(suffix + bonus);

        final TextView persuadere = findViewById(R.id.persuadere);
        final CheckBox comppersuadere = findViewById(R.id.comppersuadere);
        final CheckBox exppersuadere = findViewById(R.id.exppersuadere);
        comppersuadere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) exppersuadere.setVisibility(View.VISIBLE);
                else exppersuadere.setVisibility(View.INVISIBLE);
                state.edit().putBoolean("comppersuadere", comppersuadere.isChecked()).apply();
                int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                persuadere.setText(suffix + bonus);
            }
        });
        exppersuadere.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("exppersuadere", exppersuadere.isChecked()).apply();int lv = state.getInt("pglv", 1);
                int bonus = mod((state.getInt("CAR", 10))) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
                String suffix = (bonus >= 0) ? "+" : "";
                persuadere.setText(suffix + bonus);
            }
        });
        comppersuadere.setChecked(state.getBoolean("comppersuadere", false));
        exppersuadere.setChecked(state.getBoolean("exppersuadere", false));
        bonus = mod((state.getInt("CAR", 10))) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        persuadere.setText(suffix + bonus);

        EditText linguetxt = findViewById(R.id.linguetxt);
        linguetxt.setText(state.getString("linguetxt", ""));
        linguetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("linguetxt", editable.toString()).apply();
            }
        });
        linguetxt.clearFocus();

        EditText armitxt = findViewById(R.id.armitxt);
        armitxt.setText(state.getString("armitxt", ""));
        armitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("armitxt", editable.toString()).apply();
            }
        });
        armitxt.clearFocus();

        EditText talentitxt = findViewById(R.id.talentitxt);
        talentitxt.setText(state.getString("talentitxt", ""));
        talentitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("talentitxt", editable.toString()).apply();
            }
        });
        talentitxt.clearFocus();

        EditText abilitatxt = findViewById(R.id.abilitatxt);
        abilitatxt.setText(state.getString("abilitatxt", ""));
        abilitatxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("abilitatxt", editable.toString()).apply();
            }
        });
        abilitatxt.clearFocus();

        final TextView mptxtv = findViewById(R.id.mptxtv);
        final TextView motxtv = findViewById(R.id.motxtv);
        final TextView matxtv = findViewById(R.id.matxtv);
        final TextView mrtxtv = findViewById(R.id.mrtxtv);
        final TextView totalmtxtv = findViewById(R.id.totalpgmoneytxtv);

        double money;
        int mp = state.getInt("mp", 0);
        int mo = state.getInt("mo", 0);
        int ma = state.getInt("ma", 0);
        int mr = state.getInt("mr", 0);
        money = Math.ceil(mp*10 + mo + ma*0.1 + mr*0.01);
        String txt = String.format(Locale.getDefault(), "%.0f", money);
        String strstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
        totalmtxtv.setText(strstr);

        mptxtv.setText(mp + "");
        mptxtv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.pgsplatpieces, state.getString("pgname", null)));
                input.setText(state.getInt("mp", 0) + "");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        mptxtv.setText(monete + "");
                        state.edit().putInt("mp", monete).apply();
                        int mo = state.getInt("mo", 0);
                        int ma = state.getInt("ma", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(monete*10 + mo + ma*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        totalmtxtv.setText(getString(R.string.total) + " " + txt + " " + getString(R.string.mo));
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });
        motxtv.setText(mo + "");
        motxtv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.pgsgoldpieces, state.getString("pgname", null)));
                input.setText(state.getInt("mo", 0) + "");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int monete = Integer.parseInt(input.getText().toString());
                        motxtv.setText(monete + "");
                        state.edit().putInt("mo", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int ma = state.getInt("ma", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(mp*10 + monete + ma*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        totalmtxtv.setText(getString(R.string.total) + " " + txt + " " + getString(R.string.mo));
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });
        matxtv.setText(ma + "");
        matxtv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.pgssilvpieces, state.getString("pgname", null)));
                input.setText(state.getInt("ma", 0) + "");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int monete = Integer.parseInt(input.getText().toString());
                        matxtv.setText(monete + "");
                        state.edit().putInt("ma", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int mo = state.getInt("mo", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(mp*10 + mo + monete*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        totalmtxtv.setText(getString(R.string.total) + " " + txt + " " + getString(R.string.mo));
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });
        mrtxtv.setText(mr + "");
        mrtxtv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.pgscopppieces, state.getString("pgname", null)));
                input.setText(state.getInt("mr", 0) + "");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Put actions for OK button here
                        int monete = Integer.parseInt(input.getText().toString());
                        mrtxtv.setText(monete + "");
                        state.edit().putInt("mr", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int mo = state.getInt("mo", 0);
                        int ma = state.getInt("ma", 0);
                        double moneteTot =  Math.ceil(mp*10 + mo + ma*0.1 + monete*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        totalmtxtv.setText(getString(R.string.total) + " " + txt + " " + getString(R.string.mo));
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });

        EditText invtxt = findViewById(R.id.invtxt);
        invtxt.setText(state.getString("inv", ""));
        invtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("inv", editable.toString()).apply();
            }
        });
        invtxt.clearFocus();

        EditText backgroundtxt = findViewById(R.id.backgroundtxt);
        backgroundtxt.setText(state.getString("background", ""));
        backgroundtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("background", editable.toString()).apply();
            }
        });
        backgroundtxt.clearFocus();

        rangedatks.removeAllViews();

        TableRow header = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangedrow, null);
        TextView name = header.findViewById(R.id.rangedname);
        TextView range = header.findViewById(R.id.range);
        TextView bonusrange = header.findViewById(R.id.rangedbonus);
        TextView comprange = header.findViewById(R.id.rangedbonuscomp);
        TextView damage = header.findViewById(R.id.rangeddamage);
        Button removebtn = header.findViewById(R.id.removeranged);
        name.setText(getString(R.string.name));
        range.setText(getString(R.string.range));
        bonusrange.setText(getString(R.string.bonusdex));
        comprange.setText(getString(R.string.comp));
        damage.setText(getString(R.string.damage));
        removebtn.setText("");
        rangedatks.addView(header);

        final Set<String> rangedset = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
        for (String str : rangedset) {
            String[] ranged = str.split("%");
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangedrow, null);
            name = newrow.findViewById(R.id.rangedname);
            range = newrow.findViewById(R.id.range);
            bonusrange = newrow.findViewById(R.id.rangedbonus);
            comprange = newrow.findViewById(R.id.rangedbonuscomp);
            damage = newrow.findViewById(R.id.rangeddamage);

            int bonusb = mod(state.getInt("DEX", 10));
            String suffixb = (bonus >= 0) ? "+" : "";

            name.setText(ranged[0]);
            range.setText(ranged[1]);
            bonusrange.setText(suffixb + bonusb);
            comprange.setText("+" + prof[lv-1]);
            damage.setText(ranged[2]);

            removebtn = newrow.findViewById(R.id.removeranged);
            final String strf = str;
            removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Set<String> rangedset = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
                    rangedset.remove(strf);
                    state.edit().putStringSet("rangedatks", rangedset).apply();
                    rangedatks.removeView(newrow);
                    return true;
                }
            });
            rangedatks.addView(newrow);
        }

        meleeatks.removeAllViews();

        header = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleerow, null);
        name = header.findViewById(R.id.meleename);
        bonusrange = header.findViewById(R.id.meleebonus);
        comprange = header.findViewById(R.id.meleebonuscomp);
        damage = header.findViewById(R.id.meleedamage);
        removebtn = header.findViewById(R.id.removemelee);
        name.setText(getString(R.string.name));
        bonusrange.setText(getString(R.string.bonusfor));
        comprange.setText(getString(R.string.comp));
        damage.setText(getString(R.string.damage));
        removebtn.setText("");
        meleeatks.addView(header);

        final Set<String> meleeset = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
        for (String str : meleeset) {
            String[] melee = str.split("%");
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleerow, null);
            name = newrow.findViewById(R.id.meleename);
            bonusrange = newrow.findViewById(R.id.meleebonus);
            comprange = newrow.findViewById(R.id.meleebonuscomp);
            damage = newrow.findViewById(R.id.meleedamage);

            int bonusb = mod(state.getInt("FOR", 10));
            String suffixb = (bonus >= 0) ? "+" : "";

            name.setText(melee[0]);
            bonusrange.setText(suffixb + bonusb);
            comprange.setText("+" + prof[lv-1]);
            damage.setText(melee[1]);

            removebtn = newrow.findViewById(R.id.removemelee);
            final String strf = str;
            removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Set<String> meleeset = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
                    meleeset.remove(strf);
                    state.edit().putStringSet("meleeatks", meleeset).apply();
                    meleeatks.removeView(newrow);
                    return true;
                }
            });
            meleeatks.addView(newrow);
        }

        addranged.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangedrow, null);
                RangedDialog inputdialog = new RangedDialog(CharacterActivity.this, state, newrow, rangedatks);
                inputdialog.show();
            }
        });

        addmelee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleerow, null);
                MeleeDialog inputdialog = new MeleeDialog(CharacterActivity.this, state, newrow, meleeatks);
                inputdialog.show();
            }
        });

        spellapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.spellsdd5");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        cantrip.setText(state.getString("cantripss", ""));
        cantrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("cantripss", editable.toString()).apply();
            }
        });
        cantrip.clearFocus();

        firstlv.setText(state.getString("firstlv", ""));
        firstlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("firstlv", editable.toString()).apply();
            }
        });
        firstlv.clearFocus();

        secondlv.setText(state.getString("secondlv", ""));
        secondlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("secondlv", editable.toString()).apply();
            }
        });
        secondlv.clearFocus();

        thirdlv.setText(state.getString("thirdlv", ""));
        thirdlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("thirdlv", editable.toString()).apply();
            }
        });
        thirdlv.clearFocus();

        fourthlv.setText(state.getString("fourthlv", ""));
        fourthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("fourthlv", editable.toString()).apply();
            }
        });
        fourthlv.clearFocus();

        fifthlv.setText(state.getString("fifthlv", ""));
        fifthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("fifthlv", editable.toString()).apply();
            }
        });
        fifthlv.clearFocus();

        sixthlv.setText(state.getString("sixthlv", ""));
        sixthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("sixthlv", editable.toString()).apply();
            }
        });
        sixthlv.clearFocus();

        seventhlv.setText(state.getString("seventhlv", ""));
        seventhlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("seventhlv", editable.toString()).apply();
            }
        });
        seventhlv.clearFocus();

        eighthlv.setText(state.getString("eighthlv", ""));
        eighthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("eighthlv", editable.toString()).apply();
            }
        });
        eighthlv.clearFocus();

        ninthlv.setText(state.getString("ninthlv", ""));
        ninthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();

            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("ninthlv", editable.toString()).apply();
            }
        });
        ninthlv.clearFocus();

        pluslv.setText(state.getString("pluslv", ""));
        pluslv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                saveSchedaPG();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("pluslv", editable.toString()).apply();
            }
        });
        pluslv.clearFocus();

        inspirationtbn.setChecked(state.getBoolean("inspiration", false));
        inspirationtbn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                state.edit().putBoolean("inspiration", b).apply();
                saveSchedaPG();
            }
        });

        XP.setText(state.getInt("xp", 0) + " xp");
        XP.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.insertxpof) + " " + state.getString("pgname", null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int xp = Integer.parseInt(input.getText().toString());

                        XP.setText(xp + " xp");
                        state.edit().putInt("xp", xp).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            }
        });
        addxpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
                final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.addxpof));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int xp = Integer.parseInt(input.getText().toString());
                        xp += state.getInt("xp", 0);

                        XP.setText(xp + " xp");
                        state.edit().putInt("xp", xp).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
            }
        });

        final InventoryAdapter inventoryAdapter = new InventoryAdapter(state);
        inventoryView.setAdapter(inventoryAdapter);
        LinearLayoutManager lytmngr = new LinearLayoutManager(CharacterActivity.this);
        lytmngr.setOrientation(LinearLayoutManager.HORIZONTAL);
        inventoryView.setLayoutManager(lytmngr);

        Button addObjBtn = findViewById(R.id.addobjbtn);
        addObjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                final EditText input = new EditText(view.getContext());
                input.setText(getString(R.string.name));
                alert.setView(input);
                alert.setNegativeButton(view.getContext().getString(R.string.annulla), null);
                final AlertDialog alertd = alert.create();
                alert.setTitle(getString(R.string.addobjtoinv));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = input.getText().toString();

                        inventoryAdapter.addObj(name + "::" + getString(R.string.keeppressedtoedit));
                        inventoryAdapter.notifyDataSetChanged();
                        dialog.cancel();
                        alertd.dismiss();
                    }
                });
                alert.show();
            }
        });

        saveSchedaPG();
    }

    private void saveSchedaPG() {
        String str = state.getString("pgname", null) + "|" +
                state.getString("pgclass", null) + "|" +
                state.getBoolean("inspiration", false) + "|" +
                state.getInt("pglv", 1) + "|" +
                state.getInt("CA", 10) + "|" +
                state.getInt("PF", -1) + "|" +
                state.getInt("PFMAX", -1) + "|" +
                state.getInt("FOR", 10) + "|" +
                state.getInt("DEX", 10) + "|" +
                state.getInt("COS", 10) + "|" +
                state.getInt("INT", 10) + "|" +
                state.getInt("SAG", 10) + "|" +
                state.getInt("CAR", 10) + "|" +
                state.getBoolean("comptsfor", false) + "|" +
                state.getBoolean("comptsdex", false) + "|" +
                state.getBoolean("comptscos", false) + "|" +
                state.getBoolean("comptsint", false) + "|" +
                state.getBoolean("comptssag", false) + "|" +
                state.getBoolean("comptscar", false) + "|" +
                state.getBoolean("compatletica", false) + "|" +
                state.getBoolean("expatletica", false) + "|" +
                state.getBoolean("compacrobazia", false) + "|" +
                state.getBoolean("expacrobazia", false) + "|" +
                state.getBoolean("compfurtivita", false) + "|" +
                state.getBoolean("expfurtivita", false) + "|" +
                state.getBoolean("comprapiditadimano", false) + "|" +
                state.getBoolean("exprapiditadimano", false) + "|" +
                state.getBoolean("compresistenzafisica", false) + "|" +
                state.getBoolean("expresistenzafisica", false) + "|" +
                state.getBoolean("compinvestigare", false) + "|" +
                state.getBoolean("expinvestigare", false) + "|" +
                state.getBoolean("comparcano", false) + "|" +
                state.getBoolean("exparcano", false) + "|" +
                state.getBoolean("compstoria", false) + "|" +
                state.getBoolean("expstoria", false) + "|" +
                state.getBoolean("compreligionefolklore", false) + "|" +
                state.getBoolean("expreligionefolklore", false) + "|" +
                state.getBoolean("compreligionefolklore", false) + "|" +
                state.getBoolean("expreligionefolklore", false) + "|" +
                state.getBoolean("compnatura", false) + "|" +
                state.getBoolean("expnatura", false) + "|" +
                state.getBoolean("compfauna", false) + "|" +
                state.getBoolean("expfauna", false) + "|" +
                state.getBoolean("compsopravvivenza", false) + "|" +
                state.getBoolean("expsopravvivenza", false) + "|" +
                state.getBoolean("compmedicina", false) + "|" +
                state.getBoolean("expmedicina", false) + "|" +
                state.getBoolean("comppercezione", false) + "|" +
                state.getBoolean("exppercezione", false) + "|" +
                state.getBoolean("compintuizione", false) + "|" +
                state.getBoolean("expintuizione", false) + "|" +
                state.getBoolean("compintimidire", false) + "|" +
                state.getBoolean("expintimidire", false) + "|" +
                state.getBoolean("compingannare", false) + "|" +
                state.getBoolean("expingannare", false) + "|" +
                state.getBoolean("compintrattenere", false) + "|" +
                state.getBoolean("expintrattenere", false) + "|" +
                state.getBoolean("comppersuadere", false) + "|" +
                state.getBoolean("exppersuadere", false) + "|" +
                state.getInt("xp", 0) + "|" +
                state.getInt("mp", 0) + "|" +
                state.getInt("mo", 0) + "|" +
                state.getInt("ma", 0) + "|" +
                state.getInt("mr", 0) + "|" +
                state.getString("crediti", "0") + "|" +
                state.getStringSet("inventory", null) + "|" +
                state.getString("inv", "") + "\n";
        FileHelper.saveToFile(str, getApplicationContext(), state.getString("pgname", null) + "PGDATA.txt");
    }

    private void initializeAds() {
        MobileAds.initialize(this, "ca-app-pub-9387595638685451~1613717773");
        AdView mAdView = findViewById(R.id.banner1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public static int mod(int punteggio) {
        double pnt = punteggio;
        return (int) floor(((pnt - 10) / 2));
    }
}
