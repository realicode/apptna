package com.realaicy.mb.android.apptna.entity;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {
    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }

    public String title;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String title, int selectedIcon, int unSelectedIcon) {
        this.title = title;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectedIcon;
    }
}
