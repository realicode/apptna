package com.realaicy.mb.android.apptna.engine;

import com.realaicy.mb.android.apptna.model.BannerModel;
import com.realaicy.mb.android.apptna.model.RefreshModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface Engine {

    @GET("{itemCount}item.json")
    Call<BannerModel> fetchItemsWithItemCount(@Path("itemCount") int itemCount);

    @GET
    Call<List<RefreshModel>> loadContentData(@Url String url);
}