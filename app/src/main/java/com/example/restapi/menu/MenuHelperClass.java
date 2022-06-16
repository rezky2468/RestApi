package com.example.restapi.menu;

import android.os.Parcel;
import android.os.Parcelable;

public class MenuHelperClass implements Parcelable {

    String name, description, price;

    public  MenuHelperClass() {

    }

    public MenuHelperClass(String name, String description, String price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    protected MenuHelperClass(Parcel in) {
        name = in.readString();
        description = in.readString();
        price = in.readString();
    }

    public static final Creator<MenuHelperClass> CREATOR = new Creator<MenuHelperClass>() {
        @Override
        public MenuHelperClass createFromParcel(Parcel in) {
            return new MenuHelperClass(in);
        }

        @Override
        public MenuHelperClass[] newArray(int size) {
            return new MenuHelperClass[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(price);
    }
}
