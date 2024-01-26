package bitmexbot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@ToString
@Getter
@Setter
public class Order {
    private String orderID;
    private Symbol symbol;
    private String side;
    private double orderQty;
    private Double price;   //can be null => market-order
    private Double stopPx;
    private boolean isWorking;
    private OrderType ordType;
    private OrderStatus ordStatus;


}
