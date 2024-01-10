package net.microwonk.studentenverwaltung.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 2, max = 255, message = "Der Studentenname '${validatedValue}' ist ungültig. Studentennamen müssen zwischen {min} und {max} Zeichen lang sein.")
    private String name;

    @Size(min = 4, max = 6, message = "Die Postleitzahl '${validatedValue}' ist ungültig. Postleitzahlen müssen zwischen {min} und {max} Zeichen lang sein.")
    private String plz;

    public Student(String name, String plz) {
        this.name = name;
        this.plz = plz;
    }

}
