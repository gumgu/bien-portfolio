package com.study.admin.login.controller;

import com.study.admin.dto.AdminDTO;
import com.study.admin.login.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 관리자(Admin) 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    /**
     * 관리자(Admin) 서비스
     */
    private final AdminService adminService;

    /**
     * 회원가입 폼을 제공합니다.
     * @param adminDTO
     * @return
     */
    @GetMapping("/add")
    public String addForm(@ModelAttribute("adminDTO") AdminDTO adminDTO) {
        return "admin/addAdminForm";
    }

    /**
     * 새로운 관리자 정보를 등록합니다.
     * @param adminDTO 등록할 관리자 정보
     * @return
     */
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute AdminDTO adminDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addAdminForm";
        }

        adminService.saveAdmin(adminDTO);
        return "redirect:/";
    }



}
