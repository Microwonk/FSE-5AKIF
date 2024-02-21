package net.microwonk.studentenverwaltung;

import lombok.AllArgsConstructor;
import net.microwonk.studentenverwaltung.domain.Client;
import net.microwonk.studentenverwaltung.domain.Student;
import net.microwonk.studentenverwaltung.repositories.ClientRepository;
import net.microwonk.studentenverwaltung.repositories.DbZugriffStudenten;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class StudentenverwaltungsApplication implements ApplicationRunner {

	private final DbZugriffStudenten dbZugriffStudenten;

	@Autowired
	ClientRepository clientRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(StudentenverwaltungsApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
		dbZugriffStudenten.insertStudent(new Student("Nicolas Frey","1234"));
		dbZugriffStudenten.insertStudent(new Student("Vorname Nachname","5678"));
		dbZugriffStudenten.insertStudent(new Student("Maria Werwolf","8080"));

		clientRepository.save(new Client("user1", passwordEncoder.encode("1234")));
		clientRepository.save(new Client("admin", passwordEncoder.encode("admin")));
	}
}
