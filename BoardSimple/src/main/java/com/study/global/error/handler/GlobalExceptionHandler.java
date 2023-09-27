package com.study.global.error.handler;

import com.study.global.api.dto.APIResult;
import com.study.global.api.dto.State;
import com.study.global.error.dto.ErrorCode;
import com.study.global.error.dto.ErrorDTO;
import com.study.global.error.exception.LoginFailedException;
import com.study.global.error.exception.NoSuchBoardSeqException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception을 다루기 위한 클래스 입니다.
 * - Exception 발생 시,
 * - APIResult. state : FAILURE(0)
 * - APIResult. result : ErrorDTO(ErrorCode, message)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 잘못된 게시글 ID로 접근하는 경우, 에러 문자를 반환합니다.
     *
     * @param ex(NoSuchBoardIdException)
     * @return ErrorDTO
     */
    @ExceptionHandler(NoSuchBoardSeqException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResult handleNoSuchBoardIdException(NoSuchBoardSeqException ex) {
        log.info("handleNoSuchBoardIdException 적용");

        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.NO_SUCH_BOARD_ID);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.FAILURE);
        apiResult.putResult("errorDTO", errorDTO);
        return apiResult;
    }

    /**
     * 아이디, 비밀번호가 일치하지 않는경우, 로그인 오류 문자를 반환합니다.
     */
    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public APIResult handleLoginFailedException(LoginFailedException ex) {
        log.debug("LoginFailedException 적용");

        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.LOGIN_FAILED);

        APIResult apiResult = new APIResult();
        apiResult.setState(State.FAILURE);
        apiResult.putResult("errorDTO", errorDTO);
        return apiResult;
    }

    /**
     * refresh 토큰 갱신 중 발생할 수 있는 InvalidTokenException,
     * ExpiredJwtException 예외를 다룹니다.
     *
     * @param ex InvalidTokenException
     * @return ErrorDTO
     */
//    @ExceptionHandler({InvalidTokenException.class, ExpiredJwtException.class})
//    public ResponseEntity handleInvalidTokenException(InvalidTokenException ex) {
//        log.info("handleInvalidTokenException 적용");
//
//        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.INVALID_TOKEN);
//
//        APIResult apiResult = new APIResult();
//        apiResult.setState(State.FAILURE);
//        apiResult.putResult("errorDTO", errorDTO);
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResult);
//    }

    /**
     * 예상되는 exception 외, 발생할 수 있는 Exception을 대비합니다.
     * @param ex(Exception)
     * @return ErrorDTO
     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public APIResult handleAllException(Exception ex) {
//        log.info("handleAllException 적용");
//
//        ErrorDTO errorDTO = new ErrorDTO(ErrorCode.EXCEPTION);
//
//        APIResult apiResult = new APIResult();
//        apiResult.setState(State.FAILURE);
//        apiResult.putResult("errorDTO", errorDTO);
//        return apiResult;
//    }

}
