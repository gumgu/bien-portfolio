package com.study.admin.util;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 전달된 파라미터의 이상 여부를 확인하는 Util 클래스 입니다.
 */
public final class ParamUtil {

    private ParamUtil() {};

    /**
     * 전달된 파라미터(String)가 비어있는지 확인합니다.
     * @param param 확인할 파라미터
     * @return boolean 확인 값
     */
    public static boolean isNull(String param) {
        // String이면서, 공백이거나 null인 경우 판별
        return !StringUtils.hasText(param);
    }

    /**
     * 전달된 파라미터(Object)가 비어있는지 확인합니다.
     * @param param 확인할 파라미터
     * @return boolean 확인 값
     */
    public static boolean isNull(Object param) {
        return ObjectUtils.isEmpty(param);
    }

    /**
     * 전달된 파라미터(List<MultipartFile>)가 비어있는지 확인합니다.
     * @param param 확인할 파라미터
     * @return boolean 확인 값
     */
    public static boolean isNull(List<MultipartFile> param) {
        return param == null && param.size() > 0;
    }


    /**
     * 전달된 파라미터가 숫자로만 구성되어있는지 확인합니다.
     * @param param 확인할 파라미터
     * @return boolean 확인 값
     * */
    public static boolean isInteger(String param) {

        // 파라미터가 비어있는지 확인합니다.
        if(param == null || param.length() == 0) {
            return false;
        }

        // 파라미터가 숫자로만 구성되어있는지 확인합니다.
        char[] charArray = param.toCharArray();
        for(char ch : charArray) {
            return !Character.isDigit(ch);
        }

        return true;
    }


}
