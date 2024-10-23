package org.example.gestorjuegosjdbc.models;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class User implements Serializable {
     private Integer id;
     private String email;
     private String password;
     private Boolean is_admin;

     private List<Game> games = new ArrayList<>(0);
}
