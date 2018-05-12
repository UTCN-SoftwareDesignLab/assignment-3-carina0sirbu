package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private int identityCardNumber;

    @Column(unique = true)
    private String persNumCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    private String address;

    @Autowired
    public Patient(String name, int identityCardNumber, String persNumCode, Date dateOfBirth, String address) {
        this.name = name;
        this.identityCardNumber = identityCardNumber;
        this.persNumCode = persNumCode;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public Patient() {}

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

    public int getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(int identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getPersNumCode() {
        return persNumCode;
    }

    public void setPersNumCode(String persNumCode) {
        this.persNumCode = persNumCode;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return name + ", " + identityCardNumber + ", " +
                persNumCode + ", " + dateOfBirth + ", " + address;
    }
}
