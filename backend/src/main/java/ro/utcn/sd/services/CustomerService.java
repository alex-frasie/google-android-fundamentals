package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.CustomerDTO;
import ro.utcn.sd.dto.PasswordResetDTO;
import ro.utcn.sd.dto.RegisterDTO;
import ro.utcn.sd.exceptions.IncorrectCredentialsException;
import ro.utcn.sd.exceptions.PasswordTooSmallException;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.repositories.CustomerRepository;
import ro.utcn.sd.utils.CustomerBuilder;
import ro.utcn.sd.utils.email.EmailSender;
import ro.utcn.sd.utils.mappers.CustomerMapper;
import ro.utcn.sd.utils.validators.CustomerValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService  {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerBuilder builder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private CustomerValidator customerValidator;

    /**
     * Register a new customer
     * @param registerDTO info to be registered
     * @return a new customer if successful
     */
    public Customer registerNewCustomer(RegisterDTO registerDTO){

        CustomerDTO customerDTO = customerMapper.convertToDTO(registerDTO);

        if(!customerValidator.isValid(customerDTO)){
            return null;
        }

        Customer customer = builder.firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .email(customerDTO.getEmail())
                .username(customerDTO.getUsername())
                .password(passwordEncoder.encode(customerDTO.getPassword()))
                .buildUser();

        addressService.addAddress(customerDTO.getAddressDTO(), customer);
        customerRepository.save(customer);

        return customer;

    }

    /**
     * Delete account of a user
     * @param username of user who wants to delete account
     */
    public void deleteCustomer(String username){

        Customer customer = customerRepository.findByUsername(username).get();

        customerRepository.delete(customer);
    }

    /**
     * Login for customer, with any credential
     * @param credential can be either username or email
     * @param password to match the account
     * @return a customer if successfull
     * @throws IncorrectCredentialsException otherwise
     */
    public Customer loginCustomer(String credential, String password) {

        if(customerRepository.findByUsername(credential).isPresent()){
            Customer customer = customerRepository.findByUsername(credential).get();

            if(passwordEncoder.matches(password, customer.getPassword()))
                return customer;
        }

        if(customerRepository.findByEmail(credential).isPresent()){
            Customer customer = customerRepository.findByEmail(credential).get();

            if(passwordEncoder.matches(password, customer.getPassword()))
                return customer;
        }

        throw new IncorrectCredentialsException();
    }

    /**
     * Returns all customers existent
     * @return a list of the customers
     */
    public List<CustomerDTO> getAllCustomers(){
       List<Customer> customers = customerRepository.findAll();
       List<CustomerDTO> customerDTOs = new ArrayList<>();

       for(Customer customer : customers){
           customerDTOs.add(customerMapper.convertToDTO(customer));
       }

       return customerDTOs;
    }

    /**
     * Reset a password using any credential
     * @param passwordResetDTO keeps a credential and the password changes
     * @throws IncorrectCredentialsException if the credentials are incorrect
     */
    public void resetPassword(PasswordResetDTO passwordResetDTO){

        if(customerRepository.findByUsername(passwordResetDTO.getCredential()).isPresent()){
            Customer customer = customerRepository.findByUsername(passwordResetDTO.getCredential()).get();

            if(passwordEncoder.matches(passwordResetDTO.getOldPassword(), customer.getPassword())){
                if(passwordResetDTO.getNewPassword().length() < 8){
                    throw new PasswordTooSmallException();
                } else {
                    customer.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
                    customerRepository.save(customer);
                    emailSender.sendMail(customer.getEmail(),
                            "[Warehouse] Change of password",
                            "Dear Mr/Mrs " + customer.getLastName() +
                                    ",\n\n This email informs you that a password change has been requested recently.\n" +
                                    "For you account '" + customer.getUsername() + "' your new password is '" + passwordResetDTO.getNewPassword() + "'.\n" +
                                    "If the request was not made by you, please contact us immediately!\n\n" +
                                    "Have a nice day!\n Car Part Warehouse");
                }
            } else throw new IncorrectCredentialsException();

        } else if(customerRepository.findByEmail(passwordResetDTO.getCredential()).isPresent()){
            Customer customer = customerRepository.findByEmail(passwordResetDTO.getCredential()).get();

            if(passwordEncoder.matches(passwordResetDTO.getOldPassword(), customer.getPassword())){
                if(passwordResetDTO.getNewPassword().length() < 8){
                    throw new PasswordTooSmallException();
                } else {
                    customer.setPassword(passwordEncoder.encode(passwordResetDTO.getNewPassword()));
                    customerRepository.save(customer);
                    emailSender.sendMail(customer.getEmail(),
                            "[Warehouse] Change of password",
                            "Dear Mr/Mrs " + customer.getLastName() +
                                    ",\n\n This email informs you that a password change has been requested recently.\n" +
                                    "For you account '" + customer.getUsername() + "' your new password is '" + passwordResetDTO.getNewPassword() + "'.\n" +
                                    "If the request was not made by you, please contact us immediately!\n\n" +
                                    "Have a nice day!\n Car Part Warehouse");
                }
            } else throw new IncorrectCredentialsException();

        } else throw new IncorrectCredentialsException();

    }


}
