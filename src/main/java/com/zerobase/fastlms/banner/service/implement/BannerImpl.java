package com.zerobase.fastlms.banner.service.implement;


import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.BannerEntity;
import com.zerobase.fastlms.banner.mapper.BannerMapper;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import com.zerobase.fastlms.banner.service.AdminBanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerImpl implements AdminBanner {
    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    @Override
    public boolean add(BannerInput param) {
        bannerRepository.save(BannerEntity.builder()
                .name(param.getBannerName())
                .path(param.getPath())
                .openMethod(param.getOpenMethod())
                .num(param.getNum())
                .isPost(param.isPosted())
                .createdDt(LocalDateTime.now())
                .fileName(param.getFileName())
                .urlName(param.getUrlName())
                .build());
        return true;
    }

    @Override
    public boolean set(BannerInput param) {
        Optional<BannerEntity> optionalInfo = bannerRepository.findById(param.getId());
        if (!optionalInfo.isPresent()) {
            return false;
        }
        BannerEntity bannerEntity = optionalInfo.get();
        bannerEntity.setName(param.getBannerName());
        bannerEntity.setPath(param.getPath());
        bannerEntity.setOpenMethod(param.getOpenMethod());
        bannerEntity.setNum(param.getNum());
        bannerEntity.setPost(param.isPosted());
        bannerEntity.setFileName(param.getFileName());
        bannerEntity.setEditedDt(LocalDateTime.now());

        bannerEntity.setUrlName(param.getUrlName());

        bannerRepository.save(bannerEntity);

        return true;

    }

    @Override
    public List<BannerDto> list(BannerParam param, List<BannerEntity> infoList) {
        List<BannerEntity> bannerEntities = bannerMapper.selectList(param);

        long total = infoList.size();


        List<BannerDto> bannerDtoList = new ArrayList<>();
        for (BannerEntity entity : bannerEntities) {
            bannerDtoList.add(build(entity));
        }


        if (!CollectionUtils.isEmpty(bannerDtoList)) {
            int n = 0;
            for (BannerDto x : bannerDtoList) {
                x.setTotalCt(bannerDtoList.size());
                x.setSeq(total - param.getPageStart() - n);
            }
        }

        return bannerDtoList;
    }

    private BannerDto build(BannerEntity entity) {
        return BannerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .path(entity.getPath())
                .num(entity.getNum())
                .isPost(entity.isPost())
                .urlName(entity.getUrlName())
                .createdDt(entity.getCreatedDt().format(DateTimeFormatter.ofPattern("yyyy--MM--dd")))
                .build();
    }

    @Override
    public BannerDto findById(long id) {
        Optional<BannerEntity> optionalInfo = bannerRepository.findById(id);


        if (optionalInfo.isPresent()) {
            BannerEntity entity = optionalInfo.get();
            return build(entity);
        }

        return null;
    }

    @Override
    public boolean delete(String list) {
        if (list != null && list.length() > 0) {
            String[] ids = list.split(",");
            for (String x : ids) {
                long id = 0;

                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {

                }

                if (id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public List<BannerDto> getPostedBanner() {
        List<BannerDto> postedBannerDtoList = new ArrayList<>();

        for (BannerEntity entity : bannerRepository.findAll()) {
            if (entity.isPost()) {
                postedBannerDtoList.add(build(entity));
            }
        }

        return postedBannerDtoList;
    }
}
