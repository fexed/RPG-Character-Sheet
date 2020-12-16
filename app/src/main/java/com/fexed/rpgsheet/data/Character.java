package com.fexed.rpgsheet.data;

import java.util.List;

public class Character {
    public int FOR, DEX, COS, INT, SAG, CAR, PF, PFMAX, CA, EXP;
    public boolean inspiration;

    public int slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, slot10;
    public String cantrips, lv1, lv2, lv3, lv4, lv5, lv6, lv7, lv8, lv9, lvplus;

    public int mp, mo, ma, mr;

    public boolean tsfor, tsdex, tscos, tsint, tssag, tscar;
    public boolean compatletica, expatletica,
            compacrobazia, expacrobazia, compfurtivita, expfurtivita, comprapiditadimano,
            exprapiditadimano, compinvestigare, expinvestigare, comparcano, exparcano, compstoria,
            expstoria, compreligione, expreligione, compnatura, expnatura, compsopravvivenza,
            exsopravvivenza, compmedicina, xpmedicina, comppercezione, exppercezione,
            compintuzione, expintuizione, compintimidire, expintimidire, compingannare,
            expingannare, compintrattenere, expintrattenere, comppersuadere, exppersuadere;

    public String portrait;

    public String linguetxt, armitxt, talentitxt, abilitatxt, inventariotxt, backgroundtxt;
    public List<MeleeWeapon> armimelee;
    public List<RangedWeapon> armiranged;
    public List<InventoryItem> inventario;
}
