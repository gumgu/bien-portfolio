package com.study.admin;

import com.study.admin.argumentresolver.Login;
import com.study.admin.dto.AdminDTO;
import com.study.admin.login.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    public AdminService adminService;

    @GetMapping("/")
    public String homeLogin(@Login AdminDTO loginAdmin, Model model) {

        // 세션에 회원 데이터가 없으면 home으로 이동
        if (loginAdmin == null) {
            return "home";
        }

        // 세션이 유지되면 loginHome으로 이동
        model.addAttribute("admin", loginAdmin);
        return "loginHome";
    }




}
