package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.AddressDTO;
import ro.utcn.sd.model.Address;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.repositories.AddressRepository;
import ro.utcn.sd.utils.mappers.AddressMapper;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    /**
     * Add a new address
     * @param addressDTO info about the address
     * @param customer whose address is added
     */
    public void addAddress(AddressDTO addressDTO, Customer customer){

        Address address = addressMapper.convertFromDTO(addressDTO);
        customer.setAddress(address);

        addressRepository.save(address);
    }
}
