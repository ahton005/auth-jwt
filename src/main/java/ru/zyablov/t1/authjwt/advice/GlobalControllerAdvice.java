package ru.zyablov.t1.authjwt.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.zyablov.t1.authjwt.exception.AuthorityNotFoundException;
import ru.zyablov.t1.authjwt.exception.UserExistException;

@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleBindException(Exception exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Внутренняя ошибка сервера");
        problemDetail.setProperty("message", exception.getMessage());
        return ResponseEntity.internalServerError().body(problemDetail);
    }

    @ExceptionHandler(AuthorityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleBindException(AuthorityNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Доступ запрещен");
        problemDetail.setProperty("message", exception.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ProblemDetail> handleBindException(UserExistException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Пользователь уже существует");
        problemDetail.setProperty("message", exception.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleBindException(UsernameNotFoundException exception) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Пользователь не найден");
        problemDetail.setProperty("message", exception.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
