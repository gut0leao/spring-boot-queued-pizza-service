package tk.gutoleao.springbootqueuedpizzaservice.validator;

import org.springframework.stereotype.Component;

import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Component
public class OrderValidator {

    private static final String MESSAGE_INVALID_DESCRITION = "Invalid description.";

    public void validate(Order order) throws InvalidOrderException {

        if (order.getDescription() == null || order.getDescription().isEmpty())
            throw new InvalidOrderException(MESSAGE_INVALID_DESCRITION);

    }
}
