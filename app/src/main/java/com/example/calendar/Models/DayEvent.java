package com.example.calendar.Models;

public class DayEvent {
    private String userUid;
    private String dayDate;
    private Integer salaryPerHour;
    private Integer hour;
    private Integer additionHour;
    private String event;
    private String commentary;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private String month;

    public DayEvent(String userUid, String dayDate, Integer salaryPerHour, Integer hour, Integer additionHour, String event, String commentary, String month) {
        this.userUid = userUid;
        this.dayDate = dayDate;
        this.salaryPerHour = salaryPerHour;
        this.hour = hour;
        this.additionHour = additionHour;
        this.event = event;
        this.commentary = commentary;
        this.month = month;
    }

    public DayEvent() {
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getDayDate() {
        return dayDate;
    }

    public void setDayDate(String dayDate) {
        this.dayDate = dayDate;
    }

    public Integer getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(Integer salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getAdditionHour() {
        return additionHour;
    }

    public void setAdditionHour(Integer additionHour) {
        this.additionHour = additionHour;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

}
