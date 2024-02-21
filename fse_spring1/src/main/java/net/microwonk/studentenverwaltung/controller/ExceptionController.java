package net.microwonk.studentenverwaltung.controller;

import net.microwonk.studentenverwaltung.auth.AuthUserNotFoundInDbException;
import net.microwonk.studentenverwaltung.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(StudentNichtGefunden.class)
    public ResponseEntity<ExceptionDTO> studentNichtGefunden(StudentNichtGefunden studentNichtGefunden) {
        return new ResponseEntity<>(new ExceptionDTO("1000",studentNichtGefunden.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentValidierungFehlgeschlagen.class)
    public ResponseEntity<FormValidierungExceptionDTO> studentValidierungFehlgeschlagen(
            StudentValidierungFehlgeschlagen studentValidierungFehlgeschlagen
    ) {
        return new ResponseEntity<>(studentValidierungFehlgeschlagen.getErrorMap(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NoAuthHeaderFoundException.class})
    public ResponseEntity<ExceptionResponse> handleException(NoAuthHeaderFoundException noAuthHeaderFoundException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("5000",noAuthHeaderFoundException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({AuthUserNotFoundInDbException.class})
    public ResponseEntity<ExceptionResponse> handleException(AuthUserNotFoundInDbException authUserNotFoundInDbException) {
        ExceptionResponse exceptionResponse = new ExceptionResponse("5001",authUserNotFoundInDbException.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    public record ExceptionResponse(String code, String message) { }
}
