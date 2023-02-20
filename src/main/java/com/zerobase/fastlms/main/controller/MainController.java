package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.service.AdminBanner;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final AdminBanner adminBanner;
    
    @RequestMapping("/")
    public String index(Model model) {
        List<BannerDto> postedBanner = adminBanner.getPostedBanner();

        Collections.sort(postedBanner, new SortedBanner());

        model.addAttribute("banners", postedBanner);
        


        
        return "index";
    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
