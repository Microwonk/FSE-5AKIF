package net.microwonk.studentenverwaltung.controller;

import net.microwonk.studentenverwaltung.exceptions.ExceptionDTO;
import net.microwonk.studentenverwaltung.exceptions.FormValidierungExceptionDTO;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.exceptions.StudentValidierungFehlgeschlagen;
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
}
