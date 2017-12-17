package com.learnacad.learnacad.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sahil Malhotra on 21-07-2017.
 */

public class Test implements  Parcelable {

    String name;
    String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.desc);
    }

    public Test() {
    }

    protected Test(Parcel in) {
        this.name = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel source) {
            return new Test(source);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };
}
