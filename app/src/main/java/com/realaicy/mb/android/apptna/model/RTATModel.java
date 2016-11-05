package com.realaicy.mb.android.apptna.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class RTATModel {
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
