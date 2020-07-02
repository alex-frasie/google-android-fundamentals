package ro.utcn.sd.utils.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.CarPartDTO;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.services.ProducerService;

import java.util.UUID;

@Component
public class CarPartMapper {

    @Autowired
    private ProducerService producerService;

    public CarPart convertToEntity(CarPartDTO carPartDTO){
        CarPart carPart = new CarPart();

        carPart.setIdCarPart(UUID.randomUUID().toString());
        carPart.setName(carPartDTO.getName());
        carPart.setNew(carPartDTO.isNew());
        carPart.setYear(carPartDTO.getYear());
        carPart.setDescription(carPartDTO.getDescription());
        carPart.setPrice(carPartDTO.getPrice());
        carPart.setQuantityAvailable(carPartDTO.getQuantityAvailable());
        carPart.setProducer(producerService.getProducerByName(carPartDTO.getProducerName()));

        return carPart;
    }

    public CarPartDTO convertToDTO(CarPart carPart){
        CarPartDTO carPartDTO = new CarPartDTO();

        carPartDTO.setName(carPart.getName());
        carPartDTO.setDescription(carPart.getDescription());
        carPartDTO.setNew(carPart.isNew());
        carPartDTO.setYear(carPart.getYear());
        carPartDTO.setQuantityAvailable(carPart.getQuantityAvailable());
        carPartDTO.setPrice(carPart.getPrice());
        carPartDTO.setProducerName(carPart.getProducer().getName());

        return carPartDTO;
    }
}
