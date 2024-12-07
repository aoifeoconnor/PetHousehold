package com.example.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "myusers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUser {

    @Id
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String county;
    private String role;

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;


    public MyUser(String mail, String password123, String admin, String user, String roleAdmin, boolean b, boolean b1, boolean b2, boolean b3) {
    }
}
