package com.fexed.rpgsheet.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class RangedWeapon implements Parcelable, Serializable {
    public String name, range, damage;

    public static final Creator<RangedWeapon> CREATOR = new Creator<RangedWeapon>() {

        @Override
        public RangedWeapon createFromParcel(Parcel source) {
            return new RangedWeapon(source);
        }

        @Override
        public RangedWeapon[] newArray(int size) {
            return new RangedWeapon[size];
        }
    };

    public RangedWeapon(String name, String range, String damage) {
        this.name = name;
        this.range = range;
        this.damage = damage;
    }

    public RangedWeapon(Parcel source) {
        this.name = source.readString();
        this.range = source.readString();
        this.damage = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(range);
        dest.writeString(damage);
    }
}
