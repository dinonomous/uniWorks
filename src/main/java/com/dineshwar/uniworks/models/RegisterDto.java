package com.dineshwar.uniworks.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public class RegisterDto {

    @NotEmpty
    private String firstname;

    @NotEmpty
    private String lastname;

    @NotEmpty
    private String username;

    @NotEmpty
    @Email(message = "Please provide a valid email address")
    private String email;

    private String phone;
    private String address;

    @NotEmpty
    private String password;

    public @NotEmpty String getFirstname() {
        return firstname;
    }

    public void setFirstname(@NotEmpty String firstname) {
        this.firstname = firstname;
    }

    public @NotEmpty String getLastname() {
        return lastname;
    }

    public void setLastname(@NotEmpty String lastname) {
        this.lastname = lastname;
    }

    public @NotEmpty String getUsername() {
        return username;
    }

    public void setUsername(@NotEmpty String username) {
        this.username = username;
    }

    public @NotEmpty @Email(message = "Please provide a valid email address") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty @Email(message = "Please provide a valid email address") String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public @NotEmpty String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty String password) {
        this.password = password;
    }
}
