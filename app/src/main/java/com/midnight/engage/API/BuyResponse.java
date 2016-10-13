package com.midnight.engage.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adi on 7/6/16.
 */
public class BuyResponse {
    @SerializedName("id")
    String id;
    @SerializedName("orderC")
    String orderC;
    @SerializedName("name")
    String name;
    @SerializedName("price")
    String price;
    @SerializedName("save")
    String save;
    @SerializedName("type")
    String type;
    @SerializedName("amount")
    String amount;
    @SerializedName("exchange")
    String exchange;

    public String getExchange() {
        return exchange;
    }

    public String getId() {
        return id;
    }

    public String getOrderC() {
        return orderC;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getSave() {
        return save;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }
}
