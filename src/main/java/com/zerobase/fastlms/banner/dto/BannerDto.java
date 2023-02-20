package com.zerobase.fastlms.banner.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BannerDto {
    private Long id;


    String name;
    String path;
    String openMethod;
    int num;
    boolean isPost;
    String createdDt;


    String fileName;
    String urlName;



    long totalCt;
    long seq;

}
