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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date")
    private Date orderDate = new Date();
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private Ticket ticket;


    public static final class OrderBuilder {
        private Date orderDate = new Date();
        private Ticket ticket;

        private OrderBuilder() {
        }

        public static OrderBuilder anOrder() {
            return new OrderBuilder();
        }

        public OrderBuilder orderDate(Date orderDate) {
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
