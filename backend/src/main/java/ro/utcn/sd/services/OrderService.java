package ro.utcn.sd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.utcn.sd.dto.DateFilterDTO;
import ro.utcn.sd.dto.OrderDTO;
import ro.utcn.sd.exceptions.EmptyCartException;
import ro.utcn.sd.exceptions.IncompatibleDatesException;
import ro.utcn.sd.model.CarPart;
import ro.utcn.sd.model.Cart;
import ro.utcn.sd.model.Customer;
import ro.utcn.sd.model.Order;
import ro.utcn.sd.repositories.CartRepository;
import ro.utcn.sd.repositories.CustomerRepository;
import ro.utcn.sd.repositories.OrderRepository;
import ro.utcn.sd.utils.PDFGenerator;
import ro.utcn.sd.utils.email.EmailSender;
import ro.utcn.sd.utils.mappers.OrderMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CarPartService carPartService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private EmailSender emailSender;

    /**
     * Place an order
     * @param username of user who computes order
     */
    public void placeOrder(String username){

        Customer customer = customerRepository.findByUsername(username).get();

        Order order = new Order();
        order.setIdOrder(UUID.randomUUID().toString());
        order.setCustomer(customer);
        order.setPlacementDate(LocalDateTime.now());
        order.setTotalAmount(computePrice(customer));
        order.setDescription("");

        List<CarPart> carParts = getCurrentCarParts(customer);

        if(carParts.isEmpty())
            throw new EmptyCartException();

        for(CarPart carPart : carParts){
            Cart cart = cartRepository.findByCustomerAndCarPart(customer, carPart).get();
            carPartService.sellCarPart(carPart.getName(), cart.getQuantity());
            String prod = cart.getQuantity().toString() + "x " + carPart.getName();
            if(order.getDescription().equalsIgnoreCase("")){
                order.setDescription(prod);
            } else {
                order.setDescription(order.getDescription() + ", " + prod);
            }

        }

        orderRepository.save(order);

        generateBill(order, carParts);
    }

    /**
     * Computes the amount of bill for a user cart
     * @param customer who computes bill
     * @return the amount to be payed
     */
    public double computePrice(Customer customer) {

        double finalPrice = 0;

        for(Cart cart : cartRepository.findByCustomer(customer)){
            CarPart carPart = cart.getCarPart();
            finalPrice += cart.getQuantity() * carPart.getPrice();
        }

        return finalPrice;
    }

    /**
     * Items in cart to be bought
     * @param customer who buys
     * @return a list of items in cart
     */
    public List<CarPart> getCurrentCarParts(Customer customer){

        List<CarPart> carParts = new ArrayList<>();

        for(Cart cart : cartRepository.findByCustomer(customer)){
            CarPart carPart = cart.getCarPart();
            carParts.add(carPart);
        }

        return carParts;

    }

    /**
     * Generate a PDF bill which will be send via email
     * @param order for which we compute bill
     * @param carParts the list of items in order
     */
    public void generateBill(Order order, List<CarPart> carParts){
        String fileName = pdfGenerator.generateBill(order, carParts);

        emailSender.sendMailWithAttachment(order.getCustomer().getEmail(),
                "[Warehouse] Confirmation of order",
                "Dear Mr/Mrs " + order.getCustomer().getLastName() +
                        ",\n Attached you will find the bill confirming your recent payment.\n" +
                        "Have a nice day!",
                fileName);
    }

    /**
     * Find all orders placed by a user
     * @param username of the user
     * @return a list of orders
     */
    public List<OrderDTO> getOrdersByCustomer(String username){
        Customer customer = customerRepository.findByUsername(username).get();

        List<Order> orders = orderRepository.findByCustomer(customer);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for(Order order : orders){
            orderDTOs.add(orderMapper.convertToDTO(order));
        }

        return orderDTOs;

    }

    /**
     * Filter orders by dates
     * @param dateFilterDTO containing date intervals
     * @return a list of filtered orders
     */
    public List<OrderDTO> filterOrdersBetweenDates(DateFilterDTO dateFilterDTO){
        Customer customer = customerRepository.findByUsername(dateFilterDTO.getUsername()).get();

        LocalDateTime from = LocalDateTime.of(dateFilterDTO.getYearFrom(), dateFilterDTO.getMonthFrom(), dateFilterDTO.getDayFrom(), 0, 0);
        LocalDateTime to = LocalDateTime.of(dateFilterDTO.getYearTo(), dateFilterDTO.getMonthTo(), dateFilterDTO.getDayTo(), 0, 0);

        if(from.isAfter(to)){
            throw new IncompatibleDatesException();
        }

        List<Order> orders = orderRepository.findByCustomerAndPlacementDateAfterAndPlacementDateBefore(customer, from, to);
        List<OrderDTO> orderDTOs = new ArrayList<>();

        for(Order order : orders){
            orderDTOs.add(orderMapper.convertToDTO(order));
        }

        return orderDTOs;
    }

}
