package com.realaicy.mb.android.apptna.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RTATModel  implements Parcelable {
    public abstract int id();
    public abstract String title();

    public static RTATModel create(int id, String title){
        return new AutoValue_RTATModel(id,title);
    }
//    @AutoValue.Builder
//    abstract static class Builder {
//        abstract Builder name(int _id,String );
//        abstract RTATModel build();
//    }
}
