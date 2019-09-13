package com.example.seyaha;

import java.io.Serializable;

public class SleepingPlace implements Serializable {

    public String name;
    public int price;

    public SleepingPlace() {
    }

    public SleepingPlace(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
