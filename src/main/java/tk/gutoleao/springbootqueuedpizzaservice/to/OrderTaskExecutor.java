package tk.gutoleao.springbootqueuedpizzaservice.to;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class OrderTaskExecutor {

    private String threadName;
    private String threadState;
    private Order order;

}
