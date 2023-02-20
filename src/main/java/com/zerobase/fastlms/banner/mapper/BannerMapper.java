package com.zerobase.fastlms.banner.mapper;

import com.zerobase.fastlms.banner.entity.BannerEntity;
import com.zerobase.fastlms.banner.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface BannerMapper {
    long selectListCt(BannerParam parameter);

    List<BannerEntity> selectList(BannerParam parameter);

}
