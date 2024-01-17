package net.microwonk.studentenverwaltung.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.services.StudentsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class ThymeleafController {

    private final StudentsService studentService;

    @GetMapping("/web/students")
    public ModelAndView allStudents() {
        return new ModelAndView("allstudents", "studenten", studentService.allStudents());
    }

    @GetMapping("/web/insertstudentform")
    public ModelAndView insertStudentForm() {
        return new ModelAndView("insertstudentform", "mystudent", new Student());
    }

    @GetMapping("/web/deletestudent/{id}")
    public String deleteStudent(@PathVariable Long id, Model model) {
        try {
            studentService.deleteStudent(id);
            return "redirect:/web/students";
        } catch (StudentNichtGefunden e) {
            model.addAttribute("errortitle", "Student-Löschen schlägt fehl!");
            model.addAttribute("errormessage", e.getMessage());
            return "myerrorspage";
        }
    }

    @PostMapping("/web/insertstudent")
    public String insertStudent(@Valid @ModelAttribute("mystudent") Student student, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "insertstudentform";
        }
        this.studentService.insertStudent(student);
        return "redirect:/web/students";
    }
}
