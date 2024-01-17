package net.microwonk.studentenverwaltung.services;

import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import net.microwonk.studentenverwaltung.repositories.DbZugriffStudenten;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentsServiceImpl implements StudentsService {

    private final DbZugriffStudenten dbZugriffStudenten;

    @Override
    public List<Student> allStudents() {
        return dbZugriffStudenten.allStudents();
    }

    @Override
    public Student insertStudent(Student student) {
        return dbZugriffStudenten.insertStudent(student);
    }

    @Override
    public Student updateStudent(Student student) throws StudentNichtGefunden {
        Student studentAusDb = dbZugriffStudenten.studentWithId(student.getId());
        studentAusDb.setName(student.getName());
        studentAusDb.setPlz(student.getPlz());
        return dbZugriffStudenten.insertStudent(studentAusDb);
    }

    @Override
    public Student studentWithId(Long id) throws StudentNichtGefunden {
        return dbZugriffStudenten.studentWithId(id);
    }

    @Override
    public List<Student> studentsWithPlz(String plz) {
        return dbZugriffStudenten.allStudentsWithPlz(plz);
    }

    @Override
    public Student deleteStudent(Long id) throws StudentNichtGefunden {
        return dbZugriffStudenten.deleteStudent(id);
    }
}
