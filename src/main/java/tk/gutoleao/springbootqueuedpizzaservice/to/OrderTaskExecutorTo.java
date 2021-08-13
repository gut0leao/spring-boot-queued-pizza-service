package tk.gutoleao.springbootqueuedpizzaservice.to;

import java.util.List;

import lombok.Data;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Data
public class OrderTaskExecutorTo {
    private List<Order> runningOrders;
    private List<Order> queuedOrders;
}
