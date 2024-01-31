package bitmexbot.model;

import com.google.gson.annotations.SerializedName;

public enum OrderSide {
    @SerializedName("Buy")
    BUY ("Buy"),
    @SerializedName("Sell")
    SELL("Sell");

    public final String label;

    private OrderSide(String label) {
        this.label = label;
    }

}
