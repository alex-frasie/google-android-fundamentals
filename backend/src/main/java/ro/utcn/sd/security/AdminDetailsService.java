package ro.utcn.sd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.utcn.sd.exceptions.IncorrectCredentialsException;
import ro.utcn.sd.model.Admin;
import ro.utcn.sd.repositories.AdminRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Assigns role to user if found
     * @param username with which user it logs in
     * @return details
     * @throws UsernameNotFoundException if username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin cashier = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Unknown admin"));

        return new User(cashier.getUsername(), cashier.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    /**
     * Loads current user
     * @return current user
     */
    @Transactional
    public Admin loadCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return adminRepository.findByUsername(username)
                .orElseThrow(IncorrectCredentialsException::new);
    }

}
