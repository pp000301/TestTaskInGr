package com.valerii.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User implements Serializable {
    private String name;
    private String surname;
    private String email;
    private ArrayList<String> roles;
    private ArrayList<String> mobile;

}
