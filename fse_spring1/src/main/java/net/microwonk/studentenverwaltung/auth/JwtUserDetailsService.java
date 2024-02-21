package net.microwonk.studentenverwaltung.auth;

import net.microwonk.studentenverwaltung.domain.Client;
import net.microwonk.studentenverwaltung.repositories.ClientRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private ClientRepository clientRepository;

    public JwtUserDetailsService(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }
    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Client client = clientRepository.findClientByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));

        final List<SimpleGrantedAuthority> roles;
        if(username.equals("admin"))
        {
            //Wenn der User admin heißt, dann bekommt er mehr Rollen.
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else
        {
            roles = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new JwtUserDetails(client.getId(), username, client.getHash(), roles);
    }
}
