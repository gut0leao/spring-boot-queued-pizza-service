package tk.gutoleao.springbootqueuedpizzaservice.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tk.gutoleao.springbootqueuedpizzaservice.converter.LocalDateTimeAttributeConverter;
import tk.gutoleao.springbootqueuedpizzaservice.enums.EnumOrderStatus;

@Entity
@Table(name = "orders", schema = "pizzaservice", uniqueConstraints = { @UniqueConstraint(columnNames = "order_id") })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(generator = "sq_order", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sq_order", sequenceName = "pizzaservice.sq_order", schema = "pizzaservice", allocationSize = 1, initialValue = 1)
    @Column(name = "order_id")
    private Long id;

    @Column(name = "order_requested_in")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime requestedIn;

    @Column(name = "order_description")
    private String description;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

    @Column(name = "order_last_update")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<OrderHistory> history = new ArrayList<>();

    @Override
    @JsonIgnore
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return String.format("{\"id\": \"%d\", \"description\": \"%s\", \"status\": \"%s\"}", this.id, this.getDescription(), this.getStatus());
        }
    }

}
