package com.example.android.mepill;

/**
 * Created by Elena on 19-Nov-17.
 */

public class AlarmCreationSingleton {

    private static AlarmCreationSingleton INSTANCE = null;
    private int quantity;
    private String medicine;
    private int hour;
    private int minute;

    private AlarmCreationSingleton() {

    }

    public static AlarmCreationSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AlarmCreationSingleton();
        }
        return INSTANCE;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}




