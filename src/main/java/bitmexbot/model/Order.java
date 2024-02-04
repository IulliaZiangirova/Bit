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
    private String orderID;

    @Enumerated(EnumType.STRING)
    private Symbol symbol;


    private String side;

    @Column(name = "order_qty")
    private double orderQty;

    private Double price;   //can be null => market-order

    private Double stopPx;

    @Column(name = "work_indicator")
    private boolean workingIndicator;

    @Column(name = "ord_type")
    @Enumerated(EnumType.STRING)
    private OrderType ordType;

    @Column(name = "ord_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus ordStatus;

//    @Column(name = "transact_time")
//    private LocalDateTime transactTime;


//    @CreationTimestamp
//    @Column(name = "create_date")
//    private LocalDateTime creationDate;
//    @UpdateTimestamp
//    @Column(name = "update_date")
//    private LocalDateTime changeDate;


}
