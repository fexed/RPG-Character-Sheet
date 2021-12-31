package com.fexed.rpgsheet;

import com.fexed.rpgsheet.data.Character;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fexed.rpgsheet.data.InventoryItem;
import com.fexed.rpgsheet.data.MeleeWeapon;
import com.fexed.rpgsheet.data.RangedWeapon;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.skydoves.balloon.Balloon;
import com.skydoves.balloon.BalloonAnimation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static java.lang.Math.floor;

public class CharacterActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, CheckBox.OnCheckedChangeListener {
    private static final int PICK_IMAGE = 101;
    static SharedPreferences state;
    static int[] prof = {2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 8, 9, 9, 9, 9, 10, 10, 10, 10, 11, 11, 11, 11, 12, 12, 12, 12, 13, 13, 13, 13};

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
    int modfor;
    TextView tsfortxt; CheckBox comptsfor;
    TextView atletica; CheckBox compatletica; CheckBox expatletica;
    int moddex;
    TextView tsdextxt; CheckBox comptsdex;
    TextView acrobazia; CheckBox compacrobazia; CheckBox expacrobazia;
    TextView furtivita; CheckBox compfurtivita; CheckBox expfurtivita;
    TextView rapiditadimano; CheckBox comprapiditadimano; CheckBox exprapiditadimano;
    int modcos;
    TextView tscostxt; CheckBox comptscos;
    int modint;
    TextView tsinttxt; CheckBox comptsint;
    TextView investigare; CheckBox compinvestigare; CheckBox expinvestigare;
    TextView arcano; CheckBox comparcano; CheckBox exparcano;
    TextView storia; CheckBox compstoria; CheckBox expstoria;
    TextView religionefolklore; CheckBox compreligionefolklore; CheckBox expreligionefolklore;
    TextView natura; CheckBox compnatura; CheckBox expnatura;
    int modsag;
    TextView tssagtxt; CheckBox comptssag;
    TextView sopravvivenza; CheckBox compsopravvivenza; CheckBox expsopravvivenza;
    TextView medicina; CheckBox compmedicina; CheckBox expmedicina;
    TextView percezione; CheckBox comppercezione; CheckBox exppercezione;
    TextView intuizione; CheckBox compintuizione; CheckBox expintuizione;
    TextView animali; CheckBox companimali; CheckBox expanimali;
    int modcar;
    TextView tscartxt; CheckBox comptscar;
    TextView intimidire; CheckBox compintimidire; CheckBox expintimidire;
    TextView ingannare; CheckBox compingannare; CheckBox expingannare;
    TextView intrattenere; CheckBox compintrattenere; CheckBox expintrattenere;
    TextView persuadere; CheckBox comppersuadere; CheckBox exppersuadere;
    ImageView portrait;

    public Character character;

    @Override
    protected void onCreate (Bundle saveBundle) {
        super.onCreate(saveBundle);
        setContentView(R.layout.charactersheet);
        state = getApplicationContext().getSharedPreferences(getString(R.string.state), Context.MODE_PRIVATE);
        setTitle(getString(android.R.string.unknownName));

        if (!state.getString("lastchangelog", "-").equals("2.0")) {
            AlertDialog dialog = new AlertDialog.Builder(CharacterActivity.this)
                    .setTitle("Changelog 2.0")
                    .setMessage(R.string.changelog)
                    .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            state.edit().putString("lastchangelog", "2.0").apply();
                            dialog.dismiss();
                        }
                    })
                    .show();
        }

        //Launches
        int n = state.getInt("launchn", 0);
        n++;
        if (n % 5 == 0) {
            Snackbar.make(findViewById(R.id.mainscroll), R.string.ratepls, Snackbar.LENGTH_INDEFINITE).setAction("Play Store", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.fexed.rpgsheet"));
                    startActivity(i);
                }
            }).show();
        }
        state.edit().putInt("launchn", n).apply();

        migrateFromPreferences();
        loadSchedaPG();
        initializeAds();
        Bundle bndl = new Bundle();
        bndl.putInt("launchtimes", state.getInt("launchn", -1));
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
            try {
                Balloon balloon = new Balloon.Builder(getApplicationContext())
                        .setText(getString(R.string.try_diceroller))
                        .setPadding(5)
                        .setDismissWhenTouchOutside(true)
                        .setArrowVisible(false)
                        .setBackgroundColorResource(R.color.colorPrimaryDark)
                        .setBalloonAnimation(BalloonAnimation.ELASTIC)
                        .build();
                balloon.showAlignTop(nametxt, 95, 45);
            } catch (Exception ignored) {}
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
            
            if (character.PF < character.PFMAX) {
                tmps = "" + character.PFMAX;
                character.PF = character.PFMAX;
                PF.setText(tmps);
            }

            character.currslot1 = character.slot1;
            tmps = character.currslot1 + "/" + character.slot1;
            firstlvslots.setText(tmps);

            character.currslot2 = character.slot2;
            tmps = character.currslot2 + "/" + character.slot2;
            secondlvslots.setText(tmps);

            character.currslot3 = character.slot3;
            tmps = character.currslot3 + "/" + character.slot3;
            thirdlvslots.setText(tmps);

            character.currslot4 = character.slot4;
            tmps = character.currslot4 + "/" + character.slot4;
            fourthlvslots.setText(tmps);

            character.currslot5 = character.slot5;
            tmps = character.currslot5 + "/" + character.slot5;
            fifthlvslots.setText(tmps);

            character.currslot6 = character.slot6;
            tmps = character.currslot6 + "/" + character.slot6;
            sixthlvslots.setText(tmps);

            character.currslot7 = character.slot7;
            tmps = character.currslot7 + "/" + character.slot7;
            seventhlvslots.setText(tmps);

            character.currslot8 = character.slot8;
            tmps = character.currslot8 + "/" + character.slot8;
            eighthlvslots.setText(tmps);

            character.currslot9 = character.slot9;
            tmps = character.currslot9 + "/" + character.slot9;
            ninthlvslots.setText(tmps);

            character.currslotplus = character.slotplus;
            tmps = character.currslotplus + "/" + character.slotplus;
            pluslvslots.setText(tmps);

            character.spellmana = character.spellmanamax;
            tmps = character.spellmanamax + "/" + character.spellmanamax;
            spellmana.setText(tmps);

            Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.resttxt), Snackbar.LENGTH_LONG).show();
        } else if (item.getItemId() == R.id.charselect) {
            loadSchedaPG();
        }
        return true;
    }

    public void migrateFromPreferences() {
        if (state.getString("pgname", null) != null) {
            Snackbar.make(findViewById(R.id.mainscroll), R.string.migratemsg, Snackbar.LENGTH_LONG).show();
            character = new Character();
            character.nome = state.getString("pgname", "");
            character.classe = state.getString("pgclass", "");
            character.LV = state.getInt("pglv", 1);
            character.FOR = state.getInt("FOR", 10);
            character.DEX = state.getInt("DEX", 10);
            character.COS = state.getInt("COS", 10);
            character.INT = state.getInt("INT", 10);
            character.SAG = state.getInt("SAG", 10);
            character.CAR = state.getInt("CAR", 10);
            character.CA = state.getInt("CA", 10);
            character.PF = state.getInt("PF", 0);
            character.PFMAX = state.getInt("PFMAX", 0);
            character.EXP = state.getInt("xp", 0);
            character.spellstat = state.getString("SPELLSTAT", "INT");
            character.spellmana = state.getInt("spellmana", 0);
            character.spellmanamax = state.getInt("spellmanamax", 0);
            character.tsfor = state.getBoolean("comptsfor", false);
            character.tsdex = state.getBoolean("comptsdex", false);
            character.tscos = state.getBoolean("comptscos", false);
            character.tsint = state.getBoolean("comptsint", false);
            character.tssag = state.getBoolean("comptssag", false);
            character.tscar = state.getBoolean("comptscar", false);
            character.compatletica = state.getBoolean("compatletica", false);
            character.expatletica = state.getBoolean("expatletica", false);
            character.compacrobazia = state.getBoolean("compacrobazia", false);
            character.expacrobazia = state.getBoolean("expacrobazia", false);
            character.compfurtivita = state.getBoolean("compfurtivita", false);
            character.expfurtivita = state.getBoolean("expfurtivita", false);
            character.comprapiditadimano = state.getBoolean("comprapiditadimano", false);
            character.exprapiditadimano = state.getBoolean("exprapiditadimano", false);
            character.compinvestigare = state.getBoolean("compinvestigare", false);
            character.expinvestigare = state.getBoolean("expinvestigare", false);
            character.comparcano = state.getBoolean("comparcano", false);
            character.exparcano = state.getBoolean("exparcano", false);
            character.compstoria = state.getBoolean("compstoria", false);
            character.expstoria = state.getBoolean("expstoria", false);
            character.compreligione = state.getBoolean("compreligionefolklore", false);
            character.expreligione = state.getBoolean("expreligionefolklore", false);
            character.compnatura = state.getBoolean("compnatura", false);
            character.expnatura = state.getBoolean("expnatura", false);
            character.compsopravvivenza = state.getBoolean("compsopravvivenza", false);
            character.expsopravvivenza = state.getBoolean("expsopravvivenza", false);
            character.compmedicina = state.getBoolean("compmedicina", false);
            character.expmedicina = state.getBoolean("expmedicina", false);
            character.comppercezione = state.getBoolean("comppercezione", false);
            character.exppercezione = state.getBoolean("exppercezione", false);
            character.compintuizione = state.getBoolean("compintuizione", false);
            character.expintuizione = state.getBoolean("expintuizione", false);
            character.companimali = state.getBoolean("companimali", false);
            character.expanimali = state.getBoolean("expanimali", false);
            character.compintimidire = state.getBoolean("compintimidire", false);
            character.expintimidire = state.getBoolean("expintimidire", false);
            character.compingannare = state.getBoolean("compingannare", false);
            character.expingannare = state.getBoolean("expingannare", false);
            character.compintrattenere = state.getBoolean("compintrattenere", false);
            character.expintrattenere = state.getBoolean("expintrattenere", false);
            character.comppersuadere = state.getBoolean("comppersuadere", false);
            character.exppersuadere = state.getBoolean("exppersuadere", false);
            character.linguetxt = state.getString("linguetxt", "");
            character.armitxt = state.getString("armitxt", "");
            character.talentitxt = state.getString("talentitxt", "");
            character.abilitatxt = state.getString("abilitatxt", "");
            character.mp = state.getInt("mp", 0);
            character.mo = state.getInt("mo", 0);
            character.ma = state.getInt("ma", 0);
            character.mr = state.getInt("mr", 0);
            character.inventariotxt = state.getString("inv", "");
            character.backgroundtxt = state.getString("background", "");
            character.cantrips = state.getString("cantripss", "");
            character.lv1 = state.getString("firstlv", "");
            character.currslot1 = state.getInt("currfirstlvslots", 0);
            character.slot1 = state.getInt("firstlvslots", 0);
            character.lv2 = state.getString("secondlv", "");
            character.currslot2 = state.getInt("currsecondlvslots", 0);
            character.slot2 = state.getInt("secondlvslots", 0);
            character.lv3 = state.getString("thirdlv", "");
            character.currslot3 = state.getInt("currthirdlvslots", 0);
            character.slot3 = state.getInt("thirdlvslots", 0);
            character.lv4 = state.getString("fourthlv", "");
            character.currslot4 = state.getInt("currfourthlvslots", 0);
            character.slot4 = state.getInt("fourthlvslots", 0);
            character.lv5 = state.getString("fifthlv", "");
            character.currslot5 = state.getInt("currfifthlvslots", 0);
            character.slot5 = state.getInt("fifthlvslots", 0);
            character.lv6 = state.getString("sixthlv", "");
            character.currslot6 = state.getInt("currsixthlvslots", 0);
            character.slot6 = state.getInt("sixthlvslots", 0);
            character.lv7 = state.getString("seventhlv", "");
            character.currslot7 = state.getInt("currseventhlvslots", 0);
            character.slot7 = state.getInt("seventhlvslots", 0);
            character.lv8 = state.getString("eighthlv", "");
            character.currslot8 = state.getInt("curreighthlvslots", 0);
            character.slot8 = state.getInt("eighthlvslots", 0);
            character.lv9 = state.getString("ninthlv", "");
            character.currslot9 = state.getInt("currninthlvslots", 0);
            character.slot9 = state.getInt("ninthlvslots", 0);
            character.lvplus = state.getString("pluslv", "");
            character.currslotplus = state.getInt("currpluslvslots", 0);
            character.slotplus = state.getInt("pluslvslots", 0);
            character.portrait = state.getString("portrait", null);

            Set<String> set = new HashSet<>(state.getStringSet("meleeatks", new HashSet<String>()));
            for (String str : set) {
                String[] melee = str.split("%");
                character.armimelee.add(new MeleeWeapon(melee[0], melee[1]));
            }
            set = new HashSet<>(state.getStringSet("rangedatks", new HashSet<String>()));
            for (String str : set) {
                String[] ranged = str.split("%");
                character.armiranged.add(new RangedWeapon(ranged[0], ranged[1], ranged[2]));
            }
            set = state.getStringSet("inventory", null);
            for (String str : set) {
                String[] item = str.split("::");
                character.inventario.add(new InventoryItem(item[0], item[1]));
            }
            saveSchedaPG();
            state.edit().clear().apply();
            Bundle bndl = new Bundle();
            bndl.putString("Name", character.nome);
            bndl.putInt("launchtimes", state.getInt("launchn", -1));
            FirebaseAnalytics.getInstance(this).logEvent("CharacterMigration", bndl);
        }
    }

    @SuppressLint("InflateParams")
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
        portrait = findViewById(R.id.pgportrait);

        String tempstr;
        if (character == null) {
            character = new Character();
            PGDialog inputdialog = new PGDialog(this);
            inputdialog.show();
        }
        Bundle bndl = new Bundle();
        bndl.putString("Name", character.nome);
        bndl.putInt("launchtimes", state.getInt("launchn", -1));
        FirebaseAnalytics.getInstance(this).logEvent(FirebaseAnalytics.Event.LOGIN, bndl);
        setTitle(character.nome);
        nametxt.setText(character.nome);
        classtxt.setText(character.classe);
        tempstr = character.LV + "";
        lvtxt.setText(tempstr);
        tempstr = "+" + (prof[character.LV - 1]);
        proftxt.setText(tempstr);

        modfor = mod(character.FOR);
        String suffix = (modfor >= 0) ? "+" : "";
        tempstr = "" + character.FOR;
        FOR.setText(tempstr);
        tempstr = suffix + modfor;
        FORmod.setText(tempstr);
        FORmod.setOnClickListener(this);

        moddex = mod(character.DEX);
        suffix = (moddex >= 0) ? "+" : "";
        tempstr = "" + character.DEX;
        DEX.setText(tempstr);
        tempstr = suffix + moddex;
        DEXmod.setText(tempstr);
        DEXmod.setOnClickListener(this);

        modcos = mod(character.COS);
        suffix = (modcos >= 0) ? "+" : "";
        tempstr = "" + character.COS;
        COS.setText(tempstr);
        tempstr = suffix + modcos;
        COSmod.setText(tempstr);
        COSmod.setOnClickListener(this);

        modint = mod(character.INT);
        suffix = (modint >= 0) ? "+" : "";
        tempstr = "" + character.INT;
        INT.setText(tempstr);
        tempstr = suffix + modint;
        INTmod.setText(tempstr);
        INTmod.setOnClickListener(this);

        modsag = mod(character.SAG);
        suffix = (modsag >= 0) ? "+" : "";
        tempstr = "" + character.SAG;
        SAG.setText(tempstr);
        tempstr = suffix + modsag;
        SAGmod.setText(tempstr);
        SAGmod.setOnClickListener(this);

        modcar = mod(character.CAR);
        suffix = (modcar >= 0) ? "+" : "";
        tempstr = "" + character.CAR;
        CAR.setText(tempstr);
        tempstr = suffix + modcar;
        CARmod.setText(tempstr);
        CARmod.setOnClickListener(this);

        tempstr = "" + character.CA;
        CA.setText(tempstr);

        tempstr = "" + character.PF;
        PF.setText(tempstr);

        tempstr = "" + character.PFMAX;
        PFmax.setText(tempstr);

        spellstat.setText(character.spellstat);
        int bonus;
        if (character.spellstat.equals("SAG"))bonus = prof[character.LV - 1] + mod(character.SAG);
        else if (character.spellstat.equals("CAR"))bonus = prof[character.LV - 1] + mod(character.CAR);
        else bonus = prof[character.LV - 1] + mod(character.INT);
        suffix = (bonus < 0) ? "" : "+";
        tempstr = suffix + bonus;
        spellatk.setText(tempstr);
        spellatk.setOnClickListener(this);
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

        tempstr = "+" + prof[character.LV - 1];
        proftxt.setText(tempstr);

        spellmana.setOnLongClickListener(this);
        tempstr = character.spellmana + "/" + character.spellmanamax;
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
        comptsfor.setChecked(character.tsfor);
        int ts = mod(character.FOR) + ((comptsfor.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsfortxt.setText(tempstr);
        tsfortxt.setOnClickListener(this);
        comptsfor.setOnClickListener(this);

        tsdextxt = findViewById(R.id.TSDEX);
        comptsdex = findViewById(R.id.comptsdex);
        comptsdex.setChecked(character.tsdex);
        ts = mod(character.DEX) + ((comptsdex.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsdextxt.setText(tempstr);
        tsdextxt.setOnClickListener(this);
        comptsdex.setOnClickListener(this);

        tscostxt = findViewById(R.id.TSCOS);
        comptscos = findViewById(R.id.comptscos);
        comptscos.setChecked(character.tscos);
        ts = mod(character.COS) + ((comptscos.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscostxt.setText(tempstr);
        tscostxt.setOnClickListener(this);
        comptscos.setOnClickListener(this);

        tsinttxt = findViewById(R.id.TSINT);
        comptsint = findViewById(R.id.comptsint);
        comptsint.setChecked(character.tsint);
        ts = mod(character.INT) + ((comptsint.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tsinttxt.setText(tempstr);
        tsinttxt.setOnClickListener(this);
        comptsint.setOnClickListener(this);

        tssagtxt = findViewById(R.id.TSSAG);
        comptssag = findViewById(R.id.comptssag);
        comptssag.setChecked(character.tssag);
        ts = mod(character.SAG) + ((comptssag.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tssagtxt.setText(tempstr);
        tssagtxt.setOnClickListener(this);
        comptssag.setOnClickListener(this);

        tscartxt = findViewById(R.id.TSCAR);
        comptscar = findViewById(R.id.comptscar);
        comptscar.setChecked(character.tscar);
        ts = mod(character.CAR) + ((comptscar.isChecked()) ? prof[character.LV - 1] : 0);
        suffix = (ts >= 0) ? "+" : "";
        tempstr = suffix + ts;
        tscartxt.setText(tempstr);
        tscartxt.setOnClickListener(this);
        comptscar.setOnClickListener(this);

        atletica = findViewById(R.id.atletica);
        compatletica = findViewById(R.id.compatletica);
        expatletica = findViewById(R.id.expatletica);
        compatletica.setOnCheckedChangeListener(this);
        expatletica.setOnCheckedChangeListener(this);
        compatletica.setChecked(character.compatletica);
        expatletica.setChecked(character.expatletica);
        bonus = mod((character.FOR))+ ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        atletica.setText(tempstr);
        atletica.setOnClickListener(this);

        acrobazia = findViewById(R.id.acrobazia);
        compacrobazia = findViewById(R.id.compacrobazia);
        expacrobazia = findViewById(R.id.expacrobazia);
        compacrobazia.setOnCheckedChangeListener(this);
        expacrobazia.setOnCheckedChangeListener(this);
        compacrobazia.setChecked(character.compacrobazia);
        expacrobazia.setChecked(character.expacrobazia);
        bonus = mod(character.DEX)+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        acrobazia.setText(tempstr);
        acrobazia.setOnClickListener(this);

        furtivita = findViewById(R.id.furtivita);
        compfurtivita = findViewById(R.id.compfurtivita);
        expfurtivita = findViewById(R.id.expfurtivita);
        compfurtivita.setOnCheckedChangeListener(this);
        expfurtivita.setOnCheckedChangeListener(this);
        compfurtivita.setChecked(character.compfurtivita);
        expfurtivita.setChecked(character.expfurtivita);
        bonus = mod(character.DEX)+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        furtivita.setText(tempstr);
        furtivita.setOnClickListener(this);

        rapiditadimano = findViewById(R.id.rapiditadimano);
        comprapiditadimano = findViewById(R.id.comprapiditadimano);
        exprapiditadimano = findViewById(R.id.exprapiditadimano);
        comprapiditadimano.setOnCheckedChangeListener(this);
        exprapiditadimano.setOnCheckedChangeListener(this);
        comprapiditadimano.setChecked(character.comprapiditadimano);
        exprapiditadimano.setChecked(character.exprapiditadimano);
        bonus = mod(character.DEX)+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        rapiditadimano.setText(tempstr);
        rapiditadimano.setOnClickListener(this);

        investigare = findViewById(R.id.investigare);
        compinvestigare = findViewById(R.id.compinvestigare);
        expinvestigare = findViewById(R.id.expinvestigare);
        compinvestigare.setOnCheckedChangeListener(this);
        expinvestigare.setOnCheckedChangeListener(this);
        compinvestigare.setChecked(character.compinvestigare);
        expinvestigare.setChecked(character.expinvestigare);
        bonus = mod(character.INT) + ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        investigare.setText(tempstr);
        investigare.setOnClickListener(this);

        arcano = findViewById(R.id.arcano);
        comparcano = findViewById(R.id.comparcano);
        exparcano = findViewById(R.id.exparcano);
        comparcano.setOnCheckedChangeListener(this);
        exparcano.setOnCheckedChangeListener(this);
        comparcano.setChecked(character.comparcano);
        exparcano.setChecked(character.exparcano);
        bonus = mod(character.INT) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        arcano.setText(tempstr);
        arcano.setOnClickListener(this);

        storia = findViewById(R.id.storia);
        compstoria = findViewById(R.id.compstoria);
        expstoria = findViewById(R.id.expstoria);
        compstoria.setOnCheckedChangeListener(this);
        expstoria.setOnCheckedChangeListener(this);
        compstoria.setChecked(character.compstoria);
        expstoria.setChecked(character.expstoria);
        bonus = mod(character.INT) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        storia.setText(tempstr);
        storia.setOnClickListener(this);

        religionefolklore = findViewById(R.id.religionefolklore);
        compreligionefolklore = findViewById(R.id.compreligionefolklore);
        expreligionefolklore = findViewById(R.id.expreligionefolklore);
        compreligionefolklore.setOnCheckedChangeListener(this);
        expreligionefolklore.setOnCheckedChangeListener(this);
        compreligionefolklore.setChecked(character.compreligione);
        expreligionefolklore.setChecked(character.expreligione);
        bonus = mod(character.INT) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        religionefolklore.setText(tempstr);
        religionefolklore.setOnClickListener(this);

        natura = findViewById(R.id.natura);
        compnatura = findViewById(R.id.compnatura);
        expnatura = findViewById(R.id.expnatura);
        compnatura.setOnCheckedChangeListener(this);
        expnatura.setOnCheckedChangeListener(this);
        compnatura.setChecked(character.compnatura);
        expnatura.setChecked(character.expnatura);
        bonus = mod(character.INT) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        natura.setText(tempstr);
        natura.setOnClickListener(this);

        sopravvivenza = findViewById(R.id.sopravvivenza);
        compsopravvivenza = findViewById(R.id.compsopravvivenza);
        expsopravvivenza = findViewById(R.id.expsopravvivenza);
        compsopravvivenza.setOnCheckedChangeListener(this);
        expsopravvivenza.setOnCheckedChangeListener(this);
        compsopravvivenza.setChecked(character.compsopravvivenza);
        expsopravvivenza.setChecked(character.expsopravvivenza);
        bonus = mod(character.SAG) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        sopravvivenza.setText(tempstr);
        sopravvivenza.setOnClickListener(this);

        medicina = findViewById(R.id.medicina);
        compmedicina = findViewById(R.id.compmedicina);
        expmedicina = findViewById(R.id.expmedicina);
        compmedicina.setOnCheckedChangeListener(this);
        expmedicina.setOnCheckedChangeListener(this);
        compmedicina.setChecked(character.compmedicina);
        expmedicina.setChecked(character.expmedicina);
        bonus = mod(character.SAG) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        medicina.setText(tempstr);
        medicina.setOnClickListener(this);

        percezione = findViewById(R.id.percezione);
        comppercezione = findViewById(R.id.comppercezione);
        exppercezione = findViewById(R.id.exppercezione);
        comppercezione.setOnCheckedChangeListener(this);
        exppercezione.setOnCheckedChangeListener(this);
        comppercezione.setChecked(character.comppercezione);
        exppercezione.setChecked(character.exppercezione);
        bonus = mod(character.SAG) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        percezione.setText(tempstr);
        percezione.setOnClickListener(this);

        intuizione = findViewById(R.id.intuizione);
        compintuizione = findViewById(R.id.compintuizione);
        expintuizione = findViewById(R.id.expintuizione);
        compintuizione.setOnCheckedChangeListener(this);
        expintuizione.setOnCheckedChangeListener(this);
        compintuizione.setChecked(character.compintuizione);
        expintuizione.setChecked(character.expintuizione);
        bonus = mod(character.SAG) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intuizione.setText(tempstr);
        intuizione.setOnClickListener(this);

        animali = findViewById(R.id.animali);
        companimali = findViewById(R.id.companimali);
        expanimali = findViewById(R.id.expanimali);
        companimali.setOnCheckedChangeListener(this);
        expanimali.setOnCheckedChangeListener(this);
        companimali.setChecked(character.companimali);
        expanimali.setChecked(character.expanimali);
        bonus = mod(character.SAG) + ((companimali.isChecked()) ? ((expanimali.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        animali.setText(tempstr);
        animali.setOnClickListener(this);

        intimidire = findViewById(R.id.intimidire);
        compintimidire = findViewById(R.id.compintimidire);
        expintimidire = findViewById(R.id.expintimidire);
        compintimidire.setOnCheckedChangeListener(this);
        expintimidire.setOnCheckedChangeListener(this);
        compintimidire.setChecked(character.compintimidire);
        expintimidire.setChecked(character.expintimidire);
        bonus = mod(character.CAR) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intimidire.setText(tempstr);
        intimidire.setOnClickListener(this);

        ingannare = findViewById(R.id.ingannare);
        compingannare = findViewById(R.id.compingannare);
        expingannare = findViewById(R.id.expingannare);
        compingannare.setOnCheckedChangeListener(this);
        expingannare.setOnCheckedChangeListener(this);
        compingannare.setChecked(character.compingannare);
        expingannare.setChecked(character.expingannare);
        bonus = mod(character.CAR) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        ingannare.setText(tempstr);
        ingannare.setOnClickListener(this);

        intrattenere = findViewById(R.id.intrattenere);
        compintrattenere = findViewById(R.id.compintrattenere);
        expintrattenere = findViewById(R.id.expintrattenere);
        compintrattenere.setOnCheckedChangeListener(this);
        expintrattenere.setOnCheckedChangeListener(this);
        compintrattenere.setChecked(character.compintrattenere);
        expintrattenere.setChecked(character.expintrattenere);
        bonus = mod(character.CAR) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        intrattenere.setText(tempstr);
        intrattenere.setOnClickListener(this);

        persuadere = findViewById(R.id.persuadere);
        comppersuadere = findViewById(R.id.comppersuadere);
        exppersuadere = findViewById(R.id.exppersuadere);
        comppersuadere.setOnCheckedChangeListener(this);
        exppersuadere.setOnCheckedChangeListener(this);
        comppersuadere.setChecked(character.comppersuadere);
        exppersuadere.setChecked(character.exppersuadere);
        bonus = mod(character.CAR) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        suffix = (bonus >= 0) ? "+" : "";
        tempstr = suffix + bonus;
        persuadere.setText(tempstr);
        persuadere.setOnClickListener(this);

        EditText linguetxt = findViewById(R.id.linguetxt);
        linguetxt.setText(character.linguetxt);
        linguetxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                character.linguetxt  = editable.toString();
                saveSchedaPG();
            }
        });
        linguetxt.clearFocus();

        EditText armitxt = findViewById(R.id.armitxt);
        armitxt.setText(character.armitxt);
        armitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                character.armitxt = editable.toString();
                saveSchedaPG();
            }
        });
        armitxt.clearFocus();

        EditText talentitxt = findViewById(R.id.talentitxt);
        talentitxt.setText(character.talentitxt);
        talentitxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                character.talentitxt = editable.toString();
                saveSchedaPG();
            }
        });
        talentitxt.clearFocus();

        EditText abilitatxt = findViewById(R.id.abilitatxt);
        abilitatxt.setText(character.abilitatxt);
        abilitatxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                character.abilitatxt = editable.toString();
                saveSchedaPG();
            }
        });
        abilitatxt.clearFocus();

        mptxtv = findViewById(R.id.mptxtv);
        motxtv = findViewById(R.id.motxtv);
        matxtv = findViewById(R.id.matxtv);
        mrtxtv = findViewById(R.id.mrtxtv);
        totalmtxtv = findViewById(R.id.totalpgmoneytxtv);

        double money;
        money = Math.ceil(character.mp*10 + character.mo + character.ma*0.1 + character.mr*0.01);
        String txt = String.format(Locale.getDefault(), "%.0f", money);
        String strstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
        totalmtxtv.setText(strstr);

        tempstr = character.mp+ "";
        mptxtv.setText(tempstr);
        mptxtv.setOnLongClickListener(this);
        tempstr = character.mo + "";
        motxtv.setText(tempstr);
        motxtv.setOnLongClickListener(this);
        tempstr = character.ma + "";
        matxtv.setText(tempstr);
        matxtv.setOnLongClickListener(this);
        tempstr = character.mr + "";
        mrtxtv.setText(tempstr);
        mrtxtv.setOnLongClickListener(this);

        EditText invtxt = findViewById(R.id.invtxt);
        invtxt.setText(character.inventariotxt);
        invtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.inventariotxt = editable.toString();
                saveSchedaPG();
            }
        });
        invtxt.clearFocus();

        EditText backgroundtxt = findViewById(R.id.backgroundtxt);
        backgroundtxt.setText(character.backgroundtxt);
        backgroundtxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                character.backgroundtxt = editable.toString();
                saveSchedaPG();
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

        for (RangedWeapon weap : character.armiranged) {
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.rangedrow, null);
            name = newrow.findViewById(R.id.rangedname);
            range = newrow.findViewById(R.id.range);
            bonusrange = newrow.findViewById(R.id.rangedbonus);
            comprange = newrow.findViewById(R.id.rangedbonuscomp);
            damage = newrow.findViewById(R.id.rangeddamage);
            removebtn = newrow.findViewById(R.id.removeranged);

            int bonusb = mod(character.DEX);
            String suffixb = (bonus >= 0) ? "+" : "";

            name.setText(weap.name);
            range.setText(weap.range);
            tempstr = suffixb + bonusb;
            bonusrange.setText(tempstr);
            tempstr = "+" + prof[character.LV - 1];
            comprange.setText(tempstr);
            damage.setText(weap.damage);

            final RangedWeapon finalweap = weap;
            removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    character.armiranged.remove(finalweap);
                    rangedatks.removeView(newrow);
                    saveSchedaPG();
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

        for (MeleeWeapon weap : character.armimelee) {
            final TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleerow, null);
            name = newrow.findViewById(R.id.meleename);
            bonusrange = newrow.findViewById(R.id.meleebonus);
            comprange = newrow.findViewById(R.id.meleebonuscomp);
            damage = newrow.findViewById(R.id.meleedamage);

            removebtn = newrow.findViewById(R.id.removemelee);

            int bonusb = mod(character.FOR);
            String suffixb = (bonus >= 0) ? "+" : "";

            name.setText(weap.name);
            tempstr = suffixb + bonusb;
            bonusrange.setText(tempstr);
            tempstr = "+" + prof[character.LV - 1];
            comprange.setText(tempstr);
            damage.setText(weap.damage);

            final MeleeWeapon finalweap = weap;
            removebtn.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    character.armimelee.remove(finalweap);
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
                RangedDialog inputdialog = new RangedDialog(CharacterActivity.this, character, newrow, rangedatks);
                inputdialog.show();
            }
        });

        addmelee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow newrow = (TableRow) LayoutInflater.from(CharacterActivity.this).inflate(R.layout.meleerow, null);
                MeleeDialog inputdialog = new MeleeDialog(CharacterActivity.this, character, newrow, meleeatks);
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

        cantrip.setText(character.cantrips);
        cantrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.cantrips = editable.toString();
                saveSchedaPG();
            }
        });
        cantrip.clearFocus();

        firstlv.setText(character.lv1);
        firstlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv1 = editable.toString();
                saveSchedaPG();
            }
        });
        firstlv.clearFocus();
        firstlvslots.setText(new StringBuilder().append(character.currslot1).append("/").append(character.slot1));
        firstlvslots.setOnLongClickListener(this);
        castfirstlv.setOnClickListener(this);


        secondlv.setText(character.lv2);
        secondlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv2 = editable.toString();
                saveSchedaPG();
            }
        });
        secondlv.clearFocus();
        secondlvslots.setText(new StringBuilder().append(character.currslot2).append("/").append(character.slot2));
        secondlvslots.setOnLongClickListener(this);
        castsecondlv.setOnClickListener(this);

        thirdlv.setText(character.lv3);
        thirdlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv3 = editable.toString();
                saveSchedaPG();
            }
        });
        thirdlv.clearFocus();
        thirdlvslots.setText(new StringBuilder().append(character.currslot3).append("/").append(character.slot3));
        thirdlvslots.setOnLongClickListener(this);
        castthirdlv.setOnClickListener(this);

        fourthlv.setText(character.lv4);
        fourthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv4 = editable.toString();
                saveSchedaPG();
            }
        });
        fourthlv.clearFocus();
        fourthlvslots.setText(new StringBuilder().append(character.currslot4).append("/").append(character.slot4));
        fourthlvslots.setOnLongClickListener(this);
        castfourthlv.setOnClickListener(this);

        fifthlv.setText(character.lv5);
        fifthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv5 = editable.toString();
                saveSchedaPG();
            }
        });
        fifthlv.clearFocus();
        fifthlvslots.setText(new StringBuilder().append(character.currslot5).append("/").append(character.slot5));
        fifthlvslots.setOnLongClickListener(this);
        castfifthlv.setOnClickListener(this);

        sixthlv.setText(character.lv6);
        sixthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv6 = editable.toString();
                saveSchedaPG();
            }
        });
        sixthlv.clearFocus();
        sixthlvslots.setText(new StringBuilder().append(character.currslot6).append("/").append(character.slot6));
        sixthlvslots.setOnLongClickListener(this);
        castsixthlv.setOnClickListener(this);

        seventhlv.setText(character.lv7);
        seventhlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv7 = editable.toString();
                saveSchedaPG();
            }
        });
        seventhlv.clearFocus();
        seventhlvslots.setText(new StringBuilder().append(character.currslot7).append("/").append(character.slot7));
        seventhlvslots.setOnLongClickListener(this);
        castseventhlv.setOnClickListener(this);

        eighthlv.setText(character.lv8);
        eighthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv8 = editable.toString();
                saveSchedaPG();
            }
        });
        eighthlv.clearFocus();
        eighthlvslots.setText(new StringBuilder().append(character.currslot8).append("/").append(character.slot8));
        eighthlvslots.setOnLongClickListener(this);
        casteightlv.setOnClickListener(this);

        ninthlv.setText(character.lv9);
        ninthlv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lv9 = editable.toString();
                saveSchedaPG();
            }
        });
        ninthlv.clearFocus();
        ninthlvslots.setText(new StringBuilder().append(character.currslot9).append("/").append(character.slot9));
        ninthlvslots.setOnLongClickListener(this);
        castninthlv.setOnClickListener(this);

        pluslv.setText(character.lvplus);
        pluslv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                character.lvplus = editable.toString();
                saveSchedaPG();
            }
        });
        pluslv.clearFocus();
        pluslvslots.setText(new StringBuilder().append(character.currslotplus).append("/").append(character.slotplus));
        pluslvslots.setOnLongClickListener(this);
        castpluslv.setOnClickListener(this);

        inspirationtbn.setChecked(character.inspiration);
        inspirationtbn.setOnCheckedChangeListener(this);

        tempstr = character.EXP + " xp";
        XP.setText(tempstr);
        XP.setOnLongClickListener(this);
        addxpbtn.setOnClickListener(this);

        final InventoryAdapter inventoryAdapter = new InventoryAdapter(character);
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

                        inventoryAdapter.addObj(new InventoryItem(name, getString(R.string.keeppressedtoedit)));
                        inventoryAdapter.notifyDataSetChanged();
                        dialog.cancel();
                        alertd.dismiss();
                        saveSchedaPG();
                    }
                });
                alert.show();
            }
        });

        portrait.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                gallery.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(gallery, PICK_IMAGE);
                return true;
            }
        });
        String path = character.portrait;
        if (path != null) {
            portrait.setImageBitmap(BitmapFactory.decodeFile(path));
        } else portrait.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.propicplaceholder));

        saveSchedaPG();
    }

    private void loadSchedaPG() {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("characters", Context.MODE_PRIVATE);
        final File[] files = directory.listFiles();
        if (files.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
            builder.setTitle(getString(R.string.selectpg));
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CharacterActivity.this, R.layout.pgselectchoice);
            for (int i = 0; i < files.length; i++) {
                arrayAdapter.add((i+1) + ". " + files[i].getName());
            }
            arrayAdapter.add(getString(R.string.newpg));
            arrayAdapter.add(getString(R.string.delpg));
            builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == files.length) {
                        character = null;
                        preparaSchedaPG();
                    } else if (which == files.length+1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CharacterActivity.this);
                        builder.setTitle(getString(R.string.selectpg));
                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(CharacterActivity.this, R.layout.pgselectchoice);
                        for (int i = 0; i < files.length; i++) {
                            arrayAdapter.add((i+1) + ". " + files[i].getName());
                        }
                        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, final int which) {
                                new AlertDialog.Builder(CharacterActivity.this)
                                        .setTitle(R.string.deleteconfirm)
                                        .setMessage(getString(R.string.delconfirmpg, files[which].getName()))
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                                if (files[which].delete()) Snackbar.make(findViewById(R.id.mainscroll), R.string.pgdeleteok, Snackbar.LENGTH_LONG).show();
                                                loadSchedaPG();
                                            }})
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                loadSchedaPG();
                                            }
                                        })
                                        .setCancelable(false).show();
                            }
                        });
                        builder.show();
                    } else {
                        ObjectInputStream os = null;
                        try {
                            os = new ObjectInputStream(new FileInputStream(files[which]));

                            //************ROTTO SE CAMBI QUALCOSA NELLA CLASSE FAI ATTENZIONE PLEASE
                            character = (Character) os.readObject();
                            //************ROTTO SE CAMBI QUALCOSA NELLA CLASSE FAI ATTENZIONE PLEASE

                            os.close();
                            preparaSchedaPG();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (os != null) os.close();
                            } catch (IOException e) {
                                Snackbar.make(findViewById(R.id.mainscroll), R.string.fileopenerror, Snackbar.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
            builder.setCancelable(false);
            builder.show();
        } else {
            character = null;
            preparaSchedaPG();
        }
    }

    public void saveSchedaPG() {
        if (!character.nome.equals("")) {
            ContextWrapper cw = new ContextWrapper(getApplicationContext());
            File directory = cw.getDir("characters", Context.MODE_PRIVATE);
            File pgfile = new File(directory, character.nome);
            ObjectOutputStream os = null;
            try {
                os = new ObjectOutputStream(new FileOutputStream(pgfile));
                os.writeObject(character);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    os.close();
                } catch (IOException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initializeAds() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                AdView mAdView = findViewById(R.id.banner1);
                AdRequest adRequest = new AdRequest.Builder().build();
                mAdView.loadAd(adRequest);
            }
        });
    }

    public static int mod(int punteggio) {
        return (int) floor((((double) punteggio - 10) / 2));
    }

    @Override
    public void onClick(View view) {
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
                        character.spellstat = stat;
                        spellstat.setText(stat);int bonus = 0;
                        if (character.spellstat.equals("SAG"))bonus = prof[character.LV - 1] + mod(character.SAG);
                        else if (character.spellstat.equals("CAR"))bonus = prof[character.LV - 1] + mod(character.CAR);
                        else bonus = prof[character.LV - 1] + mod(character.INT);
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
                character.spellmana = Math.min(character.spellmana+1, character.spellmanamax);
                tempstr = character.spellmana+ "/" + character.spellmanamax;
                spellmana.setText(tempstr);
                break;
            case R.id.removemana:
                character.spellmana = Math.max(character.spellmana-1, 0);
                tempstr = character.spellmana + "/" + character.spellmanamax;
                spellmana.setText(tempstr);
                break;
            case R.id.pfplus:
                character.PF++;
                if (character.PF > character.PFMAX) character.PF = character.PFMAX;
                tempstr = character.PF + "";
                PF.setText(tempstr);
                break;
            case R.id.pfminus:
                character.PF--;
                tempstr = character.PF + "";
                PF.setText(tempstr);
                break;
            case R.id.comptsfor:
                character.tsfor = comptsfor.isChecked();
                int tsf = mod(character.FOR) + ((comptsfor.isChecked()) ? prof[character.LV - 1] : 0);
                suffix = (tsf >= 0) ? "+" : "";
                tempstr = suffix + tsf;
                tsfortxt.setText(tempstr);
                break;
            case R.id.comptsdex:
                character.tsdex = comptsdex.isChecked();
                int tsd = mod(character.DEX) + ((comptsdex.isChecked()) ? prof[character.LV - 1] : 0);
                suffix = (tsd >= 0) ? "+" : "";
                tempstr = suffix + tsd;
                tsdextxt.setText(tempstr);
                break;
            case R.id.comptscos:
                character.tscos = comptscos.isChecked();
                int tsc = mod(character.COS) + ((comptscos.isChecked()) ? prof[character.LV - 1] : 0);
                suffix = (tsc >= 0) ? "+" : "";
                tempstr = suffix + tsc;
                tscostxt.setText(tempstr);
                break;
            case R.id.comptsint:
                character.tsint = comptsint.isChecked();
                int tsi = mod(character.INT) + ((comptsint.isChecked()) ? prof[character.LV - 1] : 0);
                suffix = (tsi >= 0) ? "+" : "";
                tempstr = suffix + tsi;
                tsinttxt.setText(tempstr);
                break;
            case R.id.comptssag:
                character.tssag = comptssag.isChecked();
                int tsa = mod(character.SAG) + ((comptssag.isChecked()) ? prof[character.LV - 1] : 0);
                suffix = (tsa >= 0) ? "+" : "";
                tempstr = suffix + tsa;
                tssagtxt.setText(tempstr);
                break;
            case R.id.comptscar:
                character.tscar = comptscar.isChecked();
                int tsca = mod(character.CAR)+ ((comptscar.isChecked()) ? prof[character.LV - 1] : 0);
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
                        xp += character.EXP;

                        String tempstr = xp + " xp";
                        XP.setText(tempstr);
                        character.EXP = xp;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                    }
                });
                alert.show();
                break;
            case R.id.castfirstlv:
                if (character.currslot1 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_1).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_1).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot1 = (character.currslot1 > 0) ? character.currslot1 - 1 : 0;
                firstlvslots.setText(new StringBuilder().append(character.currslot1).append("/").append(character.slot1));
                saveSchedaPG();
                break;
            case R.id.castsecondlv:
                if (character.currslot2 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_2).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_2).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot2 = (character.currslot2 > 0) ? character.currslot2 - 1 : 0;
                secondlvslots.setText(new StringBuilder().append(character.currslot2).append("/").append(character.slot2));
                saveSchedaPG();
                break;
            case R.id.castthirdlv:
                if (character.currslot3 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_3).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_3).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot3 = (character.currslot3 > 0) ? character.currslot3 - 1 : 0;
                thirdlvslots.setText(new StringBuilder().append(character.currslot3).append("/").append(character.slot3));
                saveSchedaPG();
                break;
            case R.id.castfourthlv:
                if (character.currslot4 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_4).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_4).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot4 = (character.currslot4 > 0) ? character.currslot4 - 1 : 0;
                fourthlvslots.setText(new StringBuilder().append(character.currslot4).append("/").append(character.slot4));
                saveSchedaPG();
                break;
            case R.id.castfifthlv:
                if (character.currslot5 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_5).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_5).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot5 = (character.currslot5 > 0) ? character.currslot5 - 1 : 0;
                fifthlvslots.setText(new StringBuilder().append(character.currslot5).append("/").append(character.slot5));
                saveSchedaPG();
                break;
            case R.id.castsixthlv:
                if (character.currslot6 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_6).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_6).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot6 = (character.currslot6 > 0) ? character.currslot6 - 1 : 0;
                sixthlvslots.setText(new StringBuilder().append(character.currslot6).append("/").append(character.slot6));
                saveSchedaPG();
                break;
            case R.id.castseventhlv:
                if (character.currslot7 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_7).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_7).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot7 = (character.currslot7 > 0) ? character.currslot7 - 1 : 0;
                seventhlvslots.setText(new StringBuilder().append(character.currslot7).append("/").append(character.slot7));
                saveSchedaPG();
                break;
            case R.id.casteightlv:
                if (character.currslot8 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_8).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_8).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot8 = (character.currslot8 > 0) ? character.currslot8 - 1 : 0;
                eighthlvslots.setText(new StringBuilder().append(character.currslot8).append("/").append(character.slot8));
                saveSchedaPG();
                break;
            case R.id.castninthlv:
                if (character.currslot9 > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello_9).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello_9).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslot9 = (character.currslot9 > 0) ? character.currslot9 - 1 : 0;
                ninthlvslots.setText(new StringBuilder().append(character.currslot9).append("/").append(character.slot9));
                saveSchedaPG();
                break;
            case R.id.castpluslv:
                if (character.currslotplus > 0) Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.casted, getString(R.string.livello).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                else Snackbar.make(findViewById(R.id.mainscroll), getString(R.string.noslots, getString(R.string.livello).toLowerCase()), Snackbar.LENGTH_SHORT).show();
                character.currslotplus = (character.currslotplus > 0) ? character.currslotplus - 1 : 0;
                pluslvslots.setText(new StringBuilder().append(character.currslotplus).append("/").append(character.slotplus));
                saveSchedaPG();
                break;
            case R.id.FORmod:
                DiceDialog inputdialog = new DiceDialog(this, state, mod(character.FOR), getString(R.string.str));
                inputdialog.show();
                break;
            case R.id.DEXmod:
                inputdialog = new DiceDialog(this, state, mod(character.DEX), getString(R.string.dex));
                inputdialog.show();
                break;
            case R.id.COSmod:
                inputdialog = new DiceDialog(this, state, mod(character.COS), getString(R.string.cos));
                inputdialog.show();
                break;
            case R.id.INTmod:
                inputdialog = new DiceDialog(this, state, mod(character.INT), getString(R.string.inte));
                inputdialog.show();
                break;
            case R.id.SAGmod:
                inputdialog = new DiceDialog(this, state, mod(character.SAG), getString(R.string.sag));
                inputdialog.show();
                break;
            case R.id.CARmod:
                inputdialog = new DiceDialog(this, state, mod(character.CAR), getString(R.string.car));
                inputdialog.show();
                break;
            case R.id.TSFOR:
                inputdialog = new DiceDialog(this, state, mod(character.FOR) + ((character.tsfor) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.str));
                inputdialog.show();
                break;
            case R.id.TSDEX:
                inputdialog = new DiceDialog(this, state, mod(character.DEX) + ((character.tsdex) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.dex));
                inputdialog.show();
                break;
            case R.id.TSCOS:
                inputdialog = new DiceDialog(this, state, mod(character.COS) + ((character.tscos) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.cos));
                inputdialog.show();
                break;
            case R.id.TSINT:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((character.tsint) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.inte));
                inputdialog.show();
                break;
            case R.id.TSSAG:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((character.tssag) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.sag));
                inputdialog.show();
                break;
            case R.id.TSCAR:
                inputdialog = new DiceDialog(this, state, mod(character.CAR) + ((character.tscar) ? prof[character.LV-1] : 0), getString(R.string.tiro_salvezza) + " " + getString(R.string.car));
                inputdialog.show();
                break;
            case R.id.atletica:
                inputdialog = new DiceDialog(this, state, mod((character.FOR))+ ((compatletica.isChecked()) ? ((expatletica.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.atletica));
                inputdialog.show();
                break;
            case R.id.acrobazia:
                inputdialog = new DiceDialog(this, state, mod(character.DEX)+ ((compacrobazia.isChecked()) ? ((expacrobazia.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.acrobazia));
                inputdialog.show();
                break;
            case R.id.furtivita:
                inputdialog = new DiceDialog(this, state, mod(character.DEX)+ ((compfurtivita.isChecked()) ? ((expfurtivita.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.furtivit));
                inputdialog.show();
                break;
            case R.id.rapiditadimano:
                inputdialog = new DiceDialog(this, state, mod(character.DEX)+ ((comprapiditadimano.isChecked()) ? ((exprapiditadimano.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.rapidit_di_mano));
                inputdialog.show();
                break;
            case R.id.investigare:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((compinvestigare.isChecked()) ? ((expinvestigare.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.investigare));
                inputdialog.show();
                break;
            case R.id.arcano:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((comparcano.isChecked()) ? ((exparcano.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.arcano));
                inputdialog.show();
                break;
            case R.id.storia:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((compstoria.isChecked()) ? ((expstoria.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.storia));
                inputdialog.show();
                break;
            case R.id.religionefolklore:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((compreligionefolklore.isChecked()) ? ((expreligionefolklore.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.religione_e_folklore));
                inputdialog.show();
                break;
            case R.id.natura:
                inputdialog = new DiceDialog(this, state, mod(character.INT) + ((compnatura.isChecked()) ? ((expnatura.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.natura));
                inputdialog.show();
                break;
            case R.id.sopravvivenza:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((compsopravvivenza.isChecked()) ? ((expsopravvivenza.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.sopravvivenza));
                inputdialog.show();
                break;
            case R.id.medicina:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((compmedicina.isChecked()) ? ((expmedicina.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.medicina));
                inputdialog.show();
                break;
            case R.id.percezione:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((comppercezione.isChecked()) ? ((exppercezione.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.percezione));
                inputdialog.show();
                break;
            case R.id.intuizione:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((compintuizione.isChecked()) ? ((expintuizione.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.intuizione));
                inputdialog.show();
                break;
            case R.id.animali:
                inputdialog = new DiceDialog(this, state, mod(character.SAG) + ((companimali.isChecked()) ? ((expanimali.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.animali));
                inputdialog.show();
                break;
            case R.id.intimidire:
                inputdialog = new DiceDialog(this, state, mod(character.CAR) + ((compintimidire.isChecked()) ? ((expintimidire.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.intimidire));
                inputdialog.show();
                break;
            case R.id.ingannare:
                inputdialog = new DiceDialog(this, state, mod(character.CAR) + ((compingannare.isChecked()) ? ((expingannare.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.ingannare));
                inputdialog.show();
                break;
            case R.id.intrattenere:
                inputdialog = new DiceDialog(this, state, mod(character.CAR) + ((compintrattenere.isChecked()) ? ((expintrattenere.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.intrattenere));
                inputdialog.show();
                break;
            case R.id.persuadere:
                inputdialog = new DiceDialog(this, state, mod(character.CAR) + ((comppersuadere.isChecked()) ? ((exppersuadere.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0), getString(R.string.persuadere));
                inputdialog.show();
                break;
            case R.id.spellatktxt:
                int bonus = 0;
                if (character.spellstat.equals("SAG"))bonus = prof[character.LV - 1] + mod(character.SAG);
                else if (character.spellstat.equals("CAR"))bonus = prof[character.LV - 1] + mod(character.CAR);
                else bonus = prof[character.LV - 1] + mod(character.INT);
                inputdialog = new DiceDialog(this, state, bonus, getString(R.string.cast) + " (" + character.spellstat + ")");
                inputdialog.show();
                break;
        }
        saveSchedaPG();
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
                alert.setTitle(getString(R.string.insertlevelof) + " " + character.nome);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int lv = Integer.parseInt(input.getText().toString());
                        if (lv <= 0) lv = 1;
                        if (lv > 45) lv = 45;

                        String tempstr;
                        tempstr = lv + "";
                        lvtxt.setText(tempstr);
                        tempstr = "+" + prof[lv - 1];
                        proftxt.setText(tempstr);
                        character.LV = lv;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.pgnametxt:
                input.setText(character.nome);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertnewnameof) + " " + character.nome);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String name = input.getText().toString();

                        nametxt.setText(name);
                        character.nome = name;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        String maxp = input.getText().toString();
                        int mana = Integer.parseInt(maxp);
                        String tempstr = character.spellmana + "/" + mana;
                        spellmana.setText(tempstr);
                        character.spellmanamax = mana;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.pgclasstxt:
                input.setText(character.classe);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertnewclass) + " " + character.nome);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String classs = input.getText().toString();

                        classtxt.setText(classs);
                        character.classe = classs;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.CA:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = character.CA + "";
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
                        character.CA = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.PF:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = character.PF + "";
                input.setText(tempstr);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertpf));
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tempstr = pnt + "";
                        PF.setText(tempstr);
                        character.PF = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.PF += pnt;

                        String tempstr = character.PF + "";
                        PF.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
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
                        character.PF -= pnt;

                        String tempstr = character.PF + "";
                        PF.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.PFmax:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                tempstr = character.PFMAX + "";
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
                        character.PFMAX = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.FOR = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.DEX = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.COS = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.INT = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.SAG = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
                        character.CAR = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.mptxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgsplatpieces, character.nome));
                tempstr = character.mp + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        mptxtv.setText(tempstr);
                        character.mp = monete;
                        double moneteTot =  Math.ceil(monete*10 + character.mo +character.ma*0.1 + character.mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.motxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgsgoldpieces, character.nome));
                tempstr = character.mo + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        motxtv.setText(tempstr);
                        character.mo = monete;
                        double moneteTot =  Math.ceil(character.mp*10 + monete +character.ma*0.1 + character.mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.matxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgssilvpieces, character.nome));
                tempstr = character.ma + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        matxtv.setText(tempstr);
                        character.ma = monete;
                        double moneteTot =  Math.ceil(character.mp*10 + character.mo + monete*0.1 + character.mr*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.mrtxtv:
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.pgscopppieces, character.nome));
                tempstr = character.mr + "";
                input.setText(tempstr);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int monete = Integer.parseInt(input.getText().toString());
                        String tempstr = monete + "";
                        mrtxtv.setText(tempstr);
                        character.mr = monete;
                        double moneteTot =  Math.ceil(character.mp*10 + character.mo +character.ma*0.1 + monete*0.01);
                        String txt = String.format(Locale.getDefault(), "%.0f", moneteTot);
                        tempstr = getString(R.string.total) + " " + txt + " " + getString(R.string.mo);
                        totalmtxtv.setText(tempstr);
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
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
                alert.setTitle(getString(R.string.insertxpof) + " " + character.nome);
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int xp = Integer.parseInt(input.getText().toString());

                        String tempstr = xp + " xp";
                        XP.setText(tempstr);
                        character.EXP = xp;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotfirsttxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_1) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        firstlvslots.setText(tmp);
                        character.slot1 = pnt;
                        character.currslot1 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotsecondtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_2) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        secondlvslots.setText(tmp);
                        character.slot2 = pnt;
                        character.currslot2 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotthirdtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_3) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        thirdlvslots.setText(tmp);
                        character.slot3 = pnt;
                        character.currslot3 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotfourthtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_4) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        fourthlvslots.setText(tmp);
                        character.slot4 = pnt;
                        character.currslot4 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotfifthtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_5) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        fifthlvslots.setText(tmp);
                        character.slot5 = pnt;
                        character.currslot5 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotsixthtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_6) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        sixthlvslots.setText(tmp);
                        character.slot6 = pnt;
                        character.currslot6 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotseventhtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_7) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        seventhlvslots.setText(tmp);
                        character.slot7 = pnt;
                        character.currslot7 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.sloteigthtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_8) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        eighthlvslots.setText(tmp);
                        character.slot8 = pnt;
                        character.currslot8 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotninthtxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello_9) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        ninthlvslots.setText(tmp);
                        character.slot9 = pnt;
                        character.currslot9 = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
                    }
                });
                alert.show();
                return true;
            case R.id.slotplustxtv:
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                alert.setView(input);
                alert.setNegativeButton(getString(R.string.annulla), null);
                alertd = alert.create();
                alert.setTitle(getString(R.string.insertmaxslots) + " (" + getString(R.string.livello) + ")");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int pnt = Integer.parseInt(input.getText().toString());
                        String tmp = pnt + "/" + pnt;
                        pluslvslots.setText(tmp);
                        character.slotplus = pnt;
                        character.currslotplus = pnt;
                        dialog.cancel();
                        alertd.dismiss();
                        preparaSchedaPG();
                        saveSchedaPG();
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
            case R.id.inspirationbtn: character.inspiration = b; break;
            //FOR
            case R.id.compatletica:
                compSwitch(b, compatletica, expatletica, atletica, character.FOR);
                character.compatletica = b;
                break;
            case R.id.expatletica:
                expSwitch(compatletica, expatletica, atletica, character.FOR);
                character.expatletica = expatletica.isChecked();
                break;
            //DEX
            case R.id.compacrobazia:
                compSwitch(b, compacrobazia, expacrobazia, acrobazia, character.DEX);
                character.compacrobazia = b;
                break;
            case R.id.expacrobazia:
                expSwitch(compacrobazia, expacrobazia, acrobazia, character.DEX);
                character.expacrobazia = expacrobazia.isChecked();
                break;
            case R.id.compfurtivita:
                compSwitch(b, compfurtivita, expfurtivita, furtivita, character.DEX);
                character.compfurtivita = b;
                break;
            case R.id.expfurtivita:
                expSwitch(compfurtivita, expfurtivita, furtivita, character.DEX);
                character.expfurtivita = expfurtivita.isChecked();
                break;
            case R.id.comprapiditadimano:
                compSwitch(b, comprapiditadimano, exprapiditadimano, rapiditadimano, character.DEX);
                character.comprapiditadimano = b;
                break;
            case R.id.exprapiditadimano:
                expSwitch(comprapiditadimano, exprapiditadimano, rapiditadimano, character.DEX);
                character.exprapiditadimano = exprapiditadimano.isChecked();
                break;
            //INT
            case R.id.compinvestigare:
                compSwitch(b, compinvestigare, expinvestigare, investigare, character.INT);
                character.compinvestigare = b;
                break;
            case R.id.expinvestigare:
                expSwitch(compinvestigare, expinvestigare, investigare, character.INT);
                character.expinvestigare = expinvestigare.isChecked();
                break;
            case R.id.comparcano:
                compSwitch(b, comparcano, exparcano, arcano, character.INT);
                character.comparcano = b;
                break;
            case R.id.exparcano:
                expSwitch(comparcano, exparcano, arcano, character.INT);
                character.exparcano = exparcano.isChecked();
                break;
            case R.id.compstoria:
                compSwitch(b, compstoria, expstoria, storia, character.INT);
                character.compstoria = b;
                break;
            case R.id.expstoria:
                expSwitch(compstoria, expstoria, storia, character.INT);
                character.expstoria = expstoria.isChecked();
                break;
            case R.id.compreligionefolklore:
                compSwitch(b, compreligionefolklore, expreligionefolklore, religionefolklore, character.INT);
                character.compreligione = b;
                break;
            case R.id.expreligionefolklore:
                expSwitch(compreligionefolklore, expreligionefolklore, religionefolklore, character.INT);
                character.expreligione = expreligionefolklore.isChecked();
                break;
            case R.id.compnatura:
                compSwitch(b, compnatura, expnatura, natura, character.INT);
                character.compnatura = b;
                break;
            case R.id.expnatura:
                expSwitch(compnatura, expnatura, natura, character.INT);
                character.expnatura = expnatura.isChecked();
                break;
            //SAG
            case R.id.compsopravvivenza:
                compSwitch(b, compsopravvivenza, expsopravvivenza, sopravvivenza, character.SAG);
                character.compsopravvivenza = b;
                break;
            case R.id.expsopravvivenza:
                expSwitch(compsopravvivenza, expsopravvivenza, sopravvivenza, character.SAG);
                character.expsopravvivenza = expsopravvivenza.isChecked();
                break;
            case R.id.compmedicina:
                compSwitch(b, compmedicina, expmedicina, medicina, character.SAG);
                character.compmedicina = b;
                break;
            case R.id.expmedicina:
                expSwitch(compmedicina, expmedicina, medicina, character.SAG);
                character.expmedicina = expmedicina.isChecked();
                break;
            case R.id.comppercezione:
                compSwitch(b, comppercezione, exppercezione, percezione, character.SAG);
                character.comppercezione = b;
                break;
            case R.id.exppercezione:
                expSwitch(comppercezione, exppercezione, percezione, character.SAG);
                character.exppercezione = exppercezione.isChecked();
                break;
            case R.id.compintuizione:
                compSwitch(b, compintuizione, expintuizione, intuizione, character.SAG);
                character.compintuizione = b;
                break;
            case R.id.expintuizione:
                expSwitch(compintuizione, expintuizione, intuizione, character.SAG);
                character.expintuizione = expintuizione.isChecked();
                break;
            case R.id.companimali:
                compSwitch(b, companimali, expanimali, animali, character.SAG);
                character.companimali = b;
                break;
            case R.id.expanimali:
                expSwitch(companimali, expanimali, animali, character.SAG);
                character.expanimali = expanimali.isChecked();
                break;
            //CAR
            case R.id.compintimidire:
                compSwitch(b, compintimidire, expintimidire, intimidire, character.CAR);
                character.compintimidire = b;
                break;
            case R.id.expintimidire:
                expSwitch(compintimidire, expintimidire, intimidire, character.CAR);
                character.expintimidire = expintimidire.isChecked();
                break;
            case R.id.compingannare:
                compSwitch(b, compingannare, expingannare, ingannare, character.CAR);
                character.compingannare = b;
                break;
            case R.id.expingannare:
                expSwitch(compingannare, expingannare, ingannare, character.CAR);
                character.expingannare = expingannare.isChecked();
                break;
            case R.id.compintrattenere:
                compSwitch(b, compintrattenere, expintrattenere, intrattenere, character.CAR);
                character.compintrattenere = b;
                break;
            case R.id.expintrattenere:
                expSwitch(compintrattenere, expintrattenere, intrattenere, character.CAR);
                character.expintrattenere = expintrattenere.isChecked();
                break;
            case R.id.comppersuadere:
                compSwitch(b, comppersuadere, exppersuadere, persuadere, character.CAR);
                character.comppersuadere = b;
                break;
            case R.id.exppersuadere:
                expSwitch(comppersuadere, exppersuadere, persuadere, character.CAR);
                character.exppersuadere = exppersuadere.isChecked();
                break;
        }
        preparaSchedaPG();
        saveSchedaPG();
    }

    public void compSwitch(boolean b, CheckBox comp, CheckBox exp, TextView label, int caratt) {
        if (b) exp.setVisibility(View.VISIBLE);
        else exp.setVisibility(View.INVISIBLE);
        int bonus = mod(caratt) + ((comp.isChecked()) ? ((exp.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }

    public void expSwitch(CheckBox comp, CheckBox exp, TextView label, int caratt) {
        int bonus = mod(caratt) + ((comp.isChecked()) ? ((exp.isChecked()) ? prof[character.LV - 1]*2 : prof[character.LV - 1]) : 0);
        String suffix = (bonus >= 0) ? "+" : "";
        String tempstr = suffix + bonus;
        label.setText(tempstr);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageUri = data.getData();

            InputStream in;
            try {
                in = getContentResolver().openInputStream(imageUri);
                Bitmap selected_img = BitmapFactory.decodeStream(in);

                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("images", Context.MODE_PRIVATE);
                File mypath = new File(directory, selected_img.hashCode() + ".png");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    selected_img.compress(Bitmap.CompressFormat.PNG, 100, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                character.portrait = new ContextWrapper(getApplicationContext()).getDir("images", Context.MODE_PRIVATE).getAbsolutePath() + "/" + selected_img.hashCode() + ".png";
                portrait.setImageBitmap(selected_img);
                saveSchedaPG();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(R.id.mainscroll), R.string.fileopenerror, Snackbar.LENGTH_LONG).show();
            }

            portrait.setImageURI(imageUri);
        }
    }
}
