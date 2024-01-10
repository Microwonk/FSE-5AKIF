package net.microwonk.studentenverwaltung.repositories;

import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.exceptions.StudentNichtGefunden;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DbZugriffStudentenJPA implements DbZugriffStudenten {

    private final StudentJPARepo studentJPARepo;

    public DbZugriffStudentenJPA(StudentJPARepo studentJPARepo) {
        this.studentJPARepo = studentJPARepo;
    }

    @Override
    public Student studentSpeichern(Student student) {
        return this.studentJPARepo.save(student);
    }

    @Override
    public List<Student> alleStudenten() {
        return this.studentJPARepo.findAll();
    }

    @Override
    public List<Student> alleStudentenAusDemOrt(String plz) {
        return this.studentJPARepo.findAllByPlz(plz);
    }

    @Override
    public Student studentMitId(Long id) throws StudentNichtGefunden {
        Optional<Student> optStudent = this.studentJPARepo.findById(id);
        return optStudent.orElseThrow(() -> new StudentNichtGefunden("Student mit der Id " + id + " nicht gefunden!"));
    }

    @Override
    public Student studentLoeschenMitId(Long id) throws StudentNichtGefunden {
      Student studentAusDb = this.studentMitId(id);
      this.studentJPARepo.deleteById(studentAusDb.getId());
      return studentAusDb;
    }
}
