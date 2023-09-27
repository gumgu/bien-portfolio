package com.study.admin.util;

import com.study.admin.dto.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 파일 업로드를 위한 Util 입니다.
 */
@Slf4j
public final class FileUtil {

    private FileUtil() {};

    // 경로
    @Value("${file.dir}")
    private static String fileDir;

    /**
     * 경로 생성
     *
     * @param fileName
     * @return 경로 + 파일명
     */
    public static String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    /**
     * 파일을 업로드 합니다.
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static FileDTO uploadFile(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()) {
            return null;
        }

        String originName = multipartFile.getOriginalFilename();
        String fileName = createFileName(originName);
        multipartFile.transferTo(new java.io.File(getFullPath(fileName)));

        FileDTO file = new FileDTO();
        file.setOriginName(originName);
        file.setFileName(fileName);
        return file;

    }

    /**
     * 중복 방지 파일명 생성
     *
     * @param originName 원본 파일명
     * @return 업로드용 파일명
     */
    public static String createFileName(String originName) {
        UUID uuid = UUID.randomUUID();
        return uuid.toString()+"_"+originName;
    }

    /**
     * 파일 다운로드를 수행합니다.
     *
     * @param fileName 서버에 저장된 파일명
     * @param originName 원본 파일명
     * @return 파일 다운로드
     * @throws MalformedURLException
     */
    public static ResponseEntity<Resource> getDownloadResponseEntity(String fileName, String originName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + getFullPath(fileName));

        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    /**
     * 서버에서 파일을 삭제합니다.
     *
     * @param fileName 서버에 저장된 파일명
     * @throws UnsupportedEncodingException
     */
    public static void deleteFile(String fileName) throws UnsupportedEncodingException {
        java.io.File file = new java.io.File(getFullPath(URLDecoder.decode(fileName, "UTF-8")));
        if (file.exists()) {
            boolean delete = file.delete();
        }
    }

}
