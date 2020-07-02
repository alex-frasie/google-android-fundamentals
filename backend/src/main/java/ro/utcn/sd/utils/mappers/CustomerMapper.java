package ro.utcn.sd.utils.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.AddressDTO;
import ro.utcn.sd.dto.CustomerDTO;
import ro.utcn.sd.dto.RegisterDTO;
import ro.utcn.sd.model.Customer;

@Component
public class CustomerMapper {

    @Autowired
    private AddressMapper addressMapper;

    public CustomerDTO convertToDTO(Customer customer){
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setUsername(customer.getUsername());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setAddressDTO(addressMapper.convertToDTO(customer.getAddress()));

        return customerDTO;
    }

    public CustomerDTO convertToDTO(RegisterDTO registerDTO){
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setUsername(registerDTO.getUsername());
        customerDTO.setEmail(registerDTO.getEmail());
        customerDTO.setPassword(registerDTO.getPassword());
        customerDTO.setFirstName(registerDTO.getFirstName());
        customerDTO.setLastName(registerDTO.getLastName());

        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(registerDTO.getStreet());
        addressDTO.setPostalCode(registerDTO.getPostalCode());
        addressDTO.setNumber(registerDTO.getNumber());
        addressDTO.setCounty(registerDTO.getCounty());
        addressDTO.setCity(registerDTO.getCity());

        customerDTO.setAddressDTO(addressDTO);

        return customerDTO;
    }
}
