package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.ProducerDTO;
import ro.utcn.sd.exceptions.ProducerAlreadyExistsException;
import ro.utcn.sd.model.Producer;
import ro.utcn.sd.repositories.ProducerRepository;
import ro.utcn.sd.utils.mappers.ProducerMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository producerRepository;

    @Autowired
    private ProducerMapper producerMapper;

    /**
     * Find producer by name
     * @param name of producer to be found
     * @return the producer if found, null otherwise
     */
    public Producer getProducerByName(String name){

        if(producerRepository.findByName(name).isPresent()){
            return producerRepository.findByName(name).get();
        } else return null;
    }

    /**
     * Get all producers
     * @return a list of all producers
     */
    public List<ProducerDTO> getAllProducers(){
        List<Producer> producers = producerRepository.findAll();
        List<ProducerDTO> producerDTOs = new ArrayList<>();

        for(Producer producer : producers){
            producerDTOs.add(producerMapper.convertToDTO(producer));
        }

        return producerDTOs;
    }

    /**
     * Add a new producer
     * @param producerDTO to be added
     * @throws ProducerAlreadyExistsException if it already exists
     */
    public void addProducer(ProducerDTO producerDTO){
        List<Producer> producers = producerRepository.findAll();

        boolean isExistent = false;
        for(Producer producer : producers){
            if(producer.getName().equalsIgnoreCase(producerDTO.getName())){
                isExistent = true;
                break;
            }
        }

        if(!isExistent) {
            Producer producer = producerMapper.convertToEntity(producerDTO);
            producerRepository.save(producer);
        } else throw new ProducerAlreadyExistsException();

    }
}
