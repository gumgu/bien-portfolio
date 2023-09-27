package com.study.admin.login.repository;

import com.study.admin.dto.AdminDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 관리자(Admin) 리포지터리
 */
@Repository
@RequiredArgsConstructor
public class AdminRepository {

    /**
     * 관리자(Admin) Mapper
     */
    private final AdminMapper adminMapper;

    /**
     * DB에 관리자 정보를 저장합니다.
     *
     * @param adminDTO 저장할 회원 정보
     */
    public void saveAdmin(AdminDTO adminDTO) {
        adminMapper.saveAdmin(adminDTO);
    }

    /**
     * DB에서 관리자 정보를 조회합니다.
     * @param adminId 조회할 관리자 ID
     * @return adminDTO 조회한 관리자 정보
     */
    public AdminDTO findAdminByAdminId(String adminId) {
        return adminMapper.findAdminByAdminId(adminId);
    }
}
