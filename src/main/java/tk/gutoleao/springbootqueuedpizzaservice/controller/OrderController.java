package tk.gutoleao.springbootqueuedpizzaservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import tk.gutoleao.springbootqueuedpizzaservice.exception.InvalidOrderException;
import tk.gutoleao.springbootqueuedpizzaservice.model.Order;
import tk.gutoleao.springbootqueuedpizzaservice.service.OrderService;

@RestController
@CrossOrigin
@Slf4j
public class OrderController {

        @Autowired
        OrderService service;

        @PostMapping(path = "/order", consumes = {
                        MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Object> newOrder(@RequestBody Order order, HttpServletRequest request)
                        throws InvalidOrderException, InterruptedException {
                log.info(order.toString());
                order = service.newOrder(order);
                String orderUrl = String.format("%s://%s:%d/analise/%s", request.getScheme(), request.getServerName(),
                                request.getServerPort(), order.getId());
                return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, orderUrl).body(order);
        }

        @GetMapping("/orders")
        public List<Order> findAllOrders() {
                return service.findAllOrders();
        }

}
