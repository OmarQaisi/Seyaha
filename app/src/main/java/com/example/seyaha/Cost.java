package com.example.seyaha;

import java.util.List;

public class Cost
{

int activities;
int entranceFees;
int food;
int transportation;
List<SleepingPlace> overNightStay;

    public Cost(int activities, int entranceFees, int food, int transportation, List<SleepingPlace> overNightStay) {
        this.activities = activities;
        this.entranceFees = entranceFees;
        this.food = food;
        this.transportation = transportation;
        this.overNightStay = overNightStay;
    }


}
