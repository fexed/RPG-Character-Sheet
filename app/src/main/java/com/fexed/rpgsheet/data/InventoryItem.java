package com.fexed.rpgsheet.data;

import android.os.Parcel;
import android.os.Parcelable;

public class InventoryItem implements Parcelable {
    String name, desc;

    public static final Creator<InventoryItem> CREATOR = new Creator<InventoryItem>() {

        @Override
        public InventoryItem createFromParcel(Parcel source) {
            return new InventoryItem(source);
        }

        @Override
        public InventoryItem[] newArray(int size) {
            return new InventoryItem[size];
        }
    };

    public InventoryItem(Parcel source) {
        name = source.readString();
        desc = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(desc);
    }
}
