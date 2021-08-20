package tk.gutoleao.springbootqueuedpizzaservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import tk.gutoleao.springbootqueuedpizzaservice.service.OrderThreadPoolTaskExecutorService;
import tk.gutoleao.springbootqueuedpizzaservice.to.OrderThreadPoolTaskExecutor;

@Controller
public class TaskExecutorController {

    @Autowired
    OrderThreadPoolTaskExecutorService service;

    @MessageMapping("/update")
    @SendTo("/topic/taskexecutor")
    public OrderThreadPoolTaskExecutor updateFromClient() {
        return service.getOrderThreadPoolTaskExecutor();
    }

}