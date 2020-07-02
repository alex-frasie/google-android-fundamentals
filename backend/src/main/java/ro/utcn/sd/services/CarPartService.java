package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.CarPartDTO;
import ro.utcn.sd.exceptions.InexistentProductException;
import ro.utcn.sd.exceptions.InvalidProductException;
import ro.utcn.sd.exceptions.NotEnoughProductsException;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.repositories.CarPartRepository;
import ro.utcn.sd.utils.CarPartBuilder;
import ro.utcn.sd.utils.mappers.CarPartMapper;
import ro.utcn.sd.utils.validators.CarPartValidator;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarPartService {

    @Autowired
    private CarPartRepository carPartRepository;

    @Autowired
    private CarPartBuilder builder;

    @Autowired
    private CarPartMapper carPartMapper;

    @Autowired
    private CarPartValidator carPartValidator;

    @Autowired
    private ProducerService producerService;

    /**
     * Add a new car part to the storage
     * @param carPartDTO to be mapped and added
     */
    public void addCarPart(CarPartDTO carPartDTO){

        if(!carPartValidator.isValid(carPartDTO))
            throw new InvalidProductException();

        CarPart carPart = builder.name(carPartDTO.getName())
                .description(carPartDTO.getDescription())
                .isNew(carPartDTO.isNew())
                .price(carPartDTO.getPrice())
                .quantity(carPartDTO.getQuantityAvailable())
                .year(carPartDTO.getYear())
                .producer(producerService.getProducerByName(carPartDTO.getProducerName()))
                .buildCarPart();

        carPartRepository.save(carPart);
    }

    /**
     * Update info about a car part
     * @param carPartDTO to be updated
     */
    public void updateCarPart(CarPartDTO carPartDTO){

        if(carPartRepository.findByName(carPartDTO.getName()).isPresent()) {
            CarPart carPart = carPartRepository.findByName(carPartDTO.getName()).get();

            carPart.setQuantityAvailable(carPartDTO.getQuantityAvailable());
            carPart.setPrice(carPartDTO.getPrice());
            carPart.setDescription(carPartDTO.getDescription());
            carPart.setYear(carPartDTO.getYear());

            carPartRepository.save(carPart);
        } else throw new InexistentProductException();
    }

    /**
     * Get details about a specific car part
     * @param name of the car part
     * @return a details car part
     */
    public CarPartDTO getCarPartByName(String name){

        if(carPartRepository.findByName(name).isPresent()) {
            CarPart carPart = carPartRepository.findByName(name).get();
            return carPartMapper.convertToDTO(carPart);
        }
        throw new InexistentProductException();
    }

    /**
     * When computing an order, update/delete those items from the database also
     * @param carPartName which is sold
     * @param quantitySold is the quantity sold
     * @throws InexistentProductException if the name of product is not found
     */
    public void sellCarPart(String carPartName, Integer quantitySold){

        if(carPartRepository.findByName(carPartName).isPresent()) {
            CarPart carPart = carPartRepository.findByName(carPartName).get();

            if (carPart.getQuantityAvailable() >= quantitySold) {
                carPart.setQuantityAvailable(carPart.getQuantityAvailable() - quantitySold);
                carPartRepository.save(carPart);
            } else throw new NotEnoughProductsException();
        } else throw new InexistentProductException();

    }

    /**
     * Delete a car part from storage
     * @param carPartName to be deleted
     * @throws InexistentProductException if name is not found
     */
    public void deleteCarPart(String carPartName){

        if(carPartRepository.findByName(carPartName).isPresent()) {
            CarPart carPart = carPartRepository.findByName(carPartName).get();
            carPartRepository.delete(carPart);
        } else throw new InexistentProductException();
    }

    /**
     * Filter car parts by new car parts.
     * @return a list of new car parts
     */
    public List<CarPartDTO> getNewCarParts() {
        List<CarPart> carParts = carPartRepository.findNewCarParts();
        List<CarPartDTO> carPartDTOs = new ArrayList<>();

        for(CarPart carPart : carParts){
            carPartDTOs.add(carPartMapper.convertToDTO(carPart));
        }

        return carPartDTOs;
    }

    /**
     * Filter car parts by old car parts.
     * @return a list of old car parts
     */
    public List<CarPartDTO> getOldCarParts() {
        List<CarPart> carParts = carPartRepository.findOldCarParts();
        List<CarPartDTO> carPartDTOs = new ArrayList<>();

        for(CarPart carPart : carParts){
            carPartDTOs.add(carPartMapper.convertToDTO(carPart));
        }

        return carPartDTOs;
    }

    /**
     * Get all car parts from storage
     * @return a list of all car parts existent
     */
    public List<CarPartDTO> getAllCarParts() {
        List<CarPart> carParts = carPartRepository.findAll();
        List<CarPartDTO> carPartDTOs = new ArrayList<>();

        for(CarPart carPart : carParts){
            carPartDTOs.add(carPartMapper.convertToDTO(carPart));
        }

        return carPartDTOs;
    }

}
