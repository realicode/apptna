package com.realaicy.mb.android.apptna.ui.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
public class SimpleCardFragment extends Fragment implements BGABanner.OnItemClickListener, BGABanner.Adapter {

    private BGABanner mDefaultBanner;
    private BGABanner mCubeBanner;
    private BGABanner mAccordionBanner;
    private BGABanner mFlipBanner;
    private BGABanner mRotateBanner;
    private BGABanner mAlphaBanner;
    private BGABanner mZoomFadeBanner;
    private BGABanner mFadeBanner;
    private BGABanner mZoomCenterBanner;
    private BGABanner mZoomBanner;
    private BGABanner mStackBanner;
    private BGABanner mZoomStackBanner;
    private BGABanner mDepthBanner;
    private Engine mEngine;


    public static SimpleCardFragment getInstance() {
        Log.e("88", "SimpleCardFragment :getInstance");

        SimpleCardFragment sf = new SimpleCardFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEngine = App.getInstance().getEngine();
        //App.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
//        TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
//        //card_title_tv.setText(mTitle);
//        card_title_tv.setText("测试");

//        mDefaultBanner = (BGABanner) v.findViewById(R.id.banner_main_default);
//        mDefaultBanner = (BGABanner) v.findViewById(R.id.banner_main_default);
//        mCubeBanner = (BGABanner) v.findViewById(R.id.banner_main_cube);
//        mAccordionBanner = (BGABanner) v.findViewById(R.id.banner_main_accordion);
//        mFlipBanner = (BGABanner) v.findViewById(R.id.banner_main_flip);
//        mRotateBanner = (BGABanner) v.findViewById(R.id.banner_main_rotate);
        mAlphaBanner = (BGABanner) v.findViewById(R.id.banner_main_alpha);
//        mZoomFadeBanner = (BGABanner) v.findViewById(R.id.banner_main_zoomFade);
//        mFadeBanner = (BGABanner) v.findViewById(R.id.banner_main_fade);
//        mZoomCenterBanner = (BGABanner) v.findViewById(R.id.banner_main_zoomCenter);
//        mZoomBanner = (BGABanner) v.findViewById(R.id.banner_main_zoom);
//        mStackBanner = (BGABanner) v.findViewById(R.id.banner_main_stack);
//        mZoomStackBanner = (BGABanner) v.findViewById(R.id.banner_main_zoomStack);
//        mDepthBanner = (BGABanner) v.findViewById(R.id.banner_main_depth);
//        mDefaultBanner.setOnItemClickListener(this);
        mAlphaBanner.setOnItemClickListener(this);

//        mCubeBanner.setOnItemClickListener(this);

//        mDefaultBanner.setAdapter(new BGABanner.Adapter() {
//            @Override
//            public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
//                ((ImageView)view).setImageResource((int)model);
//            }
//        });
        //mDefaultBanner.setData(Arrays.asList(R.drawable.ic_guide_1, R.drawable.ic_guide_2, R.drawable.ic_guide_3), null);
//        loadData(mDefaultBanner, 6);
//        loadData(mCubeBanner, 2);
//        loadData(mAccordionBanner, 3);
//        loadData(mFlipBanner, 4);
//        loadData(mRotateBanner, 5);
        loadData(mAlphaBanner, 6);
//        loadData(mZoomFadeBanner, 3);
//        loadData(mFadeBanner, 4);
//        loadData(mZoomCenterBanner, 5);
//        loadData(mZoomBanner, 6);
//        loadData(mStackBanner, 3);
//        loadData(mZoomStackBanner, 4);
//        loadData(mDepthBanner, 5);
        return v;
    }


    @Override
    public void onBannerItemClick(BGABanner banner, View view, Object model, int position) {
        Toast.makeText(App.getInstance(), "点击了第" + (position + 1) + "页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fillBannerItem(BGABanner banner, View view, Object model, int position) {
        Glide.with(SimpleCardFragment.this)
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

                banner.setAdapter(SimpleCardFragment.this);
                banner.setData(bannerModel.imgs, bannerModel.tips);
            }

            @Override
            public void onFailure(Call<BannerModel> call, Throwable t) {
                Toast.makeText(App.getInstance(), "网络数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }


}