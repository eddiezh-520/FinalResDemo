package com.example.finalresdemo.ui.vo;

import com.example.finalresdemo.bean.Product;
import com.example.finalresdemo.bean.Restaurant;

public class ProductItem extends Product {

    public int count;

    public ProductItem(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.label = product.getLabel();
        this.description = product.getDescription();
        this.icon = product.getIcon();
        this.price = product.getPrice();
        this.restaurant = product.getRestaurant();
    }
}
