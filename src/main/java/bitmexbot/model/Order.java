package bitmexbot.model;

import lombok.Builder;

@Builder
public class Order {
    private String orderID;
    private Symbol symbol;
    private boolean isBuy;
    private double orderQty;
    private Double price;   //can be null => market-order
    private Double stopPx;
    private boolean isWorking;
    private OrderType orderType;
    private OrderStatus orderStatus;



    public String getOrderId() {
        return orderID;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public Double getPrice() {
        return price;
    }

    public Double getStopPx() {
        return stopPx;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
