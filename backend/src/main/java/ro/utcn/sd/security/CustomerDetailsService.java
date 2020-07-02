package ro.utcn.sd.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.repositories.CustomerRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class CustomerDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Assigns role to user if found
     * @param username with which user it logs in
     * @return details
     * @throws UsernameNotFoundException if username not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(customerRepository.findByUsername(username).isPresent()) {
            Customer customer = customerRepository.findByUsername(username).get();
            return new User(customer.getUsername(), customer.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        } else if(customerRepository.findByEmail(username).isPresent()) {
            Customer customer = customerRepository.findByEmail(username).get();
            return new User(customer.getUsername(), customer.getPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_CUSTOMER")));
        }

        return null;
     }

    /**
     * Loads current user
     * @return current user
     */
    @Transactional
    public Customer loadCurrentUser(){
         String username = SecurityContextHolder.getContext().getAuthentication().getName();
         if(customerRepository.findByUsername(username).isPresent()) {
             return customerRepository.findByUsername(username).get();
         }

         return null;
     }

}
