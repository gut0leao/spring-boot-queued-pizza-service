package tk.gutoleao.springbootqueuedpizzaservice.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.enums.EnumOrderStatus;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Service
@Slf4j
public class AsyncOrderService {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderTaskExecutorService orderTaskExecutorService;

    @Async("orderTaskExecutor")
    public CompletableFuture<Order> processar(Order order) throws InterruptedException {
        orderTaskExecutorService.addToRunningMap(Thread.currentThread().getName(), order);
        service.updateHistory(order, EnumOrderStatus.PREPARING);
        Thread.sleep(10000);
        service.updateHistory(order, EnumOrderStatus.DELIVERING);
        Thread.sleep(10000);
        service.updateHistory(order, EnumOrderStatus.DELIVERED);
        orderTaskExecutorService.removeFromRunningMap(Thread.currentThread().getName(), order);

        return CompletableFuture.completedFuture(order);
    }

}
