package com.study.admin.login.service;

import com.study.admin.dto.AdminDTO;
import com.study.admin.login.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 회원(Member) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    /**
     * 관리자(Admin) CRUD 리포지터리
     */
    private final AdminRepository adminRepository;

    /**
     * 새로운 관리자를 등록합니다.
     * @param adminDTO 등록할 회원 정보
     */
    public void saveAdmin(AdminDTO adminDTO) {
        adminRepository.saveAdmin(adminDTO);
    }

    /**
     * 주어진 ID로 관리자 정보를 조회합니다.
     * @param adminId 조회할 아이디
     */
    public AdminDTO findAdminByAdminId(String adminId) {
        return adminRepository.findAdminByAdminId(adminId);
    }


}
