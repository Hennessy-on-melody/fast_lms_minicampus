package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.banner.dto.BannerDto;

import java.util.Comparator;

public class SortedBanner implements Comparator<BannerDto> {
    @Override
    public int compare(BannerDto befor, BannerDto after) {
        return befor.getNum() -after.getNum();
    }
}
