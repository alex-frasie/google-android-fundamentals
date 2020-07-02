package ro.utcn.sd.utils.mappers;

import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.AddressDTO;
import ro.utcn.sd.model.Address;

import java.util.UUID;

@Component
public class AddressMapper {

    public Address convertFromDTO(AddressDTO addressDTO){
        Address address = new Address();

        address.setCity(addressDTO.getCity());
        address.setCounty(addressDTO.getCounty());
        address.setIdAddress(UUID.randomUUID().toString());
        address.setNumber(addressDTO.getNumber());
        address.setStreet(addressDTO.getStreet());
        address.setPostalCode(addressDTO.getPostalCode());

        return address;
    }

    public AddressDTO convertToDTO(Address address){
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCity(address.getCity());
        addressDTO.setCounty(address.getCounty());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setStreet(address.getStreet());

        return addressDTO;
    }
}
