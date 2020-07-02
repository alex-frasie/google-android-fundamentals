package ro.utcn.sd.utils;

import org.springframework.stereotype.Component;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.model.Producer;

import java.util.UUID;

@Component
public class CarPartBuilder {

    protected CarPart carPart = new CarPart();

    /**
     * Builds the car part when ready
     * @return the newly formed car part
     */
    public CarPart buildCarPart(){
        id();
        return carPart;
    }

    /**
     * Build the random generated id
     */
    public CarPartBuilder id(){
        carPart.setIdCarPart(UUID.randomUUID().toString());
        return this;
    }

    /**
     * Add the name attribute
     */
    public CarPartBuilder name(String name){
        carPart.setName(name);
        return this;
    }

    /**
     * Add the is new attribute
     */
    public CarPartBuilder isNew(boolean isNew){
        carPart.setNew(isNew);
        return this;
    }

    /**
     * Add the year attribute
     */
    public CarPartBuilder year(Integer year){
        carPart.setYear(year);
        return this;
    }

    /**
     * Add the description attribute
     */
    public CarPartBuilder description(String description){
        carPart.setDescription(description);
        return this;
    }

    /**
     * Add the price attribute
     */
    public CarPartBuilder price(double price){
        carPart.setPrice(price);
        return this;
    }

    /**
     * Add the quantity attribute
     */
    public CarPartBuilder quantity(Integer quantityAvailable){
        carPart.setQuantityAvailable(quantityAvailable);
        return this;
    }

    /**
     * Add the producer attribute
     */
    public CarPartBuilder producer(Producer producer){
        carPart.setProducer(producer);
        return this;
    }

}
