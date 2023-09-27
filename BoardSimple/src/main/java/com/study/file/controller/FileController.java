package com.study.file.controller;

import com.study.file.service.FileService;
import com.study.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriUtils;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

/**
 * 파일(File) 컨트롤러
 */
@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:3030")
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    /**
     * 파일(File) 서비스
     */
    private final FileService fileService;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam String originName,
                                                 @RequestParam String fileName) throws MalformedURLException {

        log.info("originName = {}", originName);
        log.info("fileName = {}", fileName);

        Resource resource = FileUtil.getDownloadResponseEntity(fileName, originName);

        String encodedUploadFileName = UriUtils.encode(originName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

}
