package net.microwonk.studentenverwaltung.services;

import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;

import java.util.List;

public interface StudentsService {
    List<Student> allStudents();
    Student insertStudent(Student student);
    Student updateStudent(Student student) throws StudentNichtGefunden;
    Student studentWithId(Long id) throws StudentNichtGefunden;
    List<Student> studentsWithPlz(String plz);
    Student deleteStudent(Long id) throws StudentNichtGefunden;
}
