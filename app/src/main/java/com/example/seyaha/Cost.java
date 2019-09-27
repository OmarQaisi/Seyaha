package com.example.seyaha;

import java.io.Serializable;
import java.util.List;

public class Cost implements Serializable {

    public int entranceFees;
    public int food;
    public int transportation;
    public List<Integer> overNightStay;

    public Cost() { }

    public Cost(int entranceFees, int food, int transportation, List<Integer> overNightStay) {
        this.entranceFees = entranceFees;
        this.food = food;
        this.transportation = transportation;
        this.overNightStay = overNightStay;
    }

}
