package tk.gutoleao.springbootqueuedpizzaservice.validator;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

@Component
@Slf4j
public class OrderValidator {

    private static final String MESSAGE_INVALID_DESCRITION = "Invalid description.";

    public void validate(Order order) throws InvalidOrderException {

        log.info("validating order...");

        if (order.getDescription() == null || order.getDescription().isEmpty())
            throw new InvalidOrderException(MESSAGE_INVALID_DESCRITION);

        log.info("Order is valid.");

    }
}
