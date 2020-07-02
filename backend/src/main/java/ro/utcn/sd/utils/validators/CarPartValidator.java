package ro.utcn.sd.utils.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.CarPartDTO;
import ro.utcn.sd.dto.ProducerDTO;
import ro.utcn.sd.exceptions.InvalidProductException;
import ro.utcn.sd.model.Producer;
import ro.utcn.sd.services.ProducerService;

import java.util.List;

@Component
public class CarPartValidator {

    @Autowired
    private ProducerService producerService;

    public boolean isValid(CarPartDTO carPartDTO){
        if(carPartDTO.getPrice() <= 0.0)
            return false;
        if(carPartDTO.getQuantityAvailable() <= 0)
            return false;

        List<ProducerDTO> producers = producerService.getAllProducers();

        boolean exists = false;
        for(ProducerDTO producerDTO : producers){
            if(producerDTO.getName().equalsIgnoreCase(carPartDTO.getProducerName()))
                exists = true;
        }

        if(!exists) throw new InvalidProductException();

        return true;
    }
}
