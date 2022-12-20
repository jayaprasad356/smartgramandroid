package com.kapp.smartgram.model;

public class TimeSlotModel {
    private String date,start_time,end_time;

    public TimeSlotModel(String date, String start_time, String end_time) {
        this.date = date;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
