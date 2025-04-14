package com.example.logistics.persistence.dao;

import com.example.logistics.entities.Driver;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DriverDAO extends BaseDAO<Driver> {
    public DriverDAO() {
        super(Driver.class);
    }

    public List<Driver> findByCompany(Long id) {
        return em.createNamedQuery("Driver.findByCompany", Driver.class)
                .setParameter("id", id)
                .getResultList();
    }
}
