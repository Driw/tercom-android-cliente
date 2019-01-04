package br.com.tercom.Entity;

import java.util.Date;

public class Register extends GenericEntity {
    private Date date;
    private String timezone;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
