package sk.upjs.miesici.klient.storage;

import java.sql.Date;

public class Training {

    private long id;
    private long clientId;
    private Date date;
    private String dayOfTheWeek;
    private String name;
    private String note;

    final String[] daysOftheWeekInSlovak = {null, "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok", "Sobota"};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayofTheWeek(int dayofTheWeek) {
        for (int i = 0; i < daysOftheWeekInSlovak.length; i++) {
            if (dayofTheWeek == i) {
                this.dayOfTheWeek = daysOftheWeekInSlovak[i];
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
