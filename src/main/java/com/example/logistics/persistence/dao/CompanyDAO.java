package com.example.logistics.persistence.dao;

import com.example.logistics.entities.Company;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompanyDAO extends BaseDAO<Company> {
    public CompanyDAO() {
        super(Company.class);
    }

    public int getTruckCount(Long companyId) {
        return ((Number) em.createNamedQuery("Company.getTruckCount")
                .setParameter("companyId", companyId)
                .getSingleResult()).intValue();
    }
}
