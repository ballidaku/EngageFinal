package com.midnight.engage.Adapters;

/**
 * Created by adi on 6/8/16.
 */
public class BuyObject {
    Integer value;
    String price;
    String sku;

    public String getSku() {
        return sku;
    }

    public BuyObject(Integer value, String price , String sku) {
        this.value = value;
        this.price = price;
        this.sku = sku;

    }



    public Integer getValue() {
        return value;
    }

    public String getPrice() {
        return price;
    }
}
