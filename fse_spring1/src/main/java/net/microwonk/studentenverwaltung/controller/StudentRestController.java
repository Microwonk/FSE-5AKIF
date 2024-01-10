package net.microwonk.studentenverwaltung.controller;

import jakarta.validation.Valid;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.FormValidierungExceptionDTO;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.exceptions.StudentValidierungFehlgeschlagen;
import net.microwonk.studentenverwaltung.services.StudentenService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/studenten")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class StudentRestController {

    private StudentenService studentenService;

    public StudentRestController(StudentenService studentenService) {
        this.studentenService = studentenService;
    }

    @GetMapping
    //CURL-Call zum Testen: curl -H "Accept: application/json" localhost:8080/api/v1/studenten
    public ResponseEntity<List<Student>> gibAlleStudenten() {
        return ResponseEntity.ok(this.studentenService.alleStudenten());
    }

    @PostMapping
    //CURL-Call zum Testen: curl -X POST -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name":"Günter Hasi 2","plz":"3322"}' http://localhost:8080/api/v1/studenten
    public ResponseEntity<Student> studentEinfuegen(@Valid @RequestBody Student student, BindingResult bindingResult) throws StudentValidierungFehlgeschlagen {
        //    String errors = "";
        FormValidierungExceptionDTO formValidationErrors = new FormValidierungExceptionDTO("9000");

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                formValidationErrors.addFormValidationError(((FieldError) error).getField(), error.getDefaultMessage());
            }
            throw new StudentValidierungFehlgeschlagen(formValidationErrors);
        } else {
            System.out.println("NAME: " + student.getName());
            return ResponseEntity.ok(this.studentenService.studentEinfuegen(student));
        }
    }

    @PutMapping
    //CURL-Call zum Testen: curl -X PUT -H "Accept: application/json" -H "Content-Type: application/json" -d '{"name":"Günter Hasi","plz":"3322"}' http://localhost:8080/api/v1/studenten
    public ResponseEntity<Student> studentUpdaten(@Valid @RequestBody Student student, BindingResult bindingResult) throws StudentValidierungFehlgeschlagen, StudentNichtGefunden {
        //    String errors = "";
        FormValidierungExceptionDTO formValidationErrors = new FormValidierungExceptionDTO("9000");

        if (student.getId() == null)
            throw new StudentNichtGefunden("Studenten-Update mit Studenten ohne ID nicht möglich!");

        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                formValidationErrors.addFormValidationError(((FieldError) error).getField(), error.getDefaultMessage());
            }
            throw new StudentValidierungFehlgeschlagen(formValidationErrors);
        } else {
            return ResponseEntity.ok(this.studentenService.studentUpdaten(student));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> studentLoeschen(@PathVariable Long id) throws StudentNichtGefunden {
        return ResponseEntity.ok(this.studentenService.studentLoeschenMitId(id));
    }

    @GetMapping("/mitplz/{plz}")
    public ResponseEntity<List<Student>> alleStudentenMitPlz(@PathVariable String plz) {
        return ResponseEntity.ok(this.studentenService.alleStudentenMitPlz(plz));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> studentMitId(@PathVariable Long id) throws StudentNichtGefunden {
        return ResponseEntity.ok(this.studentenService.studentMitId(id));
    }
}