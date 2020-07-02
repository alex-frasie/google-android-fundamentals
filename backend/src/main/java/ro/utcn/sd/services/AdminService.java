package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.utcn.sd.exceptions.IncorrectCredentialsException;
import ro.utcn.sd.model.Admin;
import ro.utcn.sd.repositories.AdminRepository;


@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Add a new administrator
     * @param newAdmin to be added
     */
    public void addAdmin(Admin newAdmin){
        newAdmin.setPassword(passwordEncoder.encode(newAdmin.getPassword()));
        adminRepository.save(newAdmin);
    }

    /**
     * Login as administrator
     * @param username of admin
     * @param password of admin
     * @return the admin if credentials match
     * @throws IncorrectCredentialsException otherwise
     */
    public Admin loginAdmin(String username, String password) {
        if(adminRepository.findByUsername(username).isPresent()) {
            Admin admin = adminRepository.findByUsername(username).get();

            if (passwordEncoder.matches(password, admin.getPassword()))
                return admin;
            else {
                throw new IncorrectCredentialsException();
            }
        } else throw new IncorrectCredentialsException();
    }
}
