package tk.gutoleao.springbootqueuedpizzaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import tk.gutoleao.springbootqueuedpizzaservice.model.Order;
import tk.gutoleao.springbootqueuedpizzaservice.model.OrderHistory;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long>, JpaSpecificationExecutor<Order> {

}
