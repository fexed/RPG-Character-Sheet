package com.fexed.rpgsheet.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MeleeWeapon implements Parcelable {
    String name, damage;

    public static final Creator<MeleeWeapon> CREATOR = new Creator<MeleeWeapon>() {
        @Override
        public MeleeWeapon createFromParcel(Parcel source) {
            return new MeleeWeapon(source);
        }

        @Override
        public MeleeWeapon[] newArray(int size) {
            return new MeleeWeapon[size];
        }
    };

    public MeleeWeapon(Parcel source) {
        name = source.readString();
        damage = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(damage);
    }
}
