package com.realaicy.mb.android.apptna.ui.fragment.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.realaicy.mb.android.apptna.App;
import com.realaicy.mb.android.apptna.R;
import com.realaicy.mb.android.apptna.engine.Engine;
import com.realaicy.mb.android.apptna.model.BannerModel;

import cn.bingoogolapple.bgabanner.BGABanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment implements BGABanner.OnItemClickListener, BGABanner.Adapter {

    private Engine mEngine;

    public static HomeFragment getInstance() {
        Log.e("88", "HomeFragment :getInstance");
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine = App.getInstance().getEngine();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        BGABanner mAlphaBanner = (BGABanner) v.findViewById(R.id.banner_main_alpha);
        mAlphaBanner.setOnItemClickListener(this);
        loadData(mAlphaBanner, 6);
        return v;
    }

    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Glide.with(HomeFragment.this)
                .load(model)
                .placeholder(R.drawable.holder)
                .error(R.drawable.holder)
                .into((ImageView) view);
    }

    private void loadData(final BGABanner banner, int count) {
        mEngine.fetchItemsWithItemCount(count).enqueue(new Callback<BannerModel>() {
            @Override
            public void onResponse(Call<BannerModel> call, Response<BannerModel> response) {
                BannerModel bannerModel = response.body();
                banner.setAdapter(HomeFragment.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

}