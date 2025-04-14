package com.example.logistics.usecases;

import com.example.logistics.entities.Company;
import com.example.logistics.entities.Truck;
import com.example.logistics.persistence.dao.TruckDAO;
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
public class Trucks implements Serializable {

    @Inject
    private TruckDAO truckDAO;

    @Inject
    private Companies companies;

    @Getter
    @Setter
    private Truck truckToCreate = new Truck();

    @Getter
    private List<Truck> allSelectedTrucks;

    public void loadSelectedTrucks(){
        if (companies.getSelectedCompanyId() != null) {
            this.allSelectedTrucks = truckDAO.findByCompany(companies.getSelectedCompanyId());
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
            Company selectedCompany = companies.getById(companies.getSelectedCompanyId());
            if (selectedCompany != null) {
                truckToCreate.setCompany(selectedCompany);
                truckDAO.create(truckToCreate);
                loadSelectedTrucks();

                companies.refreshCompany();
                truckToCreate = new Truck();
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: Company is not selected.",
                            null));
        }
    }

    @Transactional
    public void deleteTruck(Truck truck) {
        truckDAO.delete(truck);
        loadSelectedTrucks();
    }
}
