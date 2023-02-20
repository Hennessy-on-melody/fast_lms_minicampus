package com.zerobase.fastlms.banner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

public class BannerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    private String bannerName;
    private String path;
    private String openMethod;
    private int bannerNum;
    boolean bannerIsPost;

    private LocalDateTime createdDt;
    private LocalDateTime editedDt;

    private String fileName;
    private String urlName;
}
