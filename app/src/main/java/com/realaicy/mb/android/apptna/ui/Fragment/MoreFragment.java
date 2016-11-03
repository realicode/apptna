package com.realaicy.mb.android.apptna.ui.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.realaicy.mb.android.apptna.R;


public class MoreFragment extends Fragment{

    public static MoreFragment getInstance() {
        MoreFragment sf = new MoreFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("777","MoreFragment onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("777","MoreFragment onCreateView");

        View v = inflater.inflate(R.layout.fr_more, null);
        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
        //card_title_tv.setText(mTitle);
        card_title_tv.setText("更多");

        //mDefaultBanner = (BGABanner)v. findViewById(R.id.banner_main_default);
        //mDefaultBanner.setOnItemClickListener(this);
        //loadData(mDefaultBanner, 1);
        return v;
    }


}