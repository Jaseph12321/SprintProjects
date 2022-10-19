package com.example.sprint3.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UpdateRequest {

    private String name;
    private String age;
    private String email;
    @Override
    public String toString() {
        return name + "/" + age + "/" + email;
    }
}
