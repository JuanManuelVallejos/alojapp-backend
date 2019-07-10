package com.grupo1.alojapp.Model;

import javax.persistence.*;

@Entity
@Table(name = "role_app")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
