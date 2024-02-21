package net.microwonk.studentenverwaltung.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Client {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String login;

    private String hash;

    public Client(String  login, String hash)
    {
        this.login = login;
        this.hash = hash;
    }

}
