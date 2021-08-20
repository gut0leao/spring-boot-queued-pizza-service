package tk.gutoleao.springbootqueuedpizzaservice.service;

import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import tk.gutoleao.springbootqueuedpizzaservice.model.Order;
import tk.gutoleao.springbootqueuedpizzaservice.to.OrderTaskExecutor;
import tk.gutoleao.springbootqueuedpizzaservice.to.OrderThreadPoolTaskExecutor;

@Service
public class OrderThreadPoolTaskExecutorService {

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private OrderThreadPoolTaskExecutor orderThreadPoolTaskExecutor = new OrderThreadPoolTaskExecutor();

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public OrderThreadPoolTaskExecutor getOrderThreadPoolTaskExecutor() {
        return this.orderThreadPoolTaskExecutor;
    }

    @PostConstruct
    private void updateExecutorData() {
        orderThreadPoolTaskExecutor.setCorePoolSize(threadPoolTaskExecutor.getCorePoolSize());
        orderThreadPoolTaskExecutor.setMaxPoolSize(threadPoolTaskExecutor.getMaxPoolSize());
        orderThreadPoolTaskExecutor.setKeepAliveSeconds(threadPoolTaskExecutor.getKeepAliveSeconds());
        orderThreadPoolTaskExecutor.setActiveCount(threadPoolTaskExecutor.getActiveCount());
    }

    public void orderProcessingStarted(String thread, Order order) {
        orderThreadPoolTaskExecutor.getQueue().remove(order);
        updateThreadMap();
        orderThreadPoolTaskExecutor.getThreadMap().get(thread).setOrder(order);
        update();
    }

    private void updateThreadMap() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.getName().startsWith("orderTaskExecutor-thread-")) {
                Order o = null;
                if (orderThreadPoolTaskExecutor.getThreadMap().containsKey(thread.getName())) {
                    o = orderThreadPoolTaskExecutor.getThreadMap().get(thread.getName()).getOrder();
                }
                OrderTaskExecutor taskExecutor = new OrderTaskExecutor(thread.getName(), thread.getState().toString(),
                        o);
                orderThreadPoolTaskExecutor.getThreadMap().put(thread.getName(), taskExecutor);
            }
        }
    }

    public void orderProcessingEnded(String thread) {
        orderThreadPoolTaskExecutor.getThreadMap().get(thread).setOrder(null);
        update();
    }

    public void addToQueue(Order order) {
        orderThreadPoolTaskExecutor.getQueue().add(order);
        update();
    }

    @Scheduled(fixedDelay = 3000)
    public void update() {
        orderThreadPoolTaskExecutor.setActiveCount(threadPoolTaskExecutor.getActiveCount());
        updateThreadMap();
        simpMessagingTemplate.convertAndSend("/topic/taskexecutor", orderThreadPoolTaskExecutor);
    }

}
