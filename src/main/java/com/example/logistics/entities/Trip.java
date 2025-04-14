package com.example.logistics.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Trip.findAll", query = "select a from Trip as a")
})
@Table(name = "TRIP")
@Getter @Setter
public class Trip {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "TRUCK_ID", nullable = false)
    private Truck truck;

    @Basic(optional = false)
    @Column(name = "DEPARTURE_DATE", nullable = false)
    private Date departureDate;

    @Basic(optional = false)
    @Column(name = "TRIP_DURATION",nullable = false)
    private Integer duration;

    @Basic
    @Column(name = "DESTINATION_COUNTRY", nullable = false)
    private String destination;

    @ManyToMany
    private List<Driver> drivers;

    @Basic
    @Column(name = "DEPARTURE_COUNTRY", nullable = false)
    private String origin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
