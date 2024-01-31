package bitmexbot.model;

import com.google.gson.annotations.SerializedName;

public enum OrderType {

    @SerializedName("Limit")
    LMT,
    MKT,
    STP_LMT,
    STP_MKT
}
