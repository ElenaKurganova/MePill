package com.example.android.mepill;

/**
 * Created by Elena on 19-Nov-17.
 */

public class Alarm {
    private long id;
    private String scheduleName;
    private String medicine;
    private int hour;
    private int minute;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
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