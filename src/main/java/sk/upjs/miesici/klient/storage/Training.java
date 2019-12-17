package sk.upjs.miesici.klient.storage;

import java.sql.Date;

public class Training {

	private Long id;
	private Long clientId;
	private Date date;
	private String dayOfTheWeek;
	private String name;
	private String note;

	final String[] daysOftheWeekInSlovak = { null, "Nedeľa", "Pondelok", "Utorok", "Streda", "Štvrtok", "Piatok",
			"Sobota" };

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
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
