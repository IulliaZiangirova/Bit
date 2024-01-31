package bitmexbot.model;

import com.google.gson.annotations.SerializedName;

public enum OrderStatus {
    @SerializedName("New")
    NEW,
    WORKING,
    FILLED,
    REJECTED,
    CANCELED
}
