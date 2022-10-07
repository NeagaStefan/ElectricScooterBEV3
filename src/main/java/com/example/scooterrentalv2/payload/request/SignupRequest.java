package com.example.scooterrentalv2.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;


public class SignupRequest {
        @NotBlank
        @Size(min = 3, max = 20)
        private String username;

        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        private Set<String> role;

    public String getCardNumber() {
        return cardNumber;
    }

    @NotBlank
        @Size(min = 6, max = 40)
        private String password;
    public String cardNumber;
    public String cardCSV;

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCSV() {
        return cardCSV;
    }

    public void setCardCSV(String cardCSV) {
        this.cardCSV = cardCSV;
    }

    public String getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(String cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    private String cardExpDate;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Set<String> getRole() {
            return this.role;
        }

        public void setRole(Set<String> role) {
            this.role = role;
        }

}

