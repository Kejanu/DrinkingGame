package de.kejanu.drinkinggame;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Person implements Parcelable {
    private String name;
    private Gender gender;
    private List<Joker> jokerList;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public Person(String name, Gender gender, List<Joker> jokerList) {
        this.name = name;
        this.gender = gender;
        this.jokerList = jokerList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", gender=" + gender +
                ", jokerList=" + jokerList +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Joker> getJokerList() {
        return jokerList;
    }

    public void setJokerList(List<Joker> jokerList) {
        this.jokerList = jokerList;
    }

    // Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.gender, flags);
        dest.writeTypedList(this.jokerList);
    }

    private Person(Parcel in) {
        this.name = in.readString();
        this.gender = in.readParcelable(Gender.class.getClassLoader());
        this.jokerList = new ArrayList<>();
        // Has an parameter "outvalue", so puts the values there
        in.readTypedList(this.jokerList, Joker.CREATOR);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
