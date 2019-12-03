package sk.upjs.miesici.admin.storage;

public class Entrance {
	private Long id;
	private Long klient_id;
	private String name;
	private String surname;
	private String arrival;
	private String exit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getKlient_id() {
		return klient_id;
	}

	public void setKlient_id(Long klient_id) {
		this.klient_id = klient_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getExit() {
		return exit;
	}

	public void setExit(String exit) {
		this.exit = exit;
	}

}
