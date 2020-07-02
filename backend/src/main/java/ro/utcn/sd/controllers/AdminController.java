package ro.utcn.sd.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.security.HttpHeadersConfiguration;
import ro.utcn.sd.dto.*;
import ro.utcn.sd.dto.responses.ResponseFactory;
import ro.utcn.sd.services.CarPartService;
import ro.utcn.sd.services.CustomerService;
import ro.utcn.sd.services.ProducerService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private ProducerService producerService;

    @Autowired
    private HttpHeadersConfiguration httpHeadersConfiguration;

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerDTO> seeAllCustomers(){
        log.info("All customers displayed.");
        return customerService.getAllCustomers();
    }


    @GetMapping(path = "/products")
    @ResponseStatus(HttpStatus.OK)
    public CarPartsDTO seeAllProducts(){

        CarPartsDTO carPartsDTO = new CarPartsDTO();
        carPartsDTO.setCarPartDTOS(carPartService.getAllCarParts());

        log.info("All products displayed.");

        return carPartsDTO;
    }

    @GetMapping("/producers")
    @ResponseStatus(HttpStatus.OK)
    public ProducersDTO seeAllProducers(){

        ProducersDTO producersDTO = new ProducersDTO();
        producersDTO.setProducerDTOs(producerService.getAllProducers());

        log.info("All producers displayed.");

        return producersDTO;
    }

    @GetMapping("/product/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public CarPartDTO seeDetailsCarPart(@PathVariable String productName){

        log.info("Details for " + productName + " are displayed.");
        return carPartService.getCarPartByName(productName);
    }



    @PostMapping("/product/add")
    public ResponseEntity addCarPart(@RequestBody CarPartDTO carPartDTO){
        carPartService.addCarPart(carPartDTO);

        log.info("Added car part " + carPartDTO.getName());
        return responseFactory.generateResponse("success", "Added: " + carPartDTO.getName(), HttpStatus.CREATED, httpHeadersConfiguration.getAdminHttpHeaders());
    }

    @PostMapping("/producer/add")
    public ResponseEntity addProducer(@RequestBody ProducerDTO producerDTO){
        producerService.addProducer(producerDTO);

        log.info("Added producer " + producerDTO.getName());
        return responseFactory.generateResponse("success", "Added: " + producerDTO.getName(), HttpStatus.CREATED, httpHeadersConfiguration.getAdminHttpHeaders());
    }



    @PostMapping("/product/update")
    public ResponseEntity updateCarPart(@RequestBody CarPartDTO carPartDTO){
        carPartService.updateCarPart(carPartDTO);

        log.info("Updated car part" + carPartDTO.getName());
        return responseFactory.generateResponse("success", "Updated: " + carPartDTO.getName(), HttpStatus.ACCEPTED, httpHeadersConfiguration.getAdminHttpHeaders());
    }



    @PostMapping("/product/delete")
    public ResponseEntity deleteCarPart(@RequestBody DeleteProdDTO deleteProdDTO){
        carPartService.deleteCarPart(deleteProdDTO.getProductName());

        log.info("Deleted car part " + deleteProdDTO.getProductName());
        return responseFactory.generateResponse("success", "Deleted: " + deleteProdDTO.getProductName(), HttpStatus.GONE, httpHeadersConfiguration.getAdminHttpHeaders());
    }

}
