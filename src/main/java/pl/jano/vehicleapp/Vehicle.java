package pl.jano.vehicleapp;

import org.springframework.lang.NonNull;

public class Vehicle {
    @NonNull
    private long id;
    @NonNull
    private String mark;
    @NonNull
    private String model;
    @NonNull
    private String color;

    public Vehicle(long id, String mark, String model, String color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return '\n' + "Vehicle no. " + getId() + '\n' + "mark: " + getMark() + '\n' + "model: " + getModel() + '\n' + "colour: " + getColor();
    }
}