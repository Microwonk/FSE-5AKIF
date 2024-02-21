package net.microwonk.studentenverwaltung.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.auth.*;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.FormValidierungExceptionDTO;
import net.microwonk.studentenverwaltung.exceptions.NoAuthHeaderFoundException;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.exceptions.StudentValidierungFehlgeschlagen;
import net.microwonk.studentenverwaltung.services.StudentsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500") // port einstellen für webseite
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentRestController {

    private final JwtTokenService jwtTokenService;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;
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

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody @Valid final AuthenticationRequest authenticationRequest) {
        try{
            System.out.println(authenticationRequest.getLogin());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAccessToken(jwtTokenService.generateAccessToken(userDetails));
        authenticationResponse.setRefreshToken(jwtTokenService.generateRefreshToken(userDetails));
        return authenticationResponse;
    }

    @PostMapping("/rtoken")//Route abgesichert, nur mit gültigem Token aufrufbar
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        //Token auslesen → muss gültig sein, sonst wäre diese Route /rtoken durch Spring-Security abgesichert und nicht erreichbar
        final String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            throw new NoAuthHeaderFoundException();
        }
        final String refreshToken = header.substring(7);
        final String username = jwtTokenService.validateTokenAndGetUsername(refreshToken);
        if (username != null) {
            final JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
            //neuen Access-Token für Benutzer generieren
            var accessToken = jwtTokenService.generateAccessToken(userDetails);
            //neuen Response zusammenbauen
            var authResponse = new AuthenticationResponse();
            authResponse.setRefreshToken(refreshToken);//bleibt der gleiche
            authResponse.setAccessToken(accessToken);//neuer Access Token
            //Response-Daten in den HTTP-Response-Body schreiben
            new ObjectMapper().writeValue(httpServletResponse.getOutputStream(),authResponse);//Response umschreiben
        } else
        {
            throw new AuthUserNotFoundInDbException();
        }
    }
}