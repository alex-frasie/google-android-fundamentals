package ro.utcn.sd.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.security.HttpHeadersConfiguration;
import ro.utcn.sd.dto.*;
import ro.utcn.sd.dto.responses.ResponseFactory;
import ro.utcn.sd.services.CarPartService;
import ro.utcn.sd.services.CartService;
import ro.utcn.sd.services.CustomerService;
import ro.utcn.sd.services.OrderService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private ResponseFactory responseFactory;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpHeadersConfiguration httpHeadersConfiguration;

    private static final Logger log = LoggerFactory.getLogger(CustomerController.class);


    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public CarPartsDTO seeAllProducts(){

        CarPartsDTO carPartsDTO = new CarPartsDTO();
        carPartsDTO.setCarPartDTOS(carPartService.getAllCarParts());

        log.info("All products displayed.");

        return carPartsDTO;
    }

    @GetMapping("/products/isNew={filter}")
    @ResponseStatus(HttpStatus.OK)
    public CarPartsDTO filterNew(@PathVariable boolean filter){

        CarPartsDTO carPartsDTO = new CarPartsDTO();

        if(filter) {
            log.info("Filtered new car parts.");
            carPartsDTO.setCarPartDTOS(carPartService.getNewCarParts());
        } else {
            log.info("Filtered old car parts.");
            carPartsDTO.setCarPartDTOS(carPartService.getOldCarParts());
        }

        return carPartsDTO;
    }

    @GetMapping("/product/{productName}")
    @ResponseStatus(HttpStatus.OK)
    public CarPartDTO seeDetailsCarPart(@PathVariable String productName){
        log.info("Details about product " + productName);
        return carPartService.getCarPartByName(productName);
    }

    @GetMapping("/cart")
    @ResponseStatus(HttpStatus.OK)
    public CartProductsDTO seeCart(){

        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        log.info("Cart of user " + customer.getUsername());

        CartProductsDTO cartProductsDTO = new CartProductsDTO();
        cartProductsDTO.setCarPartCartDTOs(cartService.getCarPartsByCustomer(customer.getUsername()));

        return cartProductsDTO;
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public OrdersDTO seeAllOrders(){

        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        log.info("Orders of user " + customer.getUsername());

        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrderDTOs(orderService.getOrdersByCustomer(customer.getUsername()));

        return ordersDTO;
    }

    @GetMapping("/orders/filter")
    @ResponseStatus(HttpStatus.OK)
    public OrdersDTO seeFilteredOrders(@RequestBody DateFilterDTO dateFilterDTO){
        OrdersDTO ordersDTO = new OrdersDTO();
        ordersDTO.setOrderDTOs(orderService.filterOrdersBetweenDates(dateFilterDTO));

        log.info("Orders filtered by dates.");

        return ordersDTO;
    }

    @PostMapping("/addToCart")
    public ResponseEntity addToCart(@RequestBody CarPartCartDTO carPartCartDTO){
        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        cartService.addToCart(carPartCartDTO, customer.getUsername());

        log.info(carPartCartDTO.getName() + " added to cart of " + customer.getUsername());

        return responseFactory.generateResponse("success", "Added to cart: " + carPartCartDTO.getQuantity().toString() + "x " + carPartCartDTO.getName(), HttpStatus.CREATED, httpHeadersConfiguration.getCustomerHttpHeaders());
    }

    @PostMapping("/checkout")
    public ResponseEntity computeOrder(){
        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        orderService.placeOrder(customer.getUsername());

        log.info("Compute order for " + customer.getUsername());

        return responseFactory.generateResponse("success", "Order placed.", HttpStatus.CREATED, httpHeadersConfiguration.getCustomerHttpHeaders());

    }

    @PutMapping("/updateCart")
    public ResponseEntity updateCart(@RequestBody CarPartCartDTO carPartCartDTO){
        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        cartService.updateQuantityInCart(carPartCartDTO, customer.getUsername());

        log.info("Update cart of " + customer.getUsername());

        return responseFactory.generateResponse("success", "Updated cart: " + carPartCartDTO.getQuantity().toString() + "x " + carPartCartDTO.getName(), HttpStatus.OK, httpHeadersConfiguration.getCustomerHttpHeaders());
    }

    @PostMapping("/deleteCart")
    public ResponseEntity deleteFromCart(@RequestBody CarPartCartDTO carPartCartDTO){
        Customer customer = httpHeadersConfiguration.getCustomerByToken();

        cartService.deleteFromCart(carPartCartDTO, customer.getUsername());

        log.info("Deleted " + carPartCartDTO.getName() + " from cart of " + customer.getUsername());

        return responseFactory.generateResponse("success", "Deleted: " + carPartCartDTO.getName(), HttpStatus.OK, httpHeadersConfiguration.getCustomerHttpHeaders());

    }

}
