package com.example.calendar.Models;

public class DayEvent {
    private Integer SalaryPerHour;
    private Integer Hour;

    public DayEvent() {
    }

    public DayEvent(Integer salaryPerHour, Integer hour, Integer additionHour, String event, String commentary) {
        SalaryPerHour = salaryPerHour;
        Hour = hour;
        AdditionHour = additionHour;
        Event = event;
        Commentary = commentary;
    }

    public Integer getSalaryPerHour() {
        return SalaryPerHour;
    }

    public void setSalaryPerHour(Integer salaryPerHour) {
        SalaryPerHour = salaryPerHour;
    }

    public Integer getHour() {
        return Hour;
    }

    public void setHour(Integer hour) {
        Hour = hour;
    }

    public Integer getAdditionHour() {
        return AdditionHour;
    }

    public void setAdditionHour(Integer additionHour) {
        AdditionHour = additionHour;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getCommentary() {
        return Commentary;
    }

    public void setCommentary(String commentary) {
        Commentary = commentary;
    }

    private Integer AdditionHour;
    private String Event;
    private String Commentary;
}
