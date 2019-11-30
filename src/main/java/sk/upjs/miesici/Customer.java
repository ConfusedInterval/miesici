package sk.upjs.miesici;

import java.util.Date;
import java.util.ArrayList;

public class Customer {
	private Long id;
	private String name;
	private String surname;
	private String address;
	private String email;
	private double credit;
	private Date membershipExp;
	private String login;
	private String password;
	private String salt;
	private boolean admin;
	private ArrayList<String> customers = new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getCredit() {
		return credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public Date getMembershipExp() {
		return membershipExp;
	}

	public void setMembershipExp(Date membershipExp) {
		this.membershipExp = membershipExp;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isAdmin(boolean admin) {
		return this.admin;
	}

	public void setAdmin() {
		this.admin = admin;
	}

	public void addCustomer(String customerName){
		customers.add(customerName);
	}

	public void setCustomers(ArrayList<String> customers){
		this.customers = customers;
	}

	public ArrayList<String> getCustomers(){
		return customers;
	}

}
