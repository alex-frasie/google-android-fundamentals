package ro.utcn.sd.utils.mappers;

import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.ProducerDTO;
import ro.utcn.sd.model.Producer;

import java.util.UUID;

@Component
public class ProducerMapper {

    public Producer convertToEntity(ProducerDTO producerDTO){
        Producer producer = new Producer();

        producer.setIdProducer(UUID.randomUUID().toString());
        producer.setCountry(producerDTO.getCountry());
        producer.setStartingYear(producerDTO.getStartingYear());
        producer.setName(producerDTO.getName());

        return producer;
    }

    public ProducerDTO convertToDTO(Producer producer){
        ProducerDTO producerDTO = new ProducerDTO();

        producerDTO.setCountry(producer.getCountry());
        producerDTO.setName(producer.getName());
        producerDTO.setStartingYear(producer.getStartingYear());

        return producerDTO;
    }
}
