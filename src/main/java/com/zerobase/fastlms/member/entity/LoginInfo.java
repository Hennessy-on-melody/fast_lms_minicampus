package com.zerobase.fastlms.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class LoginInfo {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String loginId;
    private LocalDateTime loginDt;
    private String ipAdd;
    private String agentInfo;
}
