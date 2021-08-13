package tk.gutoleao.springbootqueuedpizzaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.model.OrderHistory;
import tk.gutoleao.springbootqueuedpizzaservice.repository.OrderHistoryRepository;

@Service
@Slf4j
public class OrderHistoryService {

    @Autowired
    private OrderHistoryRepository repository;

    public OrderHistory newOrderHistory(OrderHistory orderHistory) {
        orderHistory = repository.save(orderHistory);
        return orderHistory;
    }
}
