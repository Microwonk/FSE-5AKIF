package net.microwonk.studentenverwaltung.auth;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @NotNull
    @Size(max = 255)
    private String login;

    @NotNull
    @Size(max = 255)
    private String password;

}
