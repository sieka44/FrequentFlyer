package com.sabre.frequentflyer.db;

import java.util.Objects;

enum UserStatus{
    NOT_A_FF(1.0), SILVER (0.9), GOLD (0.8);
    private final double cost;
    UserStatus(double a){
        this.cost = a;
    }
}

public class User {
    private String Name;
    private String Surname;
    private String email;
    private UserStatus status;
    private double miles;

    public User(String name, String surname, String email) {
        Name = name;
        Surname = surname;
        this.email = email;
        miles = 0;
    }

    public void changeStatus(String status){
        if ("silver".equalsIgnoreCase(status))this.status= UserStatus.SILVER;
        else if ("gold".equalsIgnoreCase(status))this.status= UserStatus.GOLD;
        else this.status = UserStatus.NOT_A_FF;
    }
    public UserStatus getStatus() {
        return status;
    }

    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }

    public String getEmail() {
        return email;
    }

    public double getMiles() {
        return miles;
    }

    public void updateMiles(double value){
        miles+=value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }
}
