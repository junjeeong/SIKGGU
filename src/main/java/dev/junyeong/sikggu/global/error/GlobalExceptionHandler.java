package dev.junyeong.sikggu.global.error;

import jakarta.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException e,
      HttpServletRequest request) {
    log.warn("Validation Exception: {} -> {}", request.getRequestURI(), e.getMessage());

    // 첫 번째 필드 에러 메시지를 추출하여 클라이언트에게 명시적으로 전달
    String defaultMessage = e.getBindingResult().getFieldError() != null ?
        e.getBindingResult().getFieldError().getDefaultMessage() : "유효성 검사 실패";

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "BAD_REQUEST",
        defaultMessage
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e,
      HttpServletRequest request) {
    log.warn("Illegal Argument Exception: {} -> {}", request.getRequestURI(), e.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.BAD_REQUEST.value(),
        "INVALID_REQUEST",
        e.getMessage()
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e,
      HttpServletRequest request) {
    log.warn("Not Found Exception: {} -> {}", request.getRequestURI(), e.getMessage());

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.NOT_FOUND.value(),
        "NOT_FOUND",
        e.getMessage()
    );

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleAllException(Exception e, HttpServletRequest request) {
    log.error("Internal Server Error: {} -> {}", request.getRequestURI(), e.getMessage(),
        e);

    ErrorResponse errorResponse = new ErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "INTERNAL_SERVER_ERROR",
        "서버에 예상치 못한 오류가 발생했습니다."
    );

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}