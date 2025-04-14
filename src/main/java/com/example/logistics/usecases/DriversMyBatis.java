package com.example.logistics.usecases;

import com.example.logistics.myBatis.dao.DriverMapper;
import com.example.logistics.myBatis.model.Driver;
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
public class DriversMyBatis implements Serializable {

    @Inject
    private DriverMapper driverMapper;

    @Inject
    private CompaniesMyBatis companies;

    @Getter
    @Setter
    private Driver driverToCreate = new Driver();

    @Getter
    private List<Driver> allSelectedDrivers;

    public void loadSelectedDrivers(){
        if (companies.getSelectedCompanyId() != null) {
            this.allSelectedDrivers = driverMapper.findByCompany(companies.getSelectedCompanyId());
        } else {
            this.allSelectedDrivers = new ArrayList<>();
        }
    }

    @PostConstruct
    public void initialize() {
        loadSelectedDrivers();
    }

    @Transactional
    public void createDriver() {
        if (companies.getSelectedCompanyId() != null) {
            try {
                driverToCreate.setCompanyId(companies.getSelectedCompanyId());
                driverMapper.insert(driverToCreate);
                loadSelectedDrivers();
                driverToCreate = new Driver();
            }
            catch (IllegalArgumentException e)
            {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error: Driver identification number must be made from 5 digits.",
                                null));
            }
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: Company is not selected.",
                            null));
        }
    }

    @Transactional
    public void deleteDriver(Driver driver) {
        driverMapper.deleteByPrimaryKey(driver.getId());
        loadSelectedDrivers();
    }
}

