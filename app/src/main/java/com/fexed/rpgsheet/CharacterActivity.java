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
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.skydoves.balloon.ArrowOrientation;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.lang.Math.floor;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, CheckBox.OnCheckedChangeListener {

    static SharedPreferences state;
    static int[] prof = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13};

    TextView FOR; TextView FORmod;
    TextView DEX; TextView DEXmod;
    TextView COS; TextView COSmod;
    TextView INT; TextView INTmod;
    TextView SAG; TextView SAGmod;
    TextView CAR; TextView CARmod;
    TextView lvtxt; TextView nametxt; TextView classtxt; TextView proftxt;
    TextView CA; TextView PF; TextView PFmax; TextView XP;
    TextView abilitatalenti; ImageView abilitatalentiarrow;
    TextView inventario; ImageView inventarioarrow;
    TextView background; ImageView backgroundarrow;
    TextView attacchi; ImageView attacchiarrow;
    TextView spellatk; TextView spellcd; TextView spellstat; TextView spellmana; Button spellapp;
    Button addmanabtn; Button removemanabtn;
    Button PFplus; Button PFminus;
    Button addranged; Button addmelee; TableLayout rangedatks; TableLayout meleeatks;
    Button addxpbtn;
    EditText cantrip; EditText firstlv; EditText secondlv; EditText thirdlv; EditText fourthlv; EditText fifthlv; EditText sixthlv; EditText seventhlv; EditText eighthlv; EditText ninthlv; EditText pluslv;
    TextView firstlvslots; TextView secondlvslots; TextView thirdlvslots; TextView fourthlvslots; TextView fifthlvslots; TextView sixthlvslots; TextView seventhlvslots; TextView eighthlvslots; TextView ninthlvslots; TextView pluslvslots;
    Button castfirstlv; Button castsecondlv; Button castthirdlv; Button castfourthlv; Button castfifthlv; Button castsixthlv; Button castseventhlv; Button casteightlv; Button castninthlv; Button castpluslv;
    CheckBox inspirationtbn;
    TextView mptxtv; TextView motxtv; TextView matxtv; TextView mrtxtv; TextView totalmtxtv;
    RecyclerView inventoryView;
    int pntfor; int modfor;
    TextView tsfortxt; CheckBox comptsfor;
    TextView atletica; CheckBox compatletica; CheckBox expatletica;
    int pntdex; int moddex;
    TextView tsdextxt; CheckBox comptsdex;
    TextView acrobazia; CheckBox compacrobazia; CheckBox expacrobazia;
    TextView furtivita; CheckBox compfurtivita; CheckBox expfurtivita;
    TextView rapiditadimano; CheckBox comprapiditadimano; CheckBox exprapiditadimano;
    int pntcos; int modcos;
    TextView tscostxt; CheckBox comptscos;
    int pntint; int modint;
    TextView tsinttxt; CheckBox comptsint;
    TextView investigare; CheckBox compinvestigare; CheckBox expinvestigare;
    TextView arcano; CheckBox comparcano; CheckBox exparcano;
    TextView storia; CheckBox compstoria; CheckBox expstoria;
    TextView religionefolklore; CheckBox compreligionefolklore; CheckBox expreligionefolklore;
    TextView natura; CheckBox compnatura; CheckBox expnatura;
    int pntsag; int modsag;
    TextView tssagtxt; CheckBox comptssag;
    TextView sopravvivenza; CheckBox compsopravvivenza; CheckBox expsopravvivenza;
    TextView medicina; CheckBox compmedicina; CheckBox expmedicina;
    TextView percezione; CheckBox comppercezione; CheckBox exppercezione;
    TextView intuizione; CheckBox compintuizione; CheckBox expintuizione;
    int pntcar; int modcar;
    TextView tscartxt; CheckBox comptscar;
    TextView intimidire; CheckBox compintimidire; CheckBox expintimidire;
    TextView ingannare; CheckBox compingannare; CheckBox expingannare;
    TextView intrattenere; CheckBox compintrattenere; CheckBox expintrattenere;
    TextView persuadere; CheckBox comppersuadere; CheckBox exppersuadere;

    @Override
    protected void onCreate (Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.charactersheet);
        state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(R.string.pgcharsheet, state.getString("pgname", "?")));

        //Launches
        int n = state.getInt("launchn", 0);
        n++;
        if (n % 5 == 0) {
            Toast.makeText(this, getString(R.string.ratepls), Toast.LENGTH_LONG).show();
        }
        state.edit().putInt("launchn", n).apply();

        preparaSchedaPG();
        initializeAds();

        Bundle bndl = new Bundle();
        bndl.putString("Name", state.getString("pgname", "nonsettato"));
        bndl.putInt("launchtimes", n);
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.APP_OPEN, bndl);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);

        //Dice roller tutorial
        boolean diceroller = state.getBoolean("diceroller", false);
        if (!diceroller) {
            TextView lvltxtv = findViewById(R.id.pglvtxt);
            Balloon balloon = new Balloon.Builder(getApplicationContext())
                    .setText(getString(R.string.try_diceroller))
                    .setPadding(5)
                    .setDismissWhenTouchOutside(true)
                    .setArrowVisible(false)
                    .setBackgroundColorResource(R.color.colorPrimaryDark)
                    .setBalloonAnimation(BalloonAnimation.ELASTIC)
                    .build();
            balloon.showAlignTop(lvltxtv, 0, 45);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            Intent myIntent = new Intent(CharacterActivity.this, Settings.class);
            startActivity(myIntent);
        } else if (item.getItemId() == R.id.dice) {
            DiceDialog inputdialog = new DiceDialog(this, state);
            inputdialog.show();
        } else if (item.getItemId() == R.id.sleep) {
            String tmps;
            int tmpi;

            tmpi = state.getInt("PFMAX", 0);
            tmps = "" + tmpi;
            state.edit().putInt("PF", tmpi).apply();
            PF.setText(tmps);

            tmpi = state.getInt("firstlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currfirstlvslots", tmpi).apply();
            firstlvslots.setText(tmps);

            tmpi = state.getInt("secondlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currsecondlvslots", tmpi).apply();
            secondlvslots.setText(tmps);

            tmpi = state.getInt("thirdlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currthirdlvslots", tmpi).apply();
            thirdlvslots.setText(tmps);

            tmpi = state.getInt("fourthlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currfourthlvslots", tmpi).apply();
            fourthlvslots.setText(tmps);

            tmpi = state.getInt("fifthlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currfifthlvslots", tmpi).apply();
            fifthlvslots.setText(tmps);

            tmpi = state.getInt("sixthlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currsixthlvslots", tmpi).apply();
            sixthlvslots.setText(tmps);

            tmpi = state.getInt("seventhlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currseventhlvslots", tmpi).apply();
            seventhlvslots.setText(tmps);

            tmpi = state.getInt("eighthlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("curreighthlvslots", tmpi).apply();
            eighthlvslots.setText(tmps);

            tmpi = state.getInt("ninthlvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currninthlvslots", tmpi).apply();
            ninthlvslots.setText(tmps);

            tmpi = state.getInt("pluslvslots", 0);
            tmps = tmpi + "/" + tmpi;
            state.edit().putInt("currpluslvslots", tmpi).apply();
            pluslvslots.setText(tmps);

            Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.resttxt), Snackbar.LENGTH_LONG).show();
        }
        return true;
    }

    private void preparaSchedaPG() {
        FOR = findViewById(R.id.FOR);
        FORmod = findViewById(R.id.FORmod);
        DEX = findViewById(R.id.DEX);
        DEXmod = findViewById(R.id.DEXmod);
        COS = findViewById(R.id.COS);
        COSmod = findViewById(R.id.COSmod);
        INT = findViewById(R.id.INT);
        INTmod = findViewById(R.id.INTmod);
        SAG = findViewById(R.id.SAG);
        SAGmod = findViewById(R.id.SAGmod);
        CAR = findViewById(R.id.CAR);
        CARmod = findViewById(R.id.CARmod);
        lvtxt = findViewById(R.id.pglvtxt);
        nametxt = findViewById(R.id.pgnametxt);
        classtxt = findViewById(R.id.pgclasstxt);
        proftxt = findViewById(R.id.proftxt);
        CA = findViewById(R.id.CA);
        PF = findViewById(R.id.PF);
        PFmax = findViewById(R.id.PFmax);
        XP = findViewById(R.id.pgxptxtv);
        abilitatalenti = findViewById(R.id.skillstitle);
        abilitatalentiarrow = findViewById(R.id.dwna1);
        inventario = findViewById(R.id.invtitle);
        inventarioarrow = findViewById(R.id.dwna3);
        background = findViewById(R.id.bgtitle);
        backgroundarrow = findViewById(R.id.dwna4);
        attacchi = findViewById(R.id.atktitle);
        attacchiarrow = findViewById(R.id.dwna2);
        spellatk = findViewById(R.id.spellatktxt);
        spellcd = findViewById(R.id.spellcdtxt);
        spellstat = findViewById(R.id.spelstatselection);
        spellmana = findViewById(R.id.manatxt);
        PFplus = findViewById(R.id.pfplus);
        PFminus = findViewById(R.id.pfminus);
        addranged = findViewById(R.id.addrangedatk);
        addmelee = findViewById(R.id.addmeleeatk);
        spellapp = findViewById(R.id.spellappbtn);
        addmanabtn = findViewById(R.id.addmana);
        removemanabtn = findViewById(R.id.removemana);
        addxpbtn = findViewById(R.id.addxpbtn);
        cantrip = findViewById(R.id.cantriplist);
        firstlv = findViewById(R.id.firstlist);
        secondlv = findViewById(R.id.secondlist);
        thirdlv = findViewById(R.id.thirdlist);
        fourthlv = findViewById(R.id.fourthlsit);
        fifthlv = findViewById(R.id.fifthlist);
        sixthlv = findViewById(R.id.sixthlist);
        seventhlv = findViewById(R.id.seventhlist);
        eighthlv = findViewById(R.id.eigththlist);
        ninthlv = findViewById(R.id.ninthlist);
        pluslv = findViewById(R.id.pluslist);
        firstlvslots = findViewById(R.id.slotfirsttxtv);
        secondlvslots = findViewById(R.id.slotsecondtxtv);
        thirdlvslots = findViewById(R.id.slotthirdtxtv);
        fourthlvslots = findViewById(R.id.slotfourthtxtv);
        fifthlvslots = findViewById(R.id.slotfifthtxtv);
        sixthlvslots = findViewById(R.id.slotsixthtxtv);
        seventhlvslots = findViewById(R.id.slotseventhtxtv);
        eighthlvslots = findViewById(R.id.sloteigthtxtv);
        ninthlvslots = findViewById(R.id.slotninthtxtv);
        pluslvslots = findViewById(R.id.slotplustxtv);
        castfirstlv = findViewById(R.id.castfirstlv);
        castsecondlv = findViewById(R.id.castsecondlv);
        castthirdlv = findViewById(R.id.castthirdlv);
        castfourthlv = findViewById(R.id.castfourthlv);
        castfifthlv = findViewById(R.id.castfifthlv);
        castsixthlv = findViewById(R.id.castsixthlv);
        castseventhlv = findViewById(R.id.castseventhlv);
        casteightlv = findViewById(R.id.casteightlv);
        castninthlv = findViewById(R.id.castninthlv);
        castpluslv = findViewById(R.id.castpluslv);
        inspirationtbn = findViewById(R.id.inspirationbtn);
        rangedatks = findViewById(R.id.rangedatks);
        meleeatks = findViewById(R.id.meleeatks);
        inventoryView = findViewById(R.id.inventoryRecV);

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

        spellstat.setOnClickListener(this);
        abilitatalenti.setOnClickListener(this);
        abilitatalentiarrow.setOnClickListener(this);
        inventario.setOnClickListener(this);
        inventarioarrow.setOnClickListener(this);
        background.setOnClickListener(this);
        backgroundarrow.setOnClickListener(this);
        attacchi.setOnClickListener(this);
        attacchiarrow.setOnClickListener(this);
        lvtxt.setOnLongClickListener(this);
        nametxt.setOnLongClickListener(this);

        tempstr = "+" + prof[state.getInt("pglv",1)-1];
        proftxt.setText(tempstr);

        spellmana.setOnLongClickListener(this);
        tempstr = state.getInt("spellmana", 0) + "/" + state.getInt("spellmanamax", 0);
        spellmana.setText(tempstr);

        addmanabtn.setOnClickListener(this);
        removemanabtn.setOnClickListener(this);
        classtxt.setOnLongClickListener(this);
        CA.setOnLongClickListener(this);
        PF.setOnLongClickListener(this);
        PFplus.setOnClickListener(this);
        PFplus.setOnLongClickListener(this);
        PFminus.setOnClickListener(this);
        PFminus.setOnLongClickListener(this);
        PFmax.setOnLongClickListener(this);
        FOR.setOnLongClickListener(this);
        DEX.setOnLongClickListener(this);
        COS.setOnLongClickListener(this);
        INT.setOnLongClickListener(this);
        SAG.setOnLongClickListener(this);
        CAR.setOnLongClickListener(this);

        tsfortxt = findViewById(R.id.TSFOR);
        comptsfor = findViewById(R.id.comptsfor);
        comptsfor.setChecked(state.getBoolean("comptsfor", false));
        int ts = mod(state.getInt("FOR", 10)) + ((comptsfor.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsfortxt.setText(tempstr);
        comptsfor.setOnClickListener(this);

        tsdextxt = findViewById(R.id.TSDEX);
        comptsdex = findViewById(R.id.comptsdex);
        comptsdex.setChecked(state.getBoolean("comptsdex", false));
        ts = mod((state.getInt("DEX", 10))) + ((comptsdex.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsdextxt.setText(tempstr);
        comptsdex.setOnClickListener(this);

        tscostxt = findViewById(R.id.TSCOS);
        comptscos = findViewById(R.id.comptscos);
        comptscos.setChecked(state.getBoolean("comptscos", false));
        ts = mod((state.getInt("COS", 10))) + ((comptscos.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscostxt.setText(tempstr);
        comptscos.setOnClickListener(this);

        tsinttxt = findViewById(R.id.TSINT);
        comptsint = findViewById(R.id.comptsint);
        comptsint.setChecked(state.getBoolean("comptsint", false));
        ts = mod((state.getInt("INT", 10))) + ((comptsint.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsinttxt.setText(tempstr);
        comptsint.setOnClickListener(this);

        tssagtxt = findViewById(R.id.TSSAG);
        comptssag = findViewById(R.id.comptssag);
        comptssag.setChecked(state.getBoolean("comptssag", false));
        ts = mod((state.getInt("SAG", 10))) + ((comptssag.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tssagtxt.setText(tempstr);
        comptssag.setOnClickListener(this);

        tscartxt = findViewById(R.id.TSCAR);
        comptscar = findViewById(R.id.comptscar);
        comptscar.setChecked(state.getBoolean("comptscar", false));
        ts = mod((state.getInt("CAR", 10))) + ((comptscar.isChecked()) ? prof[lv-1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscartxt.setText(tempstr);
        comptscar.setOnClickListener(this);

        atletica = findViewById(R.id.atletica);
        compatletica = findViewById(R.id.compatletica);
        expatletica = findViewById(R.id.expatletica);
        compatletica.setOnCheckedChangeListener(this);
        expatletica.setOnCheckedChangeListener(this);
        compatletica.setChecked(state.getBoolean("compatletica", false));
        expatletica.setChecked(state.getBoolean("expatletica", false));
        bonus = mod((state.getInt("FOR", 10)))+ ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        atletica.setText(tempstr);

        acrobazia = findViewById(R.id.acrobazia);
        compacrobazia = findViewById(R.id.compacrobazia);
        expacrobazia = findViewById(R.id.expacrobazia);
        compacrobazia.setOnCheckedChangeListener(this);
        expacrobazia.setOnCheckedChangeListener(this);
        compacrobazia.setChecked(state.getBoolean("compacrobazia", false));
        expacrobazia.setChecked(state.getBoolean("expacrobazia", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        acrobazia.setText(tempstr);

        furtivita = findViewById(R.id.furtivita);
        compfurtivita = findViewById(R.id.compfurtivita);
        expfurtivita = findViewById(R.id.expfurtivita);
        compfurtivita.setOnCheckedChangeListener(this);
        expfurtivita.setOnCheckedChangeListener(this);
        compfurtivita.setChecked(state.getBoolean("compfurtivita", false));
        expfurtivita.setChecked(state.getBoolean("expfurtivita", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        furtivita.setText(tempstr);

        rapiditadimano = findViewById(R.id.rapiditadimano);
        comprapiditadimano = findViewById(R.id.comprapiditadimano);
        exprapiditadimano = findViewById(R.id.exprapiditadimano);
        comprapiditadimano.setOnCheckedChangeListener(this);
        exprapiditadimano.setOnCheckedChangeListener(this);
        comprapiditadimano.setChecked(state.getBoolean("comprapiditadimano", false));
        exprapiditadimano.setChecked(state.getBoolean("exprapiditadimano", false));
        bonus = mod((state.getInt("DEX", 10)))+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        rapiditadimano.setText(tempstr);

        investigare = findViewById(R.id.investigare);
        compinvestigare = findViewById(R.id.compinvestigare);
        expinvestigare = findViewById(R.id.expinvestigare);
        compinvestigare.setOnCheckedChangeListener(this);
        expinvestigare.setOnCheckedChangeListener(this);
        compinvestigare.setChecked(state.getBoolean("compinvestigare", false));
        expinvestigare.setChecked(state.getBoolean("expinvestigare", false));
        bonus = mod((state.getInt("INT", 10))) + ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        investigare.setText(tempstr);

        arcano = findViewById(R.id.arcano);
        comparcano = findViewById(R.id.comparcano);
        exparcano = findViewById(R.id.exparcano);
        comparcano.setOnCheckedChangeListener(this);
        exparcano.setOnCheckedChangeListener(this);
        comparcano.setChecked(state.getBoolean("comparcano", false));
        exparcano.setChecked(state.getBoolean("exparcano", false));
        bonus = mod((state.getInt("INT", 10))) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        arcano.setText(tempstr);

        storia = findViewById(R.id.storia);
        compstoria = findViewById(R.id.compstoria);
        expstoria = findViewById(R.id.expstoria);
        compstoria.setOnCheckedChangeListener(this);
        expstoria.setOnCheckedChangeListener(this);
        compstoria.setChecked(state.getBoolean("compstoria", false));
        expstoria.setChecked(state.getBoolean("expstoria", false));
        bonus = mod((state.getInt("INT", 10))) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        storia.setText(tempstr);

        religionefolklore = findViewById(R.id.religionefolklore);
        compreligionefolklore = findViewById(R.id.compreligionefolklore);
        expreligionefolklore = findViewById(R.id.expreligionefolklore);
        compreligionefolklore.setOnCheckedChangeListener(this);
        expreligionefolklore.setOnCheckedChangeListener(this);
        compreligionefolklore.setChecked(state.getBoolean("compreligionefolklore", false));
        expreligionefolklore.setChecked(state.getBoolean("expreligionefolklore", false));
        bonus = mod((state.getInt("INT", 10))) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        religionefolklore.setText(tempstr);

        natura = findViewById(R.id.natura);
        compnatura = findViewById(R.id.compnatura);
        expnatura = findViewById(R.id.expnatura);
        compnatura.setOnCheckedChangeListener(this);
        expnatura.setOnCheckedChangeListener(this);
        compnatura.setChecked(state.getBoolean("compnatura", false));
        expnatura.setChecked(state.getBoolean("expnatura", false));
        bonus = mod((state.getInt("INT", 10))) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        natura.setText(tempstr);

        sopravvivenza = findViewById(R.id.sopravvivenza);
        compsopravvivenza = findViewById(R.id.compsopravvivenza);
        expsopravvivenza = findViewById(R.id.expsopravvivenza);
        compsopravvivenza.setOnCheckedChangeListener(this);
        expsopravvivenza.setOnCheckedChangeListener(this);
        compsopravvivenza.setChecked(state.getBoolean("compsopravvivenza", false));
        expsopravvivenza.setChecked(state.getBoolean("expsopravvivenza", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        sopravvivenza.setText(tempstr);

        medicina = findViewById(R.id.medicina);
        compmedicina = findViewById(R.id.compmedicina);
        expmedicina = findViewById(R.id.expmedicina);
        compmedicina.setOnCheckedChangeListener(this);
        expmedicina.setOnCheckedChangeListener(this);
        compmedicina.setChecked(state.getBoolean("compmedicina", false));
        expmedicina.setChecked(state.getBoolean("expmedicina", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        medicina.setText(tempstr);

        percezione = findViewById(R.id.percezione);
        comppercezione = findViewById(R.id.comppercezione);
        exppercezione = findViewById(R.id.exppercezione);
        comppercezione.setOnCheckedChangeListener(this);
        exppercezione.setOnCheckedChangeListener(this);
        comppercezione.setChecked(state.getBoolean("comppercezione", false));
        exppercezione.setChecked(state.getBoolean("exppercezione", false));
        bonus = mod((state.getInt("SAG", 10))) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        percezione.setText(tempstr);

        intuizione = findViewById(R.id.intuizione);
        compintuizione = findViewById(R.id.compintuizione);
        expintuizione = findViewById(R.id.expintuizione);
        compintuizione.setOnCheckedChangeListener(this);
        expintuizione.setOnCheckedChangeListener(this);
        compintuizione.setChecked(state.getBoolean("compintuizione", false));
        expintuizione.setChecked(state.getBoolean("expintuizione", false));
        bonus = mod((state.getInt("SAG", 10))) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intuizione.setText(tempstr);

        intimidire = findViewById(R.id.intimidire);
        compintimidire = findViewById(R.id.compintimidire);
        expintimidire = findViewById(R.id.expintimidire);
        compintimidire.setOnCheckedChangeListener(this);
        expintimidire.setOnCheckedChangeListener(this);
        compintimidire.setChecked(state.getBoolean("compintimidire", false));
        expintimidire.setChecked(state.getBoolean("expintimidire", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intimidire.setText(tempstr);

        ingannare = findViewById(R.id.ingannare);
        compingannare = findViewById(R.id.compingannare);
        expingannare = findViewById(R.id.expingannare);
        compingannare.setOnCheckedChangeListener(this);
        expingannare.setOnCheckedChangeListener(this);
        compingannare.setChecked(state.getBoolean("compingannare", false));
        expingannare.setChecked(state.getBoolean("expingannare", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        ingannare.setText(tempstr);

        intrattenere = findViewById(R.id.intrattenere);
        compintrattenere = findViewById(R.id.compintrattenere);
        expintrattenere = findViewById(R.id.expintrattenere);
        compintrattenere.setOnCheckedChangeListener(this);
        expintrattenere.setOnCheckedChangeListener(this);
        compintrattenere.setChecked(state.getBoolean("compintrattenere", false));
        expintrattenere.setChecked(state.getBoolean("expintrattenere", false));
        bonus = mod((state.getInt("CAR", 10))) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intrattenere.setText(tempstr);

        persuadere = findViewById(R.id.persuadere);
        comppersuadere = findViewById(R.id.comppersuadere);
        exppersuadere = findViewById(R.id.exppersuadere);
        comppersuadere.setOnCheckedChangeListener(this);
        exppersuadere.setOnCheckedChangeListener(this);
        comppersuadere.setChecked(state.getBoolean("comppersuadere", false));
        exppersuadere.setChecked(state.getBoolean("exppersuadere", false));
        bonus = mod((state.getInt("CAR", 10))) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        persuadere.setText(tempstr);

        EditText linguetxt = findViewById(R.id.linguetxt);
        linguetxt.setText(state.getString("linguetxt", ""));
        linguetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                state.edit().putString("abilitatxt", editable.toString()).apply();
            }
        });
        abilitatxt.clearFocus();

        mptxtv = findViewById(R.id.mptxtv);
        motxtv = findViewById(R.id.motxtv);
        matxtv = findViewById(R.id.matxtv);
        mrtxtv = findViewById(R.id.mrtxtv);
        totalmtxtv = findViewById(R.id.totalpgmoneytxtv);

        double money;
        int mp = state.getInt("mp", 0);
        int mo = state.getInt("mo", 0);
        int ma = state.getInt("ma", 0);
        int mr = state.getInt("mr", 0);
        money = Math.ceil(mp*10 + mo + ma*0.1 + mr*0.01);
        String txt = String.format(Locale.getDefault(), "%.0f", money);
        String strstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
        totalmtxtv.setText(strstr);

        tempstr = mp + "";
        mptxtv.setText(tempstr);
        mptxtv.setOnLongClickListener(this);
        tempstr = mo + "";
        motxtv.setText(tempstr);
        motxtv.setOnLongClickListener(this);
        tempstr = ma + "";
        matxtv.setText(tempstr);
        matxtv.setOnLongClickListener(this);
        tempstr = mr + "";
        mrtxtv.setText(tempstr);
        mrtxtv.setOnLongClickListener(this);

        EditText invtxt = findViewById(R.id.invtxt);
        invtxt.setText(state.getString("inv", ""));
        invtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            tempstr = suffixb + bonusb;
            bonusrange.setText(tempstr);
            tempstr = "+" + prof[lv-1];
            comprange.setText(tempstr);
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        firstlvslots.setText(new StringBuilder().append(state.getInt("currfirstlvslots", 0)).append("/").append(state.getInt("firstlvslots", 0)));
        castfirstlv.setOnClickListener(this);


        secondlv.setText(state.getString("secondlv", ""));
        secondlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        secondlvslots.setText(new StringBuilder().append(state.getInt("currsecondlvslots", 0)).append("/").append(state.getInt("secondlvslots", 0)));
        castsecondlv.setOnClickListener(this);

        thirdlv.setText(state.getString("thirdlv", ""));
        thirdlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        thirdlvslots.setText(new StringBuilder().append(state.getInt("currthirdlvslots", 0)).append("/").append(state.getInt("thirdlvslots", 0)));
        castthirdlv.setOnClickListener(this);

        fourthlv.setText(state.getString("fourthlv", ""));
        fourthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        fourthlvslots.setText(new StringBuilder().append(state.getInt("currfourthlvslots", 0)).append("/").append(state.getInt("fourthlvslots", 0)));
        castfourthlv.setOnClickListener(this);

        fifthlv.setText(state.getString("fifthlv", ""));
        fifthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        fifthlvslots.setText(new StringBuilder().append(state.getInt("currfifthlvslots", 0)).append("/").append(state.getInt("fifthlvslots", 0)));
        castfifthlv.setOnClickListener(this);

        sixthlv.setText(state.getString("sixthlv", ""));
        sixthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        sixthlvslots.setText(new StringBuilder().append(state.getInt("currsixthlvslots", 0)).append("/").append(state.getInt("sixthlvslots", 0)));
        castsixthlv.setOnClickListener(this);

        seventhlv.setText(state.getString("seventhlv", ""));
        seventhlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        seventhlvslots.setText(new StringBuilder().append(state.getInt("currseventhlvslots", 0)).append("/").append(state.getInt("seventhlvslots", 0)));
        castseventhlv.setOnClickListener(this);

        eighthlv.setText(state.getString("eighthlv", ""));
        eighthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        eighthlvslots.setText(new StringBuilder().append(state.getInt("curreighthlvslots", 0)).append("/").append(state.getInt("eighthlvslots", 0)));
        casteightlv.setOnClickListener(this);

        ninthlv.setText(state.getString("ninthlv", ""));
        ninthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        ninthlvslots.setText(new StringBuilder().append(state.getInt("currninthlvslots", 0)).append("/").append(state.getInt("ninthlvslots", 0)));
        castninthlv.setOnClickListener(this);

        pluslv.setText(state.getString("pluslv", ""));
        pluslv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

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
        pluslvslots.setText(new StringBuilder().append(state.getInt("currpluslvslots", 0)).append("/").append(state.getInt("pluslvslots", 0)));
        castpluslv.setOnClickListener(this);

        inspirationtbn.setChecked(state.getBoolean("inspiration", false));
        inspirationtbn.setOnCheckedChangeListener(this);

        tempstr = state.getInt("xp", 0) + " xp";
        XP.setText(tempstr);
        XP.setOnLongClickListener(this);
        addxpbtn.setOnClickListener(this);

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

                        inventoryAdapter.addObj(name.replace("::", "") + "::" + getString(R.string.keeppressedtoedit));
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
        return (int) floor((((double) punteggio - 10) / 2));
    }

    @Override
    public void onClick(View view) {
        int lv = state.getInt("pglv", 1);
        String tempstr, suffix;
        switch (view.getId()) {
            case R.id.spelstatselection:
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
                break;
            case R.id.dwna1:
            case R.id.skillstitle:
                LinearLayout skilllyt = findViewById(R.id.skills);
                if (skilllyt.getVisibility() == View.VISIBLE) {
                    skilllyt.setVisibility(View.GONE);
                    abilitatalentiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    skilllyt.setVisibility(View.VISIBLE);
                    abilitatalentiarrow.setImageResource(R.drawable.uparrow);
                }
                break;
            case R.id.dwna2:
            case R.id.atktitle:
                LinearLayout atklyt = findViewById(R.id.atk);
                if (atklyt.getVisibility() == View.VISIBLE) {
                    atklyt.setVisibility(View.GONE);
                    attacchiarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    atklyt.setVisibility(View.VISIBLE);
                    attacchiarrow.setImageResource(R.drawable.uparrow);
                }
                break;
            case R.id.dwna3:
            case R.id.invtitle:
                LinearLayout invlyt = findViewById(R.id.inventory);
                if (invlyt.getVisibility() == View.VISIBLE) {
                    invlyt.setVisibility(View.GONE);
                    inventarioarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    invlyt.setVisibility(View.VISIBLE);
                    inventarioarrow.setImageResource(R.drawable.uparrow);
                }
                break;
            case R.id.dwna4:
            case R.id.bgtitle:
                LinearLayout bglyt = findViewById(R.id.background);
                if (bglyt.getVisibility() == View.VISIBLE) {
                    bglyt.setVisibility(View.GONE);
                    backgroundarrow.setImageResource(R.drawable.downarrow);
                }
                else {
                    bglyt.setVisibility(View.VISIBLE);
                    backgroundarrow.setImageResource(R.drawable.uparrow);
                }
                break;
            case R.id.addmana:
                int mana = state.getInt("spellmana", 0);
                int manamax = state.getInt("spellmanamax", 0);
                mana++;
                mana = Math.min(mana, manamax);
                state.edit().putInt("spellmana", mana).apply();
                tempstr = mana + "/" + manamax;
                spellmana.setText(tempstr);
                saveSchedaPG();
                break;
            case R.id.removemana:
                int manam = state.getInt("spellmana", 0);
                manam--;
                manam = Math.max(manam, 0);
                state.edit().putInt("spellmana", manam).apply();
                tempstr = manam + "/" + state.getInt("spellmanamax", 0);
                spellmana.setText(tempstr);
                saveSchedaPG();
                break;
            case R.id.pfplus:
                int pf = state.getInt("PF", 0);
                pf++;
                int pfmax = state.getInt("PFMAX", pf);
                if (pf > pfmax) pf = pfmax;
                state.edit().putInt("PF", pf).apply();
                tempstr = pf + "";
                PF.setText(tempstr);
                saveSchedaPG();
                break;
            case R.id.pfminus:
                int pfm = state.getInt("PF", 0);
                pfm--;
                state.edit().putInt("PF", pfm).apply();
                tempstr = pfm + "";
                PF.setText(tempstr);
                saveSchedaPG();
                break;
            case R.id.comptsfor:
                state.edit().putBoolean("comptsfor", comptsfor.isChecked()).apply();
                int tsf = mod(state.getInt("FOR", 10)) + ((comptsfor.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsf >= 0) ? "+" : "";
                tempstr = suffix + tsf;
                tsfortxt.setText(tempstr);
                break;
            case R.id.comptsdex:
                state.edit().putBoolean("comptsdex", comptsdex.isChecked()).apply();
                int tsd = mod((state.getInt("DEX", 10))) + ((comptsdex.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsd >= 0) ? "+" : "";
                tempstr = suffix + tsd;
                tsdextxt.setText(tempstr);
                break;
            case R.id.comptscos:
                state.edit().putBoolean("comptscos", comptscos.isChecked()).apply();
                int tsc = mod((state.getInt("COS", 10))) + ((comptscos.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsc >= 0) ? "+" : "";
                tempstr = suffix + tsc;
                tscostxt.setText(tempstr);
                break;
            case R.id.comptsint:
                state.edit().putBoolean("comptsint", comptsint.isChecked()).apply();
                int tsi = mod((state.getInt("INT", 10))) + ((comptsint.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsi >= 0) ? "+" : "";
                tempstr = suffix + tsi;
                tsinttxt.setText(tempstr);
                break;
            case R.id.comptssag:
                state.edit().putBoolean("comptssag", comptssag.isChecked()).apply();
                int tsa = mod((state.getInt("SAG", 10))) + ((comptssag.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsa >= 0) ? "+" : "";
                tempstr = suffix + tsa;
                tssagtxt.setText(tempstr);
                break;
            case R.id.comptscar:
                state.edit().putBoolean("comptscar", comptscar.isChecked()).apply();
                int tsca = mod((state.getInt("CAR", 10)))+ ((comptscar.isChecked()) ? prof[lv-1] : 0);
                suffix = (tsca >= 0) ? "+" : "";
                tempstr = suffix + tsca;
                tscartxt.setText(tempstr);
                break;
            case R.id.addxpbtn:
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

                        String tempstr = xp + " xp";
                        XP.setText(tempstr);
                        state.edit().putInt("xp", xp).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                break;
            case R.id.castfirstlv:
                int n = state.getInt("currfirstlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                firstlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("firstlvslots", 0)));
                state.edit().putInt("currfirstlvslots", n).apply();
                break;
            case R.id.castsecondlv:
                n = state.getInt("currsecondlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                secondlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("secondlvslots", 0)));
                state.edit().putInt("currsecondlvslots", n).apply();
                break;
            case R.id.castthirdlv:
                n = state.getInt("currthirdlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                thirdlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("thirdlvslots", 0)));
                state.edit().putInt("currthirdlvslots", n).apply();
                break;
            case R.id.castfourthlv:
                n = state.getInt("currfourthlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                fourthlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("fourthlvslots", 0)));
                state.edit().putInt("currfourthlvslots", n).apply();
                break;
            case R.id.castfifthlv:
                n = state.getInt("currfifthlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                fifthlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("fifthlvslots", 0)));
                state.edit().putInt("currfifthlvslots", n).apply();
                break;
            case R.id.castsixthlv:
                n = state.getInt("currsixthlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                sixthlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("sixthlvslots", 0)));
                state.edit().putInt("currsixthlvslots", n).apply();
                break;
            case R.id.castseventhlv:
                n = state.getInt("currseventhlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                seventhlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("seventhlvslots", 0)));
                state.edit().putInt("currseventhlvslots", n).apply();
                break;
            case R.id.casteightlv:
                n = state.getInt("curreighthlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                eighthlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("eighthlvslots", 0)));
                state.edit().putInt("curreighthlvslots", n).apply();
                break;
            case R.id.castninthlv:
                n = state.getInt("currninthlvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                ninthlvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("ninthlvslots", 0)));
                state.edit().putInt("currninthlvslots", n).apply();
                break;
            case R.id.castpluslv:
                n = state.getInt("currpluslvslots", 0);
                n = (n > 0) ? n - 1 : 0;
                pluslvslots.setText(new StringBuilder().append(n).append("/").append(state.getInt("pluslvslots", 0)));
                state.edit().putInt("currpluslvslots", n).apply();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(CharacterActivity.this);
        final EditText input = new EditText(CharacterActivity.this.getApplicationContext());
        final AlertDialog alertd;
        String tempstr;
        switch (view.getId()) {
            case R.id.pglvtxt:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertd = alert.create();
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
            case R.id.pgnametxt:
                input.setText(state.getString("pgname", ""));
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.manatxt:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.pgclasstxt:
                input.setText(state.getString("pgclass", ""));
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.CA:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = state.getInt("CA", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertd = alert.create();
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
            case R.id.PF:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = state.getInt("PF", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.pfplus:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = 0 + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.pfminus:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = 0 + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.PFmax:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = state.getInt("PFMAX", 0) + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.FOR:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
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
            case R.id.DEX:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.dex));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        DEX.setText(tempstr);
                        tempstr = suffix + mod;
                        DEXmod.setText(tempstr);
                        state.edit().putInt("DEX", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.COS:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.cos));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        COS.setText(tempstr);
                        tempstr = suffix + mod;
                        COSmod.setText(tempstr);
                        state.edit().putInt("COS", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.INT:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.inte));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        INT.setText(tempstr);
                        tempstr = suffix + mod;
                        INTmod.setText(tempstr);
                        state.edit().putInt("INT", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.SAG:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.sag));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        SAG.setText(tempstr);
                        tempstr = suffix + mod;
                        SAGmod.setText(tempstr);
                        state.edit().putInt("SAG", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.CAR:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insert) + " " + getString(R.string.car));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        int mod = mod(pnt);
                        String suffix = (mod >= 0) ? "+" : "";

                        String tempstr = pnt + "";
                        CAR.setText(tempstr);
                        tempstr = suffix + mod;
                        CARmod.setText(tempstr);
                        state.edit().putInt("CAR", pnt).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.mptxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgsplatpieces, state.getString("pgname", null)));
                tempstr = state.getInt("mp", 0) + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        mptxtv.setText(tempstr);
                        state.edit().putInt("mp", monete).apply();
                        int mo = state.getInt("mo", 0);
                        int ma = state.getInt("ma", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(monete*10 + mo + ma*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.motxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgsgoldpieces, state.getString("pgname", null)));
                tempstr = state.getInt("mo", 0) + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        motxtv.setText(tempstr);
                        state.edit().putInt("mo", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int ma = state.getInt("ma", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(mp*10 + monete + ma*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.matxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgssilvpieces, state.getString("pgname", null)));
                tempstr = state.getInt("ma", 0) + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        matxtv.setText(tempstr);
                        state.edit().putInt("ma", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int mo = state.getInt("mo", 0);
                        int mr = state.getInt("mr", 0);
                        double moneteTot =  Math.ceil(mp*10 + mo + monete*0.1 + mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.mrtxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgscopppieces, state.getString("pgname", null)));
                tempstr = state.getInt("mr", 0) + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        mrtxtv.setText(tempstr);
                        state.edit().putInt("mr", monete).apply();
                        int mp = state.getInt("mp", 0);
                        int mo = state.getInt("mo", 0);
                        int ma = state.getInt("ma", 0);
                        double moneteTot =  Math.ceil(mp*10 + mo + ma*0.1 + monete*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.pgxptxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertxpof) + " " + state.getString("pgname", null));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int xp = Integer.parseInt(input.getText().toString());

                        String tempstr = xp + " xp";
                        XP.setText(tempstr);
                        state.edit().putInt("xp", xp).apply();
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                return true;
            default: return false;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.inspirationbtn: state.edit().putBoolean("inspiration", b).apply(); saveSchedaPG(); break;
            //FOR
            case R.id.compatletica: compSwitch(b, compatletica, expatletica, atletica, "compatletica", "FOR"); break;
            case R.id.expatletica: expSwitch(compatletica, expatletica, atletica, "expatletica", "FOR"); break;
            //DEX
            case R.id.compacrobazia: compSwitch(b, compacrobazia, expacrobazia, acrobazia, "compacrobazia", "DEX"); break;
            case R.id.expacrobazia: expSwitch(compacrobazia, expacrobazia, acrobazia, "compacrobazia", "DEX"); break;
            case R.id.compfurtivita: compSwitch(b, compfurtivita, expfurtivita, furtivita, "compfurtivita", "DEX"); break;
            case R.id.expfurtivita: expSwitch(compfurtivita, expfurtivita, furtivita, "expfurtivita", "DEX"); break;
            case R.id.comprapiditadimano: compSwitch(b, comprapiditadimano, exprapiditadimano, rapiditadimano, "comprapiditadimano", "DEX"); break;
            case R.id.exprapiditadimano: expSwitch(comprapiditadimano, exprapiditadimano, rapiditadimano, "exprapiditadimano", "DEX"); break;
            //INT
            case R.id.compinvestigare: compSwitch(b, compinvestigare, expinvestigare, investigare, "compinvestigare", "INT"); break;
            case R.id.expinvestigare: expSwitch(compinvestigare, expinvestigare, investigare, "expinvestigare", "INT"); break;
            case R.id.comparcano: compSwitch(b, comparcano, exparcano, arcano, "comparcano", "INT"); break;
            case R.id.exparcano: expSwitch(comparcano, exparcano, arcano, "exparcano", "INT"); break;
            case R.id.compstoria: compSwitch(b, compstoria, expstoria, storia, "compstoria", "INT"); break;
            case R.id.expstoria: expSwitch(compstoria, expstoria, storia, "expstoria", "INT"); break;
            case R.id.compreligionefolklore: compSwitch(b, compreligionefolklore, expreligionefolklore, religionefolklore, "compreligionefolklore", "INT"); break;
            case R.id.expreligionefolklore: expSwitch(compreligionefolklore, expreligionefolklore, religionefolklore, "expreligionefolklore", "INT"); break;
            case R.id.compnatura: compSwitch(b, compnatura, expnatura, natura, "compnatura", "INT"); break;
            case R.id.expnatura: expSwitch(compnatura, expnatura, natura, "expnatura", "INT"); break;
            //SAG
            case R.id.compsopravvivenza: compSwitch(b, compsopravvivenza, expsopravvivenza, sopravvivenza, "compsopravvivenza", "SAG"); break;
            case R.id.expsopravvivenza: expSwitch(compsopravvivenza, expsopravvivenza, sopravvivenza, "expsopravvivenza", "SAG"); break;
            case R.id.compmedicina: compSwitch(b, compmedicina, expmedicina, medicina, "compmedicina", "SAG"); break;
            case R.id.expmedicina: expSwitch(compmedicina, expmedicina, medicina, "expmedicina", "SAG"); break;
            case R.id.comppercezione: compSwitch(b, comppercezione, exppercezione, percezione, "comppercezione", "SAG"); break;
            case R.id.exppercezione: expSwitch(comppercezione, exppercezione, percezione, "exppercezione", "SAG"); break;
            case R.id.compintuizione: compSwitch(b, compintuizione, expintuizione, intuizione, "compintuizione", "SAG"); break;
            case R.id.expintuizione: expSwitch(compintuizione, expintuizione, intuizione, "expintuizione", "SAG"); break;
            //CAR
            case R.id.compintimidire: compSwitch(b, compintimidire, expintimidire, intimidire, "compintimidire", "CAR"); break;
            case R.id.expintimidire: expSwitch(compintimidire, expintimidire, intimidire, "expintimidire", "CAR"); break;
            case R.id.compingannare: compSwitch(b, compingannare, expingannare, ingannare, "compingannare", "CAR"); break;
            case R.id.expingannare: expSwitch(compingannare, expingannare, ingannare, "expingannare", "CAR"); break;
            case R.id.compintrattenere: compSwitch(b, compintrattenere, expintrattenere, intrattenere, "compintrattenere", "CAR"); break;
            case R.id.expintrattenere: expSwitch(compintrattenere, expintrattenere, intrattenere, "expintrattenere", "CAR"); break;
            case R.id.comppersuadere: compSwitch(b, comppersuadere, exppersuadere, persuadere, "comppersuadere", "CAR"); break;
            case R.id.exppersuadere: expSwitch(comppersuadere, exppersuadere, persuadere, "exppersuadere", "CAR"); break;

        }
    }

    public void compSwitch(boolean b, CheckBox comp, CheckBox exp, TextView label, String stateLabel, String carattLabel) {
        int lv = state.getInt("pglv", 1);
        if (b) exp.setVisibility(View.VISIBLE);
        else exp.setVisibility(View.INVISIBLE);
        state.edit().putBoolean(stateLabel, comp.isChecked()).apply();
        int bonus = mod((state.getInt(carattLabel, 10))) + ((comp.isChecked()) ? ((exp.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }

    public void expSwitch(CheckBox comp, CheckBox exp, TextView label, String stateLabel, String carattLabel) {
        int lv = state.getInt("pglv", 1);
        state.edit().putBoolean(stateLabel, exp.isChecked()).apply();
        int bonus = mod((state.getInt(carattLabel, 10)))+ ((comp.isChecked()) ? ((exp.isChecked()) ? prof[lv-1]*2 : prof[lv-1]) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }
}
