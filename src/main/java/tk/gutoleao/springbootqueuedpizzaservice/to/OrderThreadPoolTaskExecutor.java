package tk.gutoleao.springbootqueuedpizzaservice.to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderThreadPoolTaskExecutor {

    private int corePoolSize;
    private int maxPoolSize;
    private int activeCount;
    private int keepAliveSeconds;
    private Map<String, OrderTaskExecutor> threadMap = new HashMap<>();
    private List<Order> queue = new ArrayList<>();

}
