package com.example.logistics.usecases;

import com.example.logistics.entities.Company;
import com.example.logistics.entities.Trip;
import com.example.logistics.entities.Driver;
import com.example.logistics.entities.Truck;
import com.example.logistics.persistence.dao.CompanyDAO;
import com.example.logistics.persistence.dao.TripDAO;
import com.example.logistics.persistence.dao.DriverDAO;
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
public class Trips implements Serializable{
    @Inject
    private TripDAO tripDAO;

    @Inject
    private CompanyDAO companyDAO;

    @Inject
    private TruckDAO truckDAO;

    @Inject
    private DriverDAO driverDAO;

    @Getter @Setter
    private Trip tripToCreate = new Trip();

    @Getter @Setter
    private Long selectedCompanyId;

    @Getter
    private List<Truck> availableTrucks;

    @Getter
    private List<Driver> availableDrivers;

    @Getter
    private List<Trip> allTrips;

    private List<Company> allCompanies;

    @Getter @Setter
    private Long selectedTruckId;

    public void loadAllTrips(){
        this.allTrips = tripDAO.findAll();
    }

    @PostConstruct
    public void initialize() {
        loadAllTrips();
    }

    public List<Company> getAllCompanies() {
        return companyDAO.findAll();
    }

    public void onCompanyChange() {
        if (selectedCompanyId != null) {
            availableTrucks = truckDAO.findByCompany(selectedCompanyId);
            availableDrivers = driverDAO.findByCompany(selectedCompanyId);
        } else {
            availableTrucks = new ArrayList<>();
            availableDrivers = new ArrayList<>();
        }
    }

    public void onTruckChange() {
        if (selectedTruckId != null && !selectedTruckId.equals(0L)) {
            Truck selectedTruck = truckDAO.findById(selectedTruckId);
            tripToCreate.setTruck(selectedTruck);
        } else {
            tripToCreate.setTruck(null);
        }
    }

    @Transactional
    public void createTrip() {
        try {
            tripDAO.create(tripToCreate);
            tripToCreate = new Trip();
            loadAllTrips();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: " + e,
                            null));
        }
    }

    @Transactional
    public void deleteTrip(Trip trip) {
        tripDAO.delete(trip);
        loadAllTrips();
    }
}
