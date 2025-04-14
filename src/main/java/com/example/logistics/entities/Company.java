package com.example.logistics.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Company.findAll", query = "select a from Company as a"),
})
@Table(name = "COMPANY")
@Getter @Setter
public class Company {
    @Id
    @GeneratedValue
    private Long id;

    @Basic(optional = false)
    @Column(unique = true, name = "NAME", nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(name = "COUNTRY", nullable = false)
    private String country;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private List<Truck> trucks;

    @OneToMany(mappedBy = "company")
    private List<Driver> drivers;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
