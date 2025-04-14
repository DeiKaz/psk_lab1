package com.example.logistics.usecases;

import com.example.logistics.entities.Company;
import com.example.logistics.persistence.dao.CompanyDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Model
@SessionScoped
public class Companies implements Serializable {

    @Inject
    private CompanyDAO companyDAO;

    @Getter @Setter
    private Long selectedCompanyId;

    @Getter @Setter
    private Company companyToCreate = new Company();

    @Getter
    private List<Company> allCompanies;

//    @Getter
//    private Map<Long, Integer> truckCount = new HashMap<>();

    public void loadAllCompanies(){
        this.allCompanies = companyDAO.findAll();
    }

    @PostConstruct
    public void initialize() {
        loadAllCompanies();
    }

    @Transactional
    public void createCompany() {
        try {
            this.companyDAO.create(companyToCreate);
            loadAllCompanies();
            companyToCreate = new Company();
        } catch (PersistenceException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error: Company name must be unique.",
                            null));
        }
    }

    @Transactional
    public void deleteCompany(Company company) {
            companyDAO.delete(company);
            loadAllCompanies();
    }

    public Company getById(Long id) {
        return companyDAO.findById(id);
    }

    @Inject
    private Trucks trucks;

    @Inject
    private Drivers drivers;

    public void onCompanySelected() {
        if (selectedCompanyId != null) {
            trucks.loadSelectedTrucks();
            drivers.loadSelectedDrivers();
        }
    }

    public void refreshCompany()
    {
        loadAllCompanies();
    }
}
