package com.realaicy.mb.android.apptna.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.realaicy.mb.android.apptna.MainActivity;
import com.realaicy.mb.android.apptna.R;

import java.lang.ref.WeakReference;


public class LoaderActivity extends Activity {

    // 延迟2秒
    private static final int LOAD_TO_MAIN_MILLIS = 1000;
    private static final int LOAD_TO_GUIDE_MILLIS = 500;
    private static final int GO_MAINPAGE = 1000;
    private static final int GO_GUIDEPAGE = 1001;

    private static final String SHAREDPREFERENCES_NAME = "first_pref";

    boolean isFirstIn = false;
    /**
     * Handler:跳转到不同界面
     */
    private Handler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<LoaderActivity> mActivity;

        MyHandler(LoaderActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAINPAGE:
                    mActivity.get().goToMainPage();
                    break;
                case GO_GUIDEPAGE:
                    mActivity.get().goToGuidePage();
                    break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        init();
    }

    @SuppressWarnings("ConstantConditions")
    private void init() {
        // 读取SharedPreferences中需要的数据
        // 使用SharedPreferences来记录程序的使用次数
        SharedPreferences preferences = getSharedPreferences(
                SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        // 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);

        // 判断程序与第几次运行，如果是第一次运行则跳转到引导界面，否则跳转到主界面
        //noinspection PointlessBooleanExpression
        if (!isFirstIn) {
            // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
            mHandler.sendEmptyMessageDelayed(GO_MAINPAGE, LOAD_TO_MAIN_MILLIS);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDEPAGE, LOAD_TO_GUIDE_MILLIS);
        }
    }//end of init

    private void goToMainPage() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        LoaderActivity.this.startActivity(intent);
        LoaderActivity.this.overridePendingTransition(R.anim.appear, R.anim.disappear);
        LoaderActivity.this.finish();
    }

    private void goToGuidePage() {
        Intent intent = new Intent(LoaderActivity.this, GuideActivity.class);
        LoaderActivity.this.startActivity(intent);
        LoaderActivity.this.finish();
    }


}
