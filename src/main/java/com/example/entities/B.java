package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class B {
    @Id
    private int id;

    @ManyToOne
    private A a;
}