package ro.utcn.sd.utils.mappers;

import org.springframework.stereotype.Component;
import ro.utcn.sd.dto.OrderDTO;
import ro.utcn.sd.model.Order;

@Component
public class OrderMapper {

    public OrderDTO convertToDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setPlacementDate(order.getPlacementDate().toString().substring(0, 10));
        orderDTO.setCustomerName(order.getCustomer().getLastName() + " " + order.getCustomer().getFirstName());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setDescription(order.getDescription());

        return orderDTO;
    }
}
