package ro.utcn.sd.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import ro.utcn.sd.model.Admin;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.repositories.AdminRepository;
import ro.utcn.sd.repositories.CustomerRepository;

import java.util.Date;

@Component
public class HttpHeadersConfiguration {

    private HttpHeaders adminHttpHeaders = new HttpHeaders();
    private HttpHeaders customerHttpHeaders = new HttpHeaders();

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Obtain a JWT token by encoding information about an administrator
     * @param username of the administrator
     */
    public void generateTokenAdmin(String username){
        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                .sign(Algorithm.HMAC256("SecurityToken"));

        adminHttpHeaders.set("Authentication", token);
    }

    /**
     * Obtain a JWT token by encoding information about a customer
     * @param username of the customer
     */
    public void generateTokenCustomer(String username){

        Customer customer = customerRepository.findByUsername(username).get();

        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject("ROLE_CUSTOMER " + username)
                .withClaim("firstName", customer.getFirstName())
                .withClaim("lastName", customer.getLastName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 864_000_000))
                .sign(Algorithm.HMAC256("SecurityToken"));

        customerHttpHeaders.set("Authentication", token);
    }

    /**
     * Obtain an administrator of a JWT token
     */
    public Admin getAdminByToken() {
        Algorithm algorithm = Algorithm.HMAC256("SecurityToken");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = verifier.verify(adminHttpHeaders.getValuesAsList("Authentication").get(0));
        String subject = jwt.getSubject();
        Admin admin = adminRepository.findByUsername(subject).get();
        return admin;
    }

    /**
     * Obtain a customer of a JWT token
     */
    public Customer getCustomerByToken() {
        Algorithm algorithm = Algorithm.HMAC256("SecurityToken");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        DecodedJWT jwt = verifier.verify(customerHttpHeaders.getValuesAsList("Authentication").get(0));
        String subject = jwt.getSubject();

        Customer customer = customerRepository.findByUsername(subject.split(" ")[1]).get();
        return customer;
    }

    public HttpHeaders getAdminHttpHeaders() {
        return adminHttpHeaders;
    }

    public HttpHeaders getCustomerHttpHeaders() {
        return customerHttpHeaders;
    }
}
