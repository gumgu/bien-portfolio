package com.study.admin.board.controller;

import com.study.admin.board.service.GalleryBoardService;
import com.study.admin.dto.AdminDTO;
import com.study.admin.dto.BoardSearchCondition;
import com.study.admin.dto.FileDTO;
import com.study.admin.dto.GalleryBoardDTO;
import com.study.admin.entity.UpdateCheck;
import com.study.admin.enums.SessionConst;
import com.study.admin.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 갤러리(Gallery) 게시글 컨트롤러
 */
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class GalleryBoardController {

    /**
     * 갤러리(Gallery) 게시글 서비스
     */
    private final GalleryBoardService galleryBoardService;

    /**
     * 파일(File) 서비스
     */
    private final FileService fileService;

    /**
     * 주어진 검색 조건에 맞춰 갤러리 게시글 리스트를 조회합니다.
     *
     * @param boardSearch 검색조건
     * @return 갤러리 게시글 리스트
     */
    @GetMapping("/galleries")
    public String getGalleries(@ModelAttribute("boardSearch") BoardSearchCondition boardSearch,
                               Model model) {
        log.info("GalleryController boardSearch = {}", boardSearch);

        // 게시판의 종류와 가져올 게시글 수를 지정합니다.
        boardSearch.setType("G");
        boardSearch.setPageSize(3);

        // 검색 조건에 맞는 게시글을 조회합니다.
        List<GalleryBoardDTO> galleryList = galleryBoardService.findBoardList(boardSearch);

        // 검색 조건에 맞는 게시글 수를 조회합니다.
        int totalListCount = galleryBoardService.findBoardCount(boardSearch);

        model.addAttribute("galleryList", galleryList);
        model.addAttribute("totalListCount", totalListCount);
        return "board/galleryList";
    }

    /**
     * 갤러리 게시글 작성/수정 폼을 제공합니다.
     *
     * @param galleryBoardDTO
     * @return
     */
    @GetMapping("/gallery")
    public String addForm(@ModelAttribute("galleryBoardDTO") GalleryBoardDTO galleryBoardDTO) {
        return "board/galleryForm";
    }

    /**
     * 주어진 seq와 일치하는 갤러리게시글 조회하고, 수정 폼을 제공합니다.
     *
     * @param seq
     * @return
     */
    @GetMapping("/gallery/{seq}")
    public String modifyForm(@PathVariable int seq, Model model) {

        GalleryBoardDTO galleryBoardDTO = galleryBoardService.findBoardBySeq(seq);

        List<FileDTO> fileList = fileService.findFileByBoardSeq(seq);

        model.addAttribute("fileList", fileList);
        model.addAttribute("galleryBoardDTO", galleryBoardDTO);

        return "board/galleryForm";
    }

    /**
     * 전달받은 갤러리 게시글 등록합니다.
     * - 게시글 등록 조건을 만족하지 않는 경우, 작성 페이지로 되돌아갑니다.
     *
     * @param galleryBoardDTO 저장할 갤러리 게시글 정보
     * @return
     */
    @PostMapping("/gallery")
    public String add(@Validated @ModelAttribute("galleryBoardDTO") GalleryBoardDTO galleryBoardDTO,
                      BindingResult bindingResult, HttpServletRequest request) throws IOException {

        log.info("저장할 galleryBoardDTO={}", galleryBoardDTO);
        log.info("저장할 Board={}", galleryBoardDTO.toBoardString());

        // binding 실패 시, 다시 작성 폼을 제공합니다.
        if (bindingResult.hasErrors()) {
            log.debug("error={}", bindingResult);
            return "board/galleryForm";
        }

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        if (adminDTO == null) {
            String redirectURL = "/admin/gallery";
            return "redirect:/admin/login?redirectURL=" + redirectURL;
        }

        galleryBoardDTO.setAdminId(adminDTO.getId());

        log.debug("galleryBoardDTO={}", galleryBoardDTO.toString());
        GalleryBoardDTO savedGallery = galleryBoardService.saveBoard(galleryBoardDTO);

        return "redirect:/admin/gallery/" + savedGallery.getSeq();
    }



    /**
     * 갤러리 게시글을 수정합니다.
     *
     * @param galleryBoardDTO 수정할 갤러리 게시글 정보
     * @return
     */
    @PostMapping("/gallery/{seq}")
    public String modify(@Validated(UpdateCheck.class) @ModelAttribute GalleryBoardDTO galleryBoardDTO,
                         BindingResult bindingResult, HttpServletRequest request) throws IOException {

        log.info("수정할 gallery = {}", galleryBoardDTO);

        // binding 실패 시, 다시 작성 폼을 제공합니다.
        if (bindingResult.hasErrors()) {
            log.debug("error={}", bindingResult);
            return "board/freeForm";
        }

        // 세션의 관리자 id를 DTO에 담습니다.
        HttpSession session = request.getSession();
        AdminDTO adminDTO = (AdminDTO) session.getAttribute(SessionConst.LOGIN_ADMIN);

        if (adminDTO == null) {
            String redirectURL = "/admin/gallery";
            return "redirect:/admin/login?redirectURL=" + redirectURL;
        }

        galleryBoardDTO.setAdminId(adminDTO.getId());

        GalleryBoardDTO modifyBoard = galleryBoardService.modifyBoard(galleryBoardDTO);

        return "redirect:/admin/gallery/" + modifyBoard.getSeq();
    }

    /**
     * 주어진 seq의 갤러리 게시글을 삭제합니다.
     *
     * @param seq 삭제할 갤러리게시글 seq
     * @return
     */
    @GetMapping("/gallery/delete/{seq}")
    public String delete(@PathVariable int seq) {

        galleryBoardService.deleteBoard(seq);

        return "redirect:/admin/galleries";
    }
}
