package tk.gutoleao.springbootqueuedpizzaservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tk.gutoleao.springbootqueuedpizzaservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o WHERE o.id = :id")
    Order getOrderById(@Param("id") Long id);

}
