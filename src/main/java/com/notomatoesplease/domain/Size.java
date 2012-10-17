package com.notomatoesplease.domain;

public class Size {
    private int price;
    private String name;

    public Size(final String name, final int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

}
