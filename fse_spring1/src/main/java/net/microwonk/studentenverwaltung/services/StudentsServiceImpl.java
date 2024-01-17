package net.microwonk.studentenverwaltung.services;

import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.repositories.DbZugriffStudenten;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentenServiceImpl implements StudentenService {

    private final DbZugriffStudenten dbZugriffStudenten;

    public StudentenServiceImpl(DbZugriffStudenten dbZugriffStudenten) {
        this.dbZugriffStudenten = dbZugriffStudenten;
    }

    @Override
    public List<Student> allStudents() {
        return this.dbZugriffStudenten.allStudents();
    }

    @Override
    public Student studentEinfuegen(Student student) {
        return this.dbZugriffStudenten.insertStudent(student);
    }

    @Override
    public Student studentUpdaten(Student student) throws StudentNichtGefunden {
        Student studentAusDb = this.dbZugriffStudenten.studentWithId(student.getId());
        studentAusDb.setName(student.getName());
        studentAusDb.setPlz(student.getPlz());
        return this.dbZugriffStudenten.insertStudent(studentAusDb);
    }

    @Override
    public Student studentMitId(Long id) throws StudentNichtGefunden {
        return this.dbZugriffStudenten.studentWithId(id);
    }

    @Override
    public List<Student> alleStudentenMitPlz(String plz) {
        return this.dbZugriffStudenten.allStudentsWithPlz(plz);
    }

    @Override
    public Student deleteStudent(Long id) throws StudentNichtGefunden {
        return this.dbZugriffStudenten.deleteStudent(id);
    }
}
