package ro.utcn.sd.utils;

import org.springframework.stereotype.Component;
import ro.utcn.sd.model.Address;
import ro.utcn.sd.model.Customer;

import java.util.UUID;

@Component
public class CustomerBuilder {

    protected Customer customer = new Customer();

    /**
     * Builds the user when ready
     * @return the newly formed user
     */
    public Customer buildUser(){
        id();
        return customer;
    }

    /**
     * Build the random generated id
     */
    public CustomerBuilder id(){
        customer.setIdCustomer(UUID.randomUUID().toString());
        return this;
    }

    /**
     * Add the first name attribute
     */
    public CustomerBuilder firstName(String firstName){
        customer.setFirstName(firstName);
        return this;
    }

    /**
     * Add the last name attribute
     */
    public CustomerBuilder lastName(String lastName){
        customer.setLastName(lastName);
        return this;
    }

    /**
     * Add the password attribute
     */
    public CustomerBuilder password(String password){
        customer.setPassword(password);
        return this;
    }

    /**
     * Add the email attribute
     */
    public CustomerBuilder email(String email){
        customer.setEmail(email);
        return this;
    }

    /**
     * Add the username attribute
     */
    public CustomerBuilder username(String username){
        customer.setUsername(username);
        return this;
    }

    /**
     * Add the address attribute
     */
    public CustomerBuilder address(Address address){
        customer.setAddress(address);
        return this;
    }

}
