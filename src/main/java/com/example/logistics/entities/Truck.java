package com.example.logistics.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Truck.findByCompany", query = "select p from Truck p where p.company.id = :id"),
})
@Table(name = "TRUCK")
@Getter @Setter
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "MODEL", nullable = false)
    private String model;

    @Basic(optional = false)
    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @OneToMany(mappedBy = "truck")
    private List<Trip> trips;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
