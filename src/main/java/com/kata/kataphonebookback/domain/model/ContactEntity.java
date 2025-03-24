package com.kata.kataphonebookback.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.Objects;

@Schema(hidden=true)
@Entity
@Table(name="contact")
public class ContactEntity {

//    @Version
//    @Column(name="VERSION")
//    private int version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false, unique = true)
    private Long id;

    @Column(name="family_name", nullable = false)
    private String familyName;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContactEntity that = (ContactEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(familyName, that.familyName) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, familyName, firstName, email, phoneNumber);
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) return false;
//        ContactEntity that = (ContactEntity) o;
//        return version == that.version && Objects.equals(id, that.id) && Objects.equals(familyName, that.familyName) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email) && Objects.equals(phoneNumber, that.phoneNumber);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(version, id, familyName, firstName, email, phoneNumber);
//    }
}
