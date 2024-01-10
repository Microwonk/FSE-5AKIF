package net.microwonk.studentenverwaltung;

import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.repositories.DbZugriffStudenten;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentenverwaltungApplication implements ApplicationRunner {

	final DbZugriffStudenten dbZugriffStudenten;

	public StudentenverwaltungApplication(DbZugriffStudenten dbZugriffStudenten) {
		this.dbZugriffStudenten = dbZugriffStudenten;
	}

	public static void main(String[] args) {
		SpringApplication.run(StudentenverwaltungApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		this.dbZugriffStudenten.studentSpeichern(new Student("Claudio Landerer","6460"));
		this.dbZugriffStudenten.studentSpeichern(new Student("Günter Hasel","3322"));
		this.dbZugriffStudenten.studentSpeichern(new Student("Maria Brunsteiner","8080"));
	}
}
