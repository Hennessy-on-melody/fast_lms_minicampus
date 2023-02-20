package com.zerobase.fastlms.banner.service;

import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.BannerEntity;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;

import java.util.List;

public interface AdminBanner {
    boolean add(BannerInput param);


    boolean set(BannerInput param);


    List<BannerDto> list(BannerParam param, List<BannerEntity> infoList);

    BannerDto findById(long id);

    boolean delete(String listNum);

    List<BannerDto> getPostedBanner();
}
