package sk.upjs.miesici;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

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

	private List<Customer> customers = new ArrayList<>();

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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void addCustomer(Customer customerName){
		customers.add(customerName);
	}

	public void setCustomers(List<Customer> customers){
		this.customers = customers;
	}

	public List<Customer> getCustomers(){
		return customers;
	}

}
