package net.microwonk.aufg_jdbc.dao_land.domain;

import lombok.Getter;

import java.sql.Date;

@Getter
public class Student extends BaseEntity{
    private String firstname;
    private String lastname;
    private String street;
    private int streetNumber;
    private int postalCode;
    private Date birthDate;

    public Student(Long id, String vorname, String nachname, String street, int streetNumber, int postalCode, Date birthDate) {
        super(id);
        this.firstname = vorname;
        this.lastname = nachname;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
    }
    public Student(String firstname, String lastname, String street, int streetNumber, int postalCode, Date birthDate) {
        super(null);
        this.firstname = firstname;
        this.lastname = lastname;
        this.street = street;
        this.streetNumber = streetNumber;
        this.postalCode = postalCode;
        this.birthDate = birthDate;
    }


    public void setFirstname(String firstname) {
        if (firstname != null && firstname.length() > 1) {
            this.firstname = firstname;
        } else {
            throw new InvalidValueException("Der Vorname muss eingetragen werden");
        }
    }

    public void setLastname(String lastname) {
        if (firstname != null && firstname.length() > 1) {
            this.lastname = lastname;
        } else {
            throw new InvalidValueException("Der Nachname muss eingetragen werden");
        }
    }

    public void setStreet(String street) {
        if (street != null && street.length() > 1) {
            this.street = street;
        } else {
            throw new InvalidValueException("Die Strasse muss eingetragen werden");
        }
    }

    public void setStreetNumber(int streetNumber) {
        if (streetNumber>0) {
            this.streetNumber = streetNumber;
        } else {
            throw new InvalidValueException("Die Hausnummer muss eingetragen werden");
        }
    }

    public void setPostalCode(int postalCode) {
        if (postalCode > 999) {
            this.postalCode = postalCode;
        } else {
            throw new InvalidValueException("Die PLZ muss eingetragen werden -> 4 Stellig");
        }
    }

    public Date getGebDatum() {
        return birthDate;
    }

    public void setGebDatum(Date birthDate) {
        if (birthDate != null) {
            this.birthDate = birthDate;
        } else {
            throw new InvalidValueException("Das Geburtsdatum muss eingetragen werden");
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber=" + streetNumber +
                ", postalCode=" + postalCode +
                ", birthDate=" + birthDate +

                '}';
    }
}

