package com.fexed.rpgsheet.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RangedWeapon implements Parcelable {
    String name, range, damage;

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

    public RangedWeapon(Parcel source) {
        name = source.readString();
        range = source.readString();
        damage = source.readString();
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
