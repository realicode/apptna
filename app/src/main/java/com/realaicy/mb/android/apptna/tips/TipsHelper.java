package com.realaicy.mb.android.apptna.tips;


public interface TipsHelper {

    void showEmpty();

    void hideEmpty();

    void showLoading(boolean firstPage);

    void hideLoading();

    void showError(boolean firstPage, Throwable error);

    void hideError();

    void showHasMore();

    void hideHasMore();
}