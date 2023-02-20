package com.zerobase.fastlms.banner.model;


import lombok.Data;

@Data
public class BannerInput {

    Long id;
    String bannerName;
    String imgPath;
    String path;
    int num;
    boolean isPosted;
    String openMethod;


    String createdDt;

    long totalCt;
    long seq;


    String fileName;
    String urlName;
}
