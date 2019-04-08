package de.kejanu.drinkinggame;

import android.os.Parcel;
import android.os.Parcelable;

public enum Gender implements Parcelable {
    MALE,
    FEMALE;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.ordinal());
    }

    public static final Creator<Gender> CREATOR = new Creator<Gender>() {
        @Override
        public Gender createFromParcel(Parcel in) {
            return Gender.values()[in.readInt()];
        }

        @Override
        public Gender[] newArray(int size) {
            return new Gender[size];
        }
    };
}
