package net.microwonk.studentenverwaltung.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.FormValidierungExceptionDTO;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.exceptions.StudentValidierungFehlgeschlagen;
import net.microwonk.studentenverwaltung.services.StudentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@CrossOrigin(origins = "http://127.0.0.1:5500") // port einstellen für webseite
@AllArgsConstructor
public class StudentRestController {

    private final StudentsService studentsService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(this.studentsService.allStudents());
    }

    @PostMapping
    public ResponseEntity<Student> insertStudent(@Valid @RequestBody Student student, BindingResult bindingResult) throws StudentValidierungFehlgeschlagen {
        FormValidierungExceptionDTO formValidationErrors = new FormValidierungExceptionDTO("9000");

        if (bindingResult.hasErrors()) {
            handleErrors(bindingResult, formValidationErrors);
        }
        return ResponseEntity.ok(this.studentsService.insertStudent(student));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student, BindingResult bindingResult) throws StudentValidierungFehlgeschlagen, StudentNichtGefunden {
        FormValidierungExceptionDTO formValidationErrors = new FormValidierungExceptionDTO("9000");

        if (student.getId() == null) {
            throw new StudentNichtGefunden("Studenten-Update mit Studenten ohne ID nicht möglich!");
        }
        if (bindingResult.hasErrors()) {
            handleErrors(bindingResult, formValidationErrors);
        }
        return ResponseEntity.ok(this.studentsService.updateStudent(student));
    }

    private void handleErrors(
            BindingResult bindingResult, FormValidierungExceptionDTO formValidationErrors
    ) throws StudentValidierungFehlgeschlagen {
        bindingResult.getAllErrors().forEach(error ->
                formValidationErrors.addFormValidationError(((FieldError) error).getField(), error.getDefaultMessage())
        );
        throw new StudentValidierungFehlgeschlagen(formValidationErrors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) throws StudentNichtGefunden {
        return ResponseEntity.ok(this.studentsService.deleteStudent(id));
    }

    @GetMapping("/plz/{plz}")
    public ResponseEntity<List<Student>> studentsWithPlz(@PathVariable String plz) {
        return ResponseEntity.ok(this.studentsService.studentsWithPlz(plz));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> studentWithId(@PathVariable Long id) throws StudentNichtGefunden {
        return ResponseEntity.ok(this.studentsService.studentWithId(id));
    }
}