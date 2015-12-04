package org.progos.tasteofabuddhabicms.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by NomBhatti on 11/30/2015.
 */
public class ScheduleItem implements Serializable {

    String time;
    String act;

    public ScheduleItem(String time, String act) {
        this.time = time;
        this.act = act;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
