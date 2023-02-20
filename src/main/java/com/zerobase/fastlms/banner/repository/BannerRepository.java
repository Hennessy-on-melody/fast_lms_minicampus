package com.zerobase.fastlms.banner.repository;

import com.zerobase.fastlms.banner.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
}
