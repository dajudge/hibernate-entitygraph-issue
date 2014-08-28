package com.example.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
public class A {
    @Id
    private int id;

    @OneToMany(mappedBy = "a")
    private Set<B> bs;
}
