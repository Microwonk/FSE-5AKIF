package net.microwonk.studentenverwaltung.repositories;

import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;

import java.util.List;

public interface DbZugriffStudenten {
    Student insertStudent(Student student);
    List<Student> allStudents();
    List<Student> allStudentsWithPlz(String plz);
    Student studentWithId(Long id) throws StudentNichtGefunden;
    Student deleteStudent(Long id) throws StudentNichtGefunden;
}
