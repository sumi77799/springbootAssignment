package com.employee.assignment.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "employees")
@TypeAlias("Employee")
public class Employee {
    @Id
    private String id;

    @Field("employeeName")
    private String employeeName;

    @Field("phoneNumber")
    private String phoneNumber;

    @Field("email")
    private String email;

    @Field("reportsTo")
    private String reportsTo;

    @Field("profileImage")
    private String profileImage;

    // Constructors are not shown for brevity

    public void setId(String id) {
        this.id = id;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setReportsTo(String reportsTo) {
        this.reportsTo = reportsTo;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // Getters for each field

    public String getId() {
        return id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getReportsTo() {
        return reportsTo;
    }

    public String getProfileImage() {
        return profileImage;
    }

    // Additional methods or overrides can be added as needed
}
