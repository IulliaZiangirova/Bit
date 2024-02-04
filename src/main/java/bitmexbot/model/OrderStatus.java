package bitmexbot.model;

import com.google.gson.annotations.SerializedName;

public enum OrderStatus {
    @SerializedName("New")
    NEW,
    WORKING,
    @SerializedName("Filled")
    FILLED,
    REJECTED,
    @SerializedName("Canceled")
    CANCELED
}
