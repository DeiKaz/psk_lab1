package com.example.logistics.persistence.dao;

import com.example.logistics.entities.Trip;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TripDAO extends BaseDAO<Trip> {
    public TripDAO() {
        super(Trip.class);
    }
}
