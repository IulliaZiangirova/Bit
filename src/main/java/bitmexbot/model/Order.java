package bitmexbot.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @Expose
    private String orderID;

    @Enumerated(EnumType.STRING)
    @Expose
    private Symbol symbol;

    @Expose
    private String side;
    @Expose
    @Column(name = "order_qty")
    private double orderQty;
    @Expose
    private Double price;   //can be null => market-order
    @Expose
    private Double stopPx;
    @Expose
    @Column(name = "work_indicator")
    private boolean workingIndicator;

    @Expose
    @Column(name = "ord_type")
    @Enumerated(EnumType.STRING)
    private OrderType ordType;
    @Expose
    @Column(name = "ord_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus ordStatus;


    @Column(name = "create_date")
    @CreationTimestamp
    public LocalDateTime creationDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime changeDate;


}
