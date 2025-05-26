package com.example.logistics.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@NamedQueries({
        @NamedQuery(name = "Driver.findByCompany", query = "select p from Driver p where p.company.id = :id"),
})
@Table(name = "DRIVER")
@Getter @Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @Column(name = "IDENTIFICATION_NUMBER", nullable = false)
    private String identificationNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "COMPANY_ID", nullable = false)
    private Company company;

    @Basic(optional = false)
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Basic(optional = false)
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @PrePersist
    @PreUpdate
    private void validateIdentificationNumber() {
        if (!Pattern.matches("^\\d{5}$", identificationNumber)) {
            throw new IllegalArgumentException("Invalid identification number format");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return Objects.equals(id, driver.id) &&
                Objects.equals(identificationNumber, driver.identificationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identificationNumber);
    }

    @ManyToMany(mappedBy = "drivers")
    private List<Trip> trips;
}
