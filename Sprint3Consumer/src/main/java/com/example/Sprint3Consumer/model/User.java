package com.example.Sprint3Consumer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    private int id;
    private String name;
    private int age;
    private String email;

    @Override
    public String toString() {
        return id + "/" + name + "/" + age + "/" + email;
    }
}
