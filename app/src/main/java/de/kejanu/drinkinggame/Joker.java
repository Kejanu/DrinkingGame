package de.kejanu.drinkinggame;

import android.os.Parcel;
import android.os.Parcelable;

public class Joker implements Parcelable {
    int id;
    boolean checked;
    String text;

    public Joker(int id, String text, boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    public Joker checkedJoker() {
        return new Joker(this.getId(), this.getText(), true);
    }

    @Override
    public String toString() {
        return "Joker{" +
                "id=" + id +
                ", checked=" + checked +
                ", text='" + text + '\'' +
                '}';
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeByte((byte) (this.checked ? 1 : 0));
        dest.writeString(this.text);
    }

    protected Joker(Parcel in) {
        id = in.readInt();
        checked = in.readByte() != 0;
        text = in.readString();
    }

    public static final Creator<Joker> CREATOR = new Creator<Joker>() {
        @Override
        public Joker createFromParcel(Parcel in) {
            return new Joker(in);
        }

        @Override
        public Joker[] newArray(int size) {
            return new Joker[size];
        }
    };
}
