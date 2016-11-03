package com.realaicy.mb.android.apptna;

import android.app.Application;
import android.content.Context;

import com.realaicy.mb.android.apptna.engine.Engine;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/21 下午10:13
 * 描述:
 */
public class App extends Application {
    private static App sInstance;
    private Engine mEngine;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        mEngine = new Retrofit.Builder()
                .baseUrl("http://7xk9dj.com1.z0.glb.clouddn.com/banner/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Engine.class);
        App.context = getApplicationContext();
    }


    public static App getInstance() {
        return sInstance;
    }

    public Engine getEngine() {
        return mEngine;
    }

    public static Context getAppContext() {
        return App.context;
    }
}