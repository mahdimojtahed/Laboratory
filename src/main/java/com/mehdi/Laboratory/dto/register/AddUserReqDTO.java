package com.mehdi.Laboratory.dto.register;

import jakarta.validation.constraints.NotBlank;

public class AddUserReqDTO {

    @NotBlank(message = "REQUIRED_USERNAME")
    private String username;

    @NotBlank(message = "REQUIRED_FIRSTNAME")
    private String firstName;

    @NotBlank(message = "REQUIRED_LASTNAME")
    private String lastName;

    @NotBlank(message = "REQUIRED_PASSWORD")
    private String password;

    private Long birthDate;

    public AddUserReqDTO() {
    }

    public AddUserReqDTO(String username, String firstName, String lastName, String password, Long birthDate) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthDate = birthDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Long birthDate) {
        this.birthDate = birthDate;
    }
}
