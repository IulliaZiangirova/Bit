package bitmexbot.model;

public class OrderRequest {
    private final String symbol;
    private final String side;
    private final Double orderQty;
    private final Double price;
    private final String ordType;
    private final Double stopPx;

    public OrderRequest(String symbol, String side, Double orderQty, Double price, String ordType, Double stopPx) {
        this.symbol = symbol;
        this.side = side;
        this.orderQty = orderQty;
        this.price = price;
        this.ordType = ordType;
        this.stopPx = stopPx;
    }

    public static OrderRequest toRequest(Order order){
        String symbol = order.getSymbol().toString();
        String side = order.getSide().toString();
        Double orderQty = order.getOrderQty();
        Double price = order.getPrice();
        String ordType = getType(order.getOrdType());
        Double stopPx = order.getStopPx();

        return new OrderRequest(symbol, side, orderQty, price, ordType, stopPx);
    }

    private static String getType(OrderType orderType){
        switch (orderType){
            case LMT: return "Limit";
            case MKT: return  "Market";
            case STP_LMT: return "StopLimit" ;
            case STP_MKT: return "Stop";
            default: throw new IllegalStateException("Unsupported order type");
        }
    }
}
