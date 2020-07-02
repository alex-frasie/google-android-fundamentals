package ro.utcn.sd.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.utcn.sd.dto.PasswordResetDTO;
import ro.utcn.sd.security.HttpHeadersConfiguration;
import ro.utcn.sd.dto.responses.ResponseFactory;
import ro.utcn.sd.dto.LoginDTO;
import ro.utcn.sd.dto.RegisterDTO;
import ro.utcn.sd.model.Admin;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.services.AdminService;
import ro.utcn.sd.services.CustomerService;

@RestController
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private HttpHeadersConfiguration httpHeadersConfiguration;

    private HttpHeaders httpHeaders = new HttpHeaders();

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/loginAdmin")
    public ResponseEntity loginAdmin(@RequestBody LoginDTO adminDTO){
        Admin admin = adminService.loginAdmin(adminDTO.getCredential(), adminDTO.getPassword());

        if(admin == null){
            log.error("Invalid login for admin!");
            return responseFactory.generateResponse("error", "Invalid credentials", HttpStatus.NOT_FOUND, null);
        }

        httpHeadersConfiguration.generateTokenAdmin(admin.getUsername());

        log.info("Admin successfully logged in!");
        return responseFactory.generateResponse("success", "Logged in!", HttpStatus.OK, httpHeadersConfiguration.getAdminHttpHeaders());
    }

    @PostMapping("/loginCustomer")
    public ResponseEntity loginCustomer(@RequestBody LoginDTO loginDTO){
        Customer customer = customerService.loginCustomer(loginDTO.getCredential(), loginDTO.getPassword());

        httpHeadersConfiguration.generateTokenCustomer(customer.getUsername());

        log.info("Customer " + customer.getUsername() + " successfully logged in!");

        return responseFactory.generateResponse("success", "Logged in!", HttpStatus.OK, httpHeadersConfiguration.getCustomerHttpHeaders());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO){
        Customer customer = customerService.registerNewCustomer(registerDTO);

        if(customer == null){
            log.error("Could not make a new account!");
            return responseFactory.generateResponse("error", "Could not register!", HttpStatus.BAD_REQUEST, null);
        }

        log.info("New account created for " + customer.getUsername());
        return responseFactory.generateResponse("success", "Registered!", HttpStatus.CREATED, httpHeaders);
    }

    @GetMapping("/accessDenied")
    public ResponseEntity accessDenied(){
        log.warn("Access denied!");
        return responseFactory.generateResponse("error", "Access Denied!", HttpStatus.UNAUTHORIZED, null);
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@RequestBody PasswordResetDTO passwordResetDTO){

        customerService.resetPassword(passwordResetDTO);

        log.info("Password successfully changed!");
        return responseFactory.generateResponse("success", "Password changed!", HttpStatus.OK, null);
    }

}
