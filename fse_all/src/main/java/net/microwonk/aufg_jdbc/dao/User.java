package net.microwonk.aufg_jdbc.dao;

import java.util.Date;

public class User {

    public static final User EMPTY = new User(0,null,"","","","", "");

    private int id;
    private Date birthDate;
    private String firstName;
    private String lastName;
    private String eMail;
    private String phone;
    private String street;

    public User(int id, Date birthDate, String firstName, String lastName, String eMail, String phone, String street) {
        setId(id);
        setBirthDate(birthDate);
        seteMail(eMail);
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
        setStreet(street);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                '}';
    }
}
