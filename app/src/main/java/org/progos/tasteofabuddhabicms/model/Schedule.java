package org.progos.tasteofabuddhabicms.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NomBhatti on 11/30/2015.
 */
public class Schedule implements Serializable {

    String id;
    String slug;
    String day;
    String date;

    public Schedule(String id, String slug, String day, String date) {
        this.id = id;
        this.slug = slug;
        this.day = day;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
