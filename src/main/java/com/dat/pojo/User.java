/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dat.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author DELL
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "{user.name.nullErr}")
    @Size(min = 1, max = 50, message = "{user.name.lengthErr}")
    private String firstName;

    @NotNull(message = "{user.name.nullErr}")
    @Size(min = 1, max = 50, message = "{user.name.lengthErr}")
    private String lastName;

    @NotNull(message = "{user.email.nullErr}")
    @Size(min = 3, max = 50, message = "{user.email.lengthErr}")
    private String email;
    private String phone;

    @NotNull(message = "{user.username.nullErr}")
    @Size(max = 50, message = "{user.username.lengthErr}")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @NotNull(message = "{user.status.nullErr}")
    private UserStatus status;

    @Column(nullable = false)
    @NotNull(message = "{user.role.nullErr}")
    private UserRole role;

    private String image;

    public User(Integer id) {
        this.id = id;
    }
}
