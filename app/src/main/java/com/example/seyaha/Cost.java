package com.example.seyaha;

import java.io.Serializable;
import java.util.List;

public class Cost implements Serializable
{

public int activities;
public int entranceFees;
public int food;
public int transportation;
public List<SleepingPlace> overNightStay;

public Cost(){}
    public Cost(int activities, int entranceFees, int food, int transportation, List<SleepingPlace> overNightStay) {
        this.activities = activities;
        this.entranceFees = entranceFees;
        this.food = food;
        this.transportation = transportation;
        this.overNightStay = overNightStay;
    }

}
