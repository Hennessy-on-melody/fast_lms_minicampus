package com.zerobase.fastlms.banner.controller;


import com.zerobase.fastlms.banner.dto.BannerDto;
import com.zerobase.fastlms.banner.entity.BannerEntity;
import com.zerobase.fastlms.banner.model.BannerInput;
import com.zerobase.fastlms.banner.model.BannerParam;
import com.zerobase.fastlms.banner.repository.BannerRepository;
import com.zerobase.fastlms.banner.service.AdminBanner;
import com.zerobase.fastlms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class BannerController extends BaseController{
    private final AdminBanner adminBanner;
    private final BannerRepository bannerRepository;

    @GetMapping(value = {"/admin/banner/add.do", "admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request, BannerInput param){

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto details = new BannerDto();

        if (editMode == true){
            long id = param.getId();
            BannerDto existedDto = adminBanner.findById(id);
            if (existedDto == null){
                model.addAttribute("msg", "강좌가 존재하지 않습니다.");
                return "common/error";
            }
            details = existedDto;
        }

        model.addAttribute("editMode", editMode);
        model.addAttribute("details", details);

        return "admin/banner/add";
    }



    @PostMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model,
                            HttpServletRequest request,
                            BannerInput param,
                            MultipartFile file){


        String saveFile = "";
        String urlFile = "";

        if (file != null){
            String leadFile = file.getOriginalFilename();

            String basePath = "/Users/hennessy_on_melody/Desktop/Project/fastlms3/files";
            String baseUrl = "/files";

            String[] fileArr = saveNewFile(basePath, baseUrl, leadFile);

            saveFile = fileArr[0];
            urlFile = fileArr[1];

            try{
                File newFile = new File(saveFile);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        param.setFileName(saveFile);
        param.setUrlName(urlFile);
        param.setBannerName(request.getParameter("name"));
        param.setPosted(request.getParameterValues("isPost") == null ? false : true);
        param.setOpenMethod(request.getParameter("openMethod"));






        boolean editStatus = request.getRequestURI().contains("/edit.do");
        if (editStatus){
            long id = param.getId();
            BannerDto existingDto = adminBanner.findById(id);
            if (existingDto == null){
                model.addAttribute("msg", "강좌 정보가 없습니다.");

                return "common/error";
            }

            boolean result = adminBanner.set(param);
        }

        else {
            boolean result = adminBanner.add(param);
        }

        return "redirect:/admin/banner/list.do";
    }

    private String[] saveNewFile(String basePath, String baseUrl, String leadFile) {

        LocalDate now = LocalDate.now();


        String[] dirs = {
                String.format("%s/%d/", basePath, now.getYear()),
                String.format("%s/%d/%02d/", basePath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", basePath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String dirUrl = String.format("%s/%d/%02d/%02d/", baseUrl, now.getYear(), now.getMonthValue(), now.getDayOfMonth());





        for (String dir : dirs){
            File file = new File(dir);
            if (!file.isDirectory()){
                file.mkdir();
            }
        }

        String extenseFile = "";

        if (leadFile != null){
            int dotLocation = leadFile.lastIndexOf(".");
            if (dotLocation > -1){
                extenseFile = leadFile.substring(dotLocation + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFile = String.format("%s%s", dirs[2], uuid);
        String newUrl = String.format("%s%s", dirUrl, uuid);

        if (extenseFile.length() > 0){
            newFile += "." + extenseFile;
            newUrl += "." + extenseFile;
        }

        return new String[]{newFile, newUrl};
    }

    @GetMapping("/admin/banner/list.do")
        public String list(Model model, BannerParam param){
            List<BannerEntity> bannerEntityList = bannerRepository.findAll();

            long total = bannerEntityList.size();


            param.init();
            List<BannerDto> bannerDtoList = adminBanner.list(param, bannerEntityList);

            String query = param.getQueryString();
            String pagerHtml = getHtml(total, param.getPageSize(), param.getPageIndex(), query);


            model.addAttribute("list", bannerDtoList);
            model.addAttribute("total", total);
            model.addAttribute("pager", pagerHtml);

            return "admin/banner/list";
    }



    @PostMapping("/admin/banner/delete.do")
    public String delete(Model model, HttpServletRequest request, CourseInput param){
        boolean result = adminBanner.delete(param.getIdList());
        return "redirect:/admin/banner/list.do";
    }
}
