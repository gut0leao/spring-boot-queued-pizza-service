package tk.gutoleao.springbootqueuedpizzaservice.service;

import org.springframework.scheduling.annotation.Async;

import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

public class AsyncOrderService {

    @Async("newOrderTaskExecutor")
    public void newOrder(Order order) {
    }

}
