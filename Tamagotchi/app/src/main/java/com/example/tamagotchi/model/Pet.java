package com.example.tamagotchi.model;

public class Pet {

    private int hunger;
    private int care;
    private int mood;

    private int day;
    private String name;

    public Pet(String name) {
        this.name = name;
        hunger = 100;
        care = 100;
        mood = 100;
        day = 1;
    }

    public int getHunger() {
        return hunger;
    }

    public int getCare() {
        return care;
    }

    public int getMood() {
        return mood;
    }

    public int getDay() {
        return day;
    }


    public String getName() {
        return name;
    }

    public void feed() {
        hunger = Math.min(100, hunger + 25);
    }

    public void clean() {
        care = Math.min(100, care + 20);
    }

    public void play() {
        mood = Math.min(100, mood + 20);
    }

    public void decreaseStats() {
        hunger -= 3;
        care -= 2;
        mood -= 2;
    }

    public void nextDay() {
        day++;
    }

    public boolean isHungryDead() {
        return hunger <= 0;
    }

    public boolean isCareDead() {
        return care <= 0;
    }

    public boolean isMoodDead() {
        return mood <= 0;
    }
}
