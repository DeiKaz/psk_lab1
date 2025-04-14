package com.example.logistics.persistence.dao;

import com.example.logistics.entities.Truck;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class TruckDAO extends BaseDAO<Truck> {
    public TruckDAO() {
        super(Truck.class);
    }

    public List<Truck> findByCompany(Long id) {
        return em.createNamedQuery("Truck.findByCompany", Truck.class)
                .setParameter("id", id)
                .getResultList();
    }
}
