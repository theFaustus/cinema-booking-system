package com.evil.cbs.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "orders")
public class Order extends AbstractEntity {

    @Column(name = "order_date")
    private LocalDate orderDate = LocalDate.now();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Ticket ticket;


    public static final class OrderBuilder {
        private LocalDate orderDate = LocalDate.now();
        private Ticket ticket;

        private OrderBuilder() {
        }

        public static OrderBuilder anOrder() {
            return new OrderBuilder();
        }

        public OrderBuilder orderDate(LocalDate orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public OrderBuilder ticket(Ticket ticket) {
            this.ticket = ticket;
            return this;
        }

        public Order build() {
            Order order = new Order();
            order.setOrderDate(orderDate);
            order.setTicket(ticket);
            return order;
        }
    }
}
