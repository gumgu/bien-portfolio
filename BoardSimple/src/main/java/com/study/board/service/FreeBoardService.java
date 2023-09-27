package com.study.board.service;

import com.study.board.dto.BoardSearchCondition;
import com.study.board.dto.FreeBoardDTO;
import com.study.board.entity.Board;
import com.study.board.entity.Category;
import com.study.board.repository.FreeBoardRepository;
import com.study.file.dto.FileDTO;
import com.study.file.repository.FileRepository;
import com.study.global.util.FileUtil;
import com.study.global.util.ParamUtil;
import com.study.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 자유 게시글(Free Board) 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FreeBoardService {

    /**
     * 자유 게시글 (FreeBoard) 리포지터리
     */
    private final FreeBoardRepository boardRepository;

    /**
     * 파일(File) 관련 Utility
     */
    private final FileUtil fileUtil;

    /**
     * 파일(File) CRUD 리포지터리
     */
    private final FileRepository fileRepository;

    /**
     * 댓글(Reply) CRUD 리포지터리
     */
    private final ReplyRepository replyRepository;

    /**
     * 새로운 자유 게시글을 등록합니다.
     *
     * @param freeBoardDTO 등록할 게시글 정보
     * @return 등록된 게시글 정보 (boardId 포함)
     * @throws IOException
     */
    public FreeBoardDTO saveFreeBoard(FreeBoardDTO freeBoardDTO) throws IOException {

        boardRepository.saveBoard(freeBoardDTO);

        log.info("freeBoardDTO.getAddFiles() = {}", freeBoardDTO.getAddFiles());
        if (!ParamUtil.isNull(freeBoardDTO.getAddFiles())) {
            // 첨부한 파일이 있는 경우, 서버에 업로드하고 db에 해당 정보를 저장합니다.
            for (MultipartFile file : freeBoardDTO.getAddFiles()) {
                log.info("saveFreeBoar의 file = {}", file);
                FileDTO fileDTO = fileUtil.uploadFile(file);
                log.info("fileDTO = {}", fileDTO);
                fileRepository.saveFile(fileDTO, freeBoardDTO.getSeq());
            }
        }
        return freeBoardDTO;
    }

    /**
     * 주어진 seq와 일치하는 게시글을 조회합니다.
     *
     * @param seq 조회할 게시글 번호
     * @return 조회한 게시글
     */
    public FreeBoardDTO findFreeBySeq(int seq) {
        return boardRepository.findBoardBySeq(seq);
    }

    /**
     * 주어진 자유게시글 seq의 조회수를 1 증가시킵니다.
     *
     * @param seq 업데이트할 게시글 번호
     */
    public void updateReadCount(int seq) {
        boardRepository.updateReadCount(seq);
    }

    /**
     * 검색 조건에 맞는 게시글을 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 리스트
     */
    public List<Board> findFreeBoardList(BoardSearchCondition boardSearchCondition) {
        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        // startRow: 한 페이지에 출력될 가장 첫 게시글의 번호
        int startRow = (boardSearchCondition.getCurrentPage() - 1) * boardSearchCondition.getPageSize();
        log.info("startRow = {}", startRow);

        return boardRepository.findBoardList(boardSearchCondition, startRow);
    }

    /**
     * 검색 조건에 맞는 게시글 갯수를 조회합니다.
     * - 검색조건이 null인 경우 default값을 설정합니다.
     *
     * @param boardSearchCondition 검색조건
     * @return 게시글 수
     */
    public int findFreeBoardListCount(BoardSearchCondition boardSearchCondition) {

        // 날짜 검색 시작일이 null인 경우, default 값을 설정합니다. (한달 전 금일)
        if (ParamUtil.isNull(boardSearchCondition.getFromDate())) {
            boardSearchCondition.setDefaultFromDate();
        }

        // 날짜 검색 종료일이 null인 경우, default 값을 설정합니다. (익일)
        if (ParamUtil.isNull(boardSearchCondition.getToDate())) {
            boardSearchCondition.setDefaultToDate();
        }

        return boardRepository.findBoardListCount(boardSearchCondition);
    }

    /**
     * 카테고리 리스트를 조회합니다.
     *
     * @param type 게시판 타입
     * @return 타입안에 포함된 카테고리 리스트
     */
    public List<Category> getCategoryList(String type) {
        return boardRepository.getCategoryList(type);
    }

    /**
     * 주어진 seq의 게시글을 수정합니다.
     * - 게시글의 정보를 수정합니다.
     * - 삭제 요청 파일(deleteFileNames)을 제거합니다.
     * - 추가 요청 파일(addFiles)를 업로드합니다.
     *
     * @param paramBoard 수정할 게시글 정보
     * @return
     */
    public FreeBoardDTO updateFreeBoard(FreeBoardDTO paramBoard) throws IOException {
        // Board 게시글 수정
        boardRepository.modifyBoard(paramBoard);

        // 기존 파일 삭제
        if (!ParamUtil.isNull(paramBoard.getDeleteFileNames())) {
            for (int i = 0; i < paramBoard.getDeleteFileNames().size(); i++) {
                // db 정보 제거
                fileRepository.deleteFileByFileName(paramBoard.getDeleteFileNames().get(i));
                // 서버의 파일 제거
                FileUtil.deleteFile(paramBoard.getDeleteFileNames().get(i));
            }
        }

        // 새로운 파일 업로드
        if (!ParamUtil.isNull(paramBoard.getAddFiles())) {
            // 첨부한 파일이 있는 경우, 서버에 업로드하고 db에 해당 정보를 저장합니다.
            for (MultipartFile file : paramBoard.getAddFiles()) {
                FileDTO fileDTO = fileUtil.uploadFile(file);
                fileRepository.saveFile(fileDTO, paramBoard.getSeq());
            }
        }

        return paramBoard;
    }

    /**
     * 주어진 게시글 번호의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 게시글 번호
     */
    public void deleteGalleryBoard(int seq) throws UnsupportedEncodingException {

        // 서버내 파일 삭제
        List<FileDTO> findFiles = fileRepository.findFileByBoardSeq(seq);
        for (int i =0; i <findFiles.size(); i++) {
            FileUtil.deleteFile(findFiles.get(i).getFileName());
        }

        // 파일 정보 삭제
        fileRepository.deleteFileByBoardSeq(seq);

        // 게시글 정보 삭제
        boardRepository.deleteBoard(seq);

    }

    /**
     * 주어진 게시글 번호의 게시글을 삭제합니다.
     *
     * @param seq 삭제할 게시글 번호
     */
    public void deleteFreeBoard(int seq) throws UnsupportedEncodingException {

        // 서버내 파일 삭제
        List<FileDTO> findFiles = fileRepository.findFileByBoardSeq(seq);
        for (int i =0; i <findFiles.size(); i++) {
            FileUtil.deleteFile(findFiles.get(i).getFileName());
        }

        // 파일 정보 삭제
        fileRepository.deleteFileByBoardSeq(seq);

        // 댓글 정보 삭제
        replyRepository.deleteByBoardSeq(seq);

        // 게시글 정보 삭제
        boardRepository.deleteBoard(seq);

    }
}
