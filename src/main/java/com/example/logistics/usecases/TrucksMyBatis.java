package com.example.logistics.usecases;

import com.example.logistics.myBatis.dao.TruckMapper;
import com.example.logistics.myBatis.model.Company;
import com.example.logistics.myBatis.model.Truck;


import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Model
@SessionScoped
public class TrucksMyBatis implements Serializable {

    @Inject
    private TruckMapper truckMapper;

    @Inject
    private CompaniesMyBatis companies;

    @Getter
    @Setter
    private Truck truckToCreate = new Truck();

    @Getter
    private List<Truck> allSelectedTrucks;

    public void loadSelectedTrucks(){
        if (companies.getSelectedCompanyId() != null) {
            this.allSelectedTrucks = truckMapper.findByCompany(companies.getSelectedCompanyId());
        } else {
            this.allSelectedTrucks = new ArrayList<>();
        }
    }

    @PostConstruct
    public void initialize() {
        loadSelectedTrucks();
    }

    @Transactional
    public void createTruck() {
        if (companies.getSelectedCompanyId() != null) {
            truckToCreate.setCompanyId(companies.getSelectedCompanyId());
            truckMapper.insert(truckToCreate);
                loadSelectedTrucks();
                truckToCreate = new Truck();
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: Company is not selected.",
                            null));
        }
    }

    @Transactional
    public void deleteTruck(Truck truck) {
        truckMapper.deleteByPrimaryKey(truck.getId());
        loadSelectedTrucks();
    }
}

