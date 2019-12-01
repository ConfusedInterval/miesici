package sk.upjs.miesici;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.Date;

public class CustomerFxModel {
    private Long id;
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty email = new SimpleStringProperty();
    private DoubleProperty credit = new SimpleDoubleProperty();
    private Date MembershipExp = new Date();
    private StringProperty login = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private BooleanProperty admin = new SimpleBooleanProperty();
    private StringProperty salt = new SimpleStringProperty();

    private ObservableList<Customer> customers = FXCollections.observableArrayList();


    public void load(Customer customer) {
        id = customer.getId();
        setName(customer.getName());
        setSurname(customer.getSurname());
        setAddress(customer.getAddress());
        setEmail(customer.getEmail());
        setCredit(customer.getCredit());
        setMembershipExp(customer.getMembershipExp());
        setLogin(customer.getLogin());
        setPassword(customer.getPassword());
        setAdmin(customer.isAdmin());
        setSalt(customer.getSalt());
        customers.setAll(customer.getCustomers());
    }

    public Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(getName());
        customer.setSurname(getSurname());
        customer.setAddress(getAddress());
        customer.setEmail(getEmail());
        customer.setCredit(getCredit());
        customer.setMembershipExp(getMembershipExp());
        customer.setLogin(getLogin());
        customer.setPassword(getPassword());
        customer.setAdmin(isAdmin());
        customer.setSalt(getSalt());
        customer.setCustomers(new ArrayList<>(customers));
        return customer;
    }


    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ObservableList<Customer> customers) {
        this.customers = customers;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public double getCredit() {
        return credit.get();
    }

    public DoubleProperty creditProperty() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit.set(credit);
    }

    public Date getMembershipExp() {
        return MembershipExp;
    }

    public void setMembershipExp(Date membershipExp) {
        this.MembershipExp = membershipExp;
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public String getSalt() {
        return salt.get();
    }

    public StringProperty saltProperty() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt.set(salt);
    }
}
