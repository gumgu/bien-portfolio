package com.study.admin.login.repository;

import com.study.admin.dto.AdminDTO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 관리자(Admin) MyBatis Mapper
 */
@Mapper
public interface AdminMapper {

    /**
     * DB에 새로운 관리자 정보를 등록합니다.
     * @param adminDTO
     * @return
     */
    int saveAdmin(AdminDTO adminDTO);

    /**
     * DB에서 관리자 정보를 조회합니다.
     * @param adminId 조회할 관리자 ID
     * @return adminDTO 조회한 관리자 정보
     */
    AdminDTO findAdminByAdminId(String adminId);

}
