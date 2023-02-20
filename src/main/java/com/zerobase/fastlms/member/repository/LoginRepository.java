package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginRepository extends JpaRepository<LoginInfo, Long> {
    List<LoginInfo> findLoginInfoByLoginId(String loginId);
}
