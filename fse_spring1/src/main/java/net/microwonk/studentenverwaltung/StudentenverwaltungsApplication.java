package net.microwonk.studentenverwaltung;

import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.repositories.DbZugriffStudenten;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class StudentenverwaltungsApplication implements ApplicationRunner {

	private final DbZugriffStudenten dbZugriffStudenten;

	public static void main(String[] args) {
		SpringApplication.run(StudentenverwaltungsApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		this.dbZugriffStudenten.insertStudent(new Student("Claudio Landerer","6460"));
		this.dbZugriffStudenten.insertStudent(new Student("Günter Hasel","3322"));
		this.dbZugriffStudenten.insertStudent(new Student("Maria Brunsteiner","8080"));
	}
}
