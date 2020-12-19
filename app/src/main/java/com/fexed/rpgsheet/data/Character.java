package com.fexed.rpgsheet.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Character implements Parcelable, Serializable {
    public String nome, classe;
    public int FOR, DEX, COS, INT, SAG, CAR, PF, PFMAX, CA, EXP, LV;
    public boolean inspiration; //1 true 0 false

    public String spellstat;
    public int slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slotplus;
    public int currslot1, currslot2, currslot3, currslot4, currslot5, currslot6, currslot7, currslot8, currslot9, currslotplus;
    public String cantrips, lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lvplus;
    public int spellmana, spellmanamax;

    public int mp, mo, ma, mr;

    public boolean tsfor, tsdex, tscos, tsint, tssag, tscar;
    public boolean compatletica, expatletica,
            compacrobazia, expacrobazia, compfurtivita, expfurtivita, comprapiditadimano,
            exprapiditadimano, compinvestigare, expinvestigare, comparcano, exparcano, compstoria,
            expstoria, compreligione, expreligione, compnatura, expnatura, compsopravvivenza,
            expsopravvivenza, compmedicina, expmedicina, comppercezione, exppercezione,
            compintuizione, expintuizione, compintimidire, expintimidire, compingannare,
            expingannare, compintrattenere, expintrattenere, comppersuadere, exppersuadere;

    public String portrait;

    public String linguetxt, armitxt, talentitxt, abilitatxt, inventariotxt, backgroundtxt;
    public List<MeleeWeapon> armimelee;
    public List<RangedWeapon> armiranged;
    public List<InventoryItem> inventario;

    public static final Creator<Character> CREATOR = new Creator<Character>() {

        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    public Character() {
        nome = ""; classe = "";
        FOR = 10; DEX = 10; COS = 10; INT = 10; SAG = 10; CAR = 10; PF = 0; PFMAX = 0; CA = 10;
        EXP = 0; LV = 1; inspiration = false;
        slot1 = 0; slot2 = 0; slot3 = 0; slot4 = 0; slot5 = 0; slot6 = 0; slot7 = 0; slot8 = 0;
        slot9 = 0; slotplus = 0;
        currslot1 = 0; currslot2 = 0; currslot3 = 0; currslot4 = 0; currslot5 = 0; currslot6 = 0; currslot7 = 0; currslot8 = 0;
        currslot9 = 0; currslotplus = 0;
        cantrips = ""; lv1 = ""; lv2 = ""; lv3 = ""; lv4 = ""; lv5 = ""; lv6 = ""; lv7 = "";
        lv8 = ""; lv9 = ""; lvplus = "";
        mp = 0; mo = 0; ma = 0; mr = 0;
        tsfor = false; tsdex = false; tscos = false; tsint = false; tssag = false; tscar = false;
        portrait = null; linguetxt = ""; armitxt = ""; talentitxt = ""; abilitatxt = "";
        inventariotxt = ""; backgroundtxt = "";
        armimelee = new ArrayList<>(); armiranged = new ArrayList<>(); inventario = new ArrayList<>();
        compatletica= false; expatletica= false; compacrobazia= false; expacrobazia= false;
        compfurtivita= false; expfurtivita= false; comprapiditadimano= false;
        exprapiditadimano= false; compinvestigare= false; expinvestigare= false;
        comparcano= false; exparcano= false; compstoria= false; expstoria= false;
        compreligione= false; expreligione= false; compnatura= false; expnatura= false;
        compsopravvivenza= false; expsopravvivenza= false; compmedicina= false; expmedicina= false;
        comppercezione= false; exppercezione= false;compintuizione= false; expintuizione= false;
        compintimidire= false; expintimidire= false; compingannare= false; expingannare= false;
        compintrattenere= false; expintrattenere= false; comppersuadere= false; exppersuadere = false;
        spellstat = "INT";
        spellmana = 0; spellmanamax = 0;
    }

    public Character(Parcel source) {
        nome = source.readString();
        classe = source.readString();
        FOR = source.readInt();
        DEX = source.readInt();
        COS = source.readInt();
        INT = source.readInt();
        SAG = source.readInt();
        CAR = source.readInt();
        PF = source.readInt();
        PFMAX = source.readInt();
        CA = source.readInt();
        EXP = source.readInt();
        LV = source.readInt();
        inspiration = (source.readInt() == 1);
        spellstat = source.readString();
        slot1 = source.readInt();
        slot2 = source.readInt();
        slot3 = source.readInt();
        slot4 = source.readInt();
        slot5 = source.readInt();
        slot6 = source.readInt();
        slot7 = source.readInt();
        slot8 = source.readInt();
        slot9 = source.readInt();
        slotplus = source.readInt();
        currslot1 = source.readInt();
        currslot2 = source.readInt();
        currslot3 = source.readInt();
        currslot4 = source.readInt();
        currslot5 = source.readInt();
        currslot6 = source.readInt();
        currslot7 = source.readInt();
        currslot8 = source.readInt();
        currslot9 = source.readInt();
        currslotplus = source.readInt();
        cantrips = source.readString();
        lv1 = source.readString();
        lv2 = source.readString();
        lv3 = source.readString();
        lv4 = source.readString();
        lv5 = source.readString();
        lv6 = source.readString();
        lv7 = source.readString();
        lv8 = source.readString();
        lv9 = source.readString();
        lvplus = source.readString();
        spellmana = source.readInt();
        spellmanamax = source.readInt();
        mp = source.readInt();
        mo = source.readInt();
        ma = source.readInt();
        mr = source.readInt();
        tsfor = (source.readInt() == 1);
        tsdex = (source.readInt() == 1);
        tscos = (source.readInt() == 1);
        tsint = (source.readInt() == 1);
        tssag = (source.readInt() == 1);
        tscar = (source.readInt() == 1);
        compatletica = (source.readInt() == 1);
        expatletica = (source.readInt() == 1);
        compacrobazia = (source.readInt() == 1);
        expacrobazia = (source.readInt() == 1);
        compfurtivita = (source.readInt() == 1);
        expfurtivita = (source.readInt() == 1);
        comprapiditadimano = (source.readInt() == 1);
        exprapiditadimano = (source.readInt() == 1);
        compinvestigare = (source.readInt() == 1);
        expinvestigare = (source.readInt() == 1);
        comparcano = (source.readInt() == 1);
        exparcano = (source.readInt() == 1);
        compstoria = (source.readInt() == 1);
        expstoria = (source.readInt() == 1);
        compreligione = (source.readInt() == 1);
        expreligione = (source.readInt() == 1);
        compnatura = (source.readInt() == 1);
        expnatura = (source.readInt() == 1);
        compsopravvivenza = (source.readInt() == 1);
        expsopravvivenza = (source.readInt() == 1);
        compmedicina = (source.readInt() == 1);
        expmedicina = (source.readInt() == 1);
        comppercezione = (source.readInt() == 1);
        exppercezione = (source.readInt() == 1);
        compintuizione = (source.readInt() == 1);
        expintuizione = (source.readInt() == 1);
        compintimidire = (source.readInt() == 1);
        expintimidire = (source.readInt() == 1);
        compingannare = (source.readInt() == 1);
        expingannare = (source.readInt() == 1);
        compintrattenere = (source.readInt() == 1);
        expintrattenere = (source.readInt() == 1);
        comppersuadere = (source.readInt() == 1);
        exppersuadere = (source.readInt() == 1);

        portrait = source.readString();
        linguetxt = source.readString();
        armitxt = source.readString();
        talentitxt = source.readString();
        abilitatxt = source.readString();
        inventariotxt = source.readString();
        backgroundtxt = source.readString();

        armimelee = new ArrayList<>();
        source.readTypedList(armimelee, MeleeWeapon.CREATOR);
        armiranged = new ArrayList<>();
        source.readTypedList(armiranged, RangedWeapon.CREATOR);
        inventario = new ArrayList<>();
        source.readTypedList(inventario, InventoryItem.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome); dest.writeString(classe);
        dest.writeInt(FOR); dest.writeInt(DEX); dest.writeInt(COS); dest.writeInt(INT);
        dest.writeInt(SAG); dest.writeInt(CAR); dest.writeInt(PF); dest.writeInt(PFMAX);
        dest.writeInt(CA); dest.writeInt(EXP); dest.writeInt(LV);
        dest.writeInt((inspiration) ? 1 : 0);
        dest.writeString(spellstat);
        dest.writeInt(slot1); dest.writeInt(slot2); dest.writeInt(slot3); dest.writeInt(slot4);
        dest.writeInt(slot5); dest.writeInt(slot6); dest.writeInt(slot7); dest.writeInt(slot8);
        dest.writeInt(slot9); dest.writeInt(slotplus);
        dest.writeInt(currslot1); dest.writeInt(currslot2); dest.writeInt(currslot3); dest.writeInt(currslot4);
        dest.writeInt(currslot5); dest.writeInt(currslot6); dest.writeInt(currslot7); dest.writeInt(currslot8);
        dest.writeInt(currslot9); dest.writeInt(currslotplus);
        dest.writeString(cantrips); dest.writeString(lv1); dest.writeString(lv2);
        dest.writeString(lv3); dest.writeString(lv4); dest.writeString(lv5); dest.writeString(lv6);
        dest.writeString(lv7); dest.writeString(lv8); dest.writeString(lv9); dest.writeString(lvplus);
        dest.writeInt(spellmana); dest.writeInt(spellmanamax);
        dest.writeInt(mp); dest.writeInt(mo); dest.writeInt(ma); dest.writeInt(mr);
        dest.writeInt((tsfor) ? 1 : 0); dest.writeInt((tsdex) ? 1 : 0); dest.writeInt((tscos) ? 1 : 0);
        dest.writeInt((tsint) ? 1 : 0); dest.writeInt((tssag) ? 1 : 0); dest.writeInt((tscar) ? 1 : 0);
        dest.writeInt((compatletica) ? 1 : 0);
        dest.writeInt((expatletica) ? 1 : 0);
        dest.writeInt((compacrobazia) ? 1 : 0);
        dest.writeInt((expacrobazia) ? 1 : 0);
        dest.writeInt((compfurtivita) ? 1 : 0);
        dest.writeInt((expfurtivita) ? 1 : 0);
        dest.writeInt((comprapiditadimano) ? 1 : 0);
        dest.writeInt((exprapiditadimano) ? 1 : 0);
        dest.writeInt((compinvestigare) ? 1 : 0);
        dest.writeInt((expinvestigare) ? 1 : 0);
        dest.writeInt((comparcano) ? 1 : 0);
        dest.writeInt((exparcano) ? 1 : 0);
        dest.writeInt((compstoria) ? 1 : 0);
        dest.writeInt((expstoria) ? 1 : 0);
        dest.writeInt((compreligione) ? 1 : 0);
        dest.writeInt((expreligione) ? 1 : 0);
        dest.writeInt((compnatura) ? 1 : 0);
        dest.writeInt((expnatura) ? 1 : 0);
        dest.writeInt((compsopravvivenza) ? 1 : 0);
        dest.writeInt((expsopravvivenza) ? 1 : 0);
        dest.writeInt((compmedicina) ? 1 : 0);
        dest.writeInt((expmedicina) ? 1 : 0);
        dest.writeInt((comppercezione) ? 1 : 0);
        dest.writeInt((exppercezione) ? 1 : 0);
        dest.writeInt((compintuizione) ? 1 : 0);
        dest.writeInt((expintuizione) ? 1 : 0);
        dest.writeInt((compintimidire) ? 1 : 0);
        dest.writeInt((expintimidire) ? 1 : 0);
        dest.writeInt((compingannare) ? 1 : 0);
        dest.writeInt((expingannare) ? 1 : 0);
        dest.writeInt((compintrattenere) ? 1 : 0);
        dest.writeInt((expintrattenere) ? 1 : 0);
        dest.writeInt((comppersuadere) ? 1 : 0);
        dest.writeInt((exppersuadere) ? 1 : 0);
        dest.writeString(portrait); dest.writeString(linguetxt); dest.writeString(armitxt);
        dest.writeString(talentitxt); dest.writeString(abilitatxt); dest.writeString(inventariotxt);
        dest.writeString(backgroundtxt);
        dest.writeTypedList(armimelee);
        dest.writeTypedList(armiranged);
        dest.writeTypedList(inventario);
    }
}
