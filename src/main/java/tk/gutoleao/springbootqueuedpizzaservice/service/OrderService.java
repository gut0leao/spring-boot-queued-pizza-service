package tk.gutoleao.springbootqueuedpizzaservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.enums.EnumOrderStatus;
import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.exeption.ResourceNotFoundException;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;
import tk.gutoleao.springbootqueuedpizzaservice.model.OrderHistory;
import tk.gutoleao.springbootqueuedpizzaservice.repository.OrderRepository;
import tk.gutoleao.springbootqueuedpizzaservice.validator.OrderValidator;

@Service
@Slf4j
@Data
public class OrderService {

    @Autowired
    private OrderValidator validator;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderHistoryService historyService;

    @Autowired
    private AsyncOrderService async;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    OrderThreadPoolTaskExecutorService threadPoolTaskExecutorService;

    public Order newOrder(Order order) throws InvalidOrderException, InterruptedException {

        validator.validate(order);

        order.setRequestedIn(LocalDateTime.now());

        addHistory(order, EnumOrderStatus.AWAITING);

        repository.save(order);

        threadPoolTaskExecutorService.addToQueue(order);

        async.processar(order);

        return order;
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order getOrderById(Long id) throws ResourceNotFoundException {
        Order order = repository.getOrderById(id);
        if (order == null)
            throw new ResourceNotFoundException();
        return order;
    }

    // without persist
    public void addHistory(Order order, EnumOrderStatus newStatus) {
        LocalDateTime now = LocalDateTime.now();
        OrderHistory history = new OrderHistory(newStatus, now, order);
        order.getHistory().add(history);
        order.setStatus(newStatus);
        order.setLastUpdate(now);
    }

    public void updateHistory(Order order, EnumOrderStatus newStatus) {
        LocalDateTime now = LocalDateTime.now();
        OrderHistory orderHistory = new OrderHistory(newStatus, now, order);
        historyService.newOrderHistory(orderHistory);
        order.setStatus(newStatus);
        order.setLastUpdate(now);
        repository.save(order);
        threadPoolTaskExecutorService.update();
    }

}
