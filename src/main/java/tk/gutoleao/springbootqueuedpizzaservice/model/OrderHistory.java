package tk.gutoleao.springbootqueuedpizzaservice.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gutoleao.springbootqueuedpizzaservice.converter.LocalDateTimeAttributeConverter;
import tk.gutoleao.springbootqueuedpizzaservice.enums.EnumOrderStatus;

@Entity
@Table(name = "orders_history", schema = "pizzaservice", uniqueConstraints = {
		@UniqueConstraint(columnNames = "order_history_id") })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

	@Id
	@GeneratedValue(generator = "sq_order_history", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sq_order_history", sequenceName = "pizzaservice.sq_order_history", schema = "pizzaservice", allocationSize = 1, initialValue = 1)
	@Column(name = "order_history_id")
	private Long id;

	@Column(name = "order_history_status")
	@Enumerated(EnumType.STRING)
	private EnumOrderStatus status;

	@Column(name = "order_history_updated_at")
	@Convert(converter = LocalDateTimeAttributeConverter.class)
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id", nullable = false)
	@JsonIgnore
	private Order order;

	public OrderHistory(EnumOrderStatus status, LocalDateTime updatedAt, Order order) {
		this.status = status;
		this.updatedAt = updatedAt;
		this.order = order;
	}

	@Override
	@JsonIgnore
	public String toString() {
		return "OrderHistory [id=" + id + ", status=" + status + ", updatedAt=" + updatedAt + "]";
	}

}
