package ro.utcn.sd.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.CustomerDTO;
import ro.utcn.sd.exceptions.EmailAlreadyInUseException;
import ro.utcn.sd.exceptions.PasswordTooSmallException;
import ro.utcn.sd.exceptions.UsernameAlreadyInUseException;
import ro.utcn.sd.repositories.CustomerRepository;

@Component
public class CustomerValidator {

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Validate if the email/username are not used and if the password has minimum 8 characters
     * @param customerDTO to be checked
     * @return true fi valid
     * @throws EmailAlreadyInUseException if email exists
     * @throws UsernameAlreadyInUseException if user already exists
     * @throws PasswordTooSmallException if password is shorter than 8 characters
     */
    public boolean isValid(CustomerDTO customerDTO){
        if(!isEmailUnique(customerDTO.getEmail()))
            throw new EmailAlreadyInUseException();
        if(!isUsernameUnique(customerDTO.getUsername()))
            throw new UsernameAlreadyInUseException();
        if(customerDTO.getPassword().length() < 8)
            throw new PasswordTooSmallException();

        return true;
    }

    public boolean isEmailUnique(String email){
        if(customerRepository.findByEmail(email).isPresent())
            return false;

        return true;
    }

    public boolean isUsernameUnique(String username){
        if(customerRepository.findByUsername(username).isPresent())
            return false;

        return true;
    }
}
