package com.pachkhede.secretdiary;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryEntry {
    private int id;
    private String title, content;
    private Date date;

    public DiaryEntry() {

    }

    public DiaryEntry(int id, String title, String content, Date date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String  getMonthAbbreviation() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM", Locale.US);
        return sdf.format(this.getDate()).toUpperCase();
    }

    public String getMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        return sdf.format(this.getDate());
    }

    public String  getYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return sdf.format(this.getDate());
    }

    public String getDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        return sdf.format(this.getDate());
    }



}
