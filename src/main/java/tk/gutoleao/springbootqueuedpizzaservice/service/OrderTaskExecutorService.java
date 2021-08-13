package tk.gutoleao.springbootqueuedpizzaservice.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Service
@Slf4j
public class OrderTaskExecutorService {

    @Autowired
    ThreadPoolTaskExecutor executor;

    private Map<String, Order> runningMap = new HashMap<>();
    private Set<Order> queue = new HashSet<>();

    public void addToRunningMap(String thread, Order order) {
        queue.remove(order);
        runningMap.put(thread, order);
        update();
    }

    public void removeFromRunningMap(String thread, Order order) {
        if (runningMap.get(thread).equals(order))
            runningMap.put(thread, null);
        update();
    }

    public void update() {
        StringBuilder sb = new StringBuilder();
        sb.append("Running: \n");
        for (Entry<String, Order> entry : runningMap.entrySet()) {
            sb.append(String.format("    %s : %s  %n", entry.getKey(), runningMap.get(entry.getKey())));
        }
        sb.append("\n");
        sb.append("Queued: \n");
        for (Order o : queue) {
            sb.append(String.format("    %s %n", o));
        }
        log.info(sb.toString());
    }

    public void addToQueue(Order order) {
        queue.add(order);
        update();
    }

}
