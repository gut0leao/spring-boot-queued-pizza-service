package tk.gutoleao.springbootqueuedpizzaservice.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import tk.gutoleao.springbootqueuedpizzaservice.enums.EnumOrderStatus;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Service
public class AsyncOrderService {

    @Autowired
    private OrderService orderService;

    @Autowired
    OrderThreadPoolTaskExecutorService threadPoolTaskExecutorService;

    @Async("orderTaskExecutor")
    public CompletableFuture<Order> processar(Order order) throws InterruptedException {

        threadPoolTaskExecutorService.orderProcessingStarted(Thread.currentThread().getName(), order);
        orderService.updateHistory(order, EnumOrderStatus.PREPARING);
        Thread.sleep(10000);
        orderService.updateHistory(order, EnumOrderStatus.DELIVERING);
        Thread.sleep(10000);
        orderService.updateHistory(order, EnumOrderStatus.DELIVERED);
        threadPoolTaskExecutorService.orderProcessingEnded(Thread.currentThread().getName());

        return CompletableFuture.completedFuture(order);
    }

}
