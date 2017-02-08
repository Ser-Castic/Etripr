package com.theironyard.entities;

import javax.persistence.*;

@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String tripName;

    @Column(nullable = false)
    String startLocation;

    @Column(nullable = false)
    String endLocation;

    @ManyToOne
    User user;
}
