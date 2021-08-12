package tk.gutoleao.springbootqueuedpizzaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;
import tk.gutoleao.springbootqueuedpizzaservice.repository.OrderRepository;
import tk.gutoleao.springbootqueuedpizzaservice.validator.OrderValidator;

@Service
public class OrderService {

    @Autowired
    private OrderValidator validator;

    @Autowired
    private OrderRepository repository;

    @Autowired
    private AsyncOrderService async;

    public Order newOrder(Order order) throws InvalidOrderException {

        validator.validate(order);

        order = repository.save(order);

        async.newOrder(order);

        return order;
    }

}
