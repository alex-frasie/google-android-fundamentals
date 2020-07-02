package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.CarPartCartDTO;
import ro.utcn.sd.exceptions.InexistentProductException;
import ro.utcn.sd.exceptions.NotEnoughProductsException;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.model.Cart;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.repositories.CarPartRepository;
import ro.utcn.sd.repositories.CartRepository;
import ro.utcn.sd.repositories.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarPartRepository carPartRepository;

    /**
     * See products in cart for a specific user
     * @param username of the user
     * @return list of the products
     */
    public List<Cart> getCartsByCustomer(String username) {

        if(customerRepository.findByUsername(username).isPresent()){
            Customer customer = customerRepository.findByUsername(username).get();

            return cartRepository.findByCustomer(customer);
        }

        return null;
    }

    /**
     * Get all products wanted by a customer
     * @param username of the customer
     * @return a list of these car parts
     */
    public List<CarPartCartDTO> getCarPartsByCustomer(String username){
        Customer customer = customerRepository.findByUsername(username).get();

        List<CarPartCartDTO> carPartCartDTOs = new ArrayList<>();

        for(Cart cart : cartRepository.findByCustomer(customer)){
            CarPartCartDTO carPartCartDTO = new CarPartCartDTO();

            carPartCartDTO.setName(cart.getCarPart().getName());
            carPartCartDTO.setPrice(cart.getCarPart().getPrice());
            carPartCartDTO.setQuantity(cart.getQuantity());

            carPartCartDTOs.add(carPartCartDTO);
        }

        return carPartCartDTOs;
    }

    /**
     * Add a car part to cart
     * @param carPartCartDTO to be added to cart
     * @param username of user who adds to cart
     * @throws NotEnoughProductsException if there are no enough products
     */
    public void addToCart(CarPartCartDTO carPartCartDTO, String username){

        Customer customer = customerRepository.findByUsername(username).get();
        CarPart carPart = carPartRepository.findByName(carPartCartDTO.getName()).get();

        System.out.println(carPart);
        System.out.println(customer);

        if (cartRepository.findByCustomerAndCarPart(customer, carPart).isPresent()) {
            Cart cart = cartRepository.findByCustomerAndCarPart(customer, carPart).get();
            if(carPart.getQuantityAvailable() >= cart.getQuantity() + carPartCartDTO.getQuantity()) {
                cart.setQuantity(cart.getQuantity() + carPartCartDTO.getQuantity());
                cartRepository.save(cart);
            } else throw new NotEnoughProductsException();
        } else {

            if (carPart.getQuantityAvailable() >= carPartCartDTO.getQuantity()) {
                Cart cart = new Cart();

                cart.setIdCart(UUID.randomUUID().toString());
                cart.setCustomer(customer);
                cart.setCarPart(carPart);
                cart.setQuantity(carPartCartDTO.getQuantity());

                cartRepository.save(cart);
            } else throw new NotEnoughProductsException();
        }

    }

    /**
     * Update quantity of a car part in cart
     * @param carPartCartDTO from cart
     * @param username whose cart is updated
     */
    public void updateQuantityInCart(CarPartCartDTO carPartCartDTO, String username){

        CarPart carPart = carPartRepository.findByName(carPartCartDTO.getName()).get();

        if(carPart.getQuantityAvailable() >= carPartCartDTO.getQuantity()) {
            for (Cart cart : getCartsByCustomer(username)) {
                if (cart.getCarPart().getIdCarPart() == carPart.getIdCarPart()) {
                    cart.setQuantity(carPartCartDTO.getQuantity());
                    cartRepository.save(cart);
                }
            }
        } else throw new NotEnoughProductsException();

    }

    /**
     * Remove an element from cart
     * @param carPartCartDTO the element to be removed
     * @param username who removes
     */
    public void deleteFromCart(CarPartCartDTO carPartCartDTO, String username){

        Customer customer = customerRepository.findByUsername(username).get();
        CarPart carPart = carPartRepository.findByName(carPartCartDTO.getName()).get();

        if(cartRepository.findByCustomerAndCarPart(customer, carPart).isPresent()) {
            Cart cart = cartRepository.findByCustomerAndCarPart(customer, carPart).get();

            cartRepository.delete(cart);
        } else throw new InexistentProductException();
    }
}
