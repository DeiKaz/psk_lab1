package com.example.logistics.usecases;

import com.example.logistics.myBatis.dao.CompanyMapper;
import com.example.logistics.myBatis.model.Company;
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
public class CompaniesMyBatis implements Serializable {

    @Inject
    private CompanyMapper companyMapper;

    @Getter @Setter
    private Long selectedCompanyId;

    @Getter @Setter
    private Company companyToCreate = new Company();

    @Getter
    private List<Company> allCompanies;

    public void loadAllCompanies(){
        this.allCompanies = companyMapper.selectAll();
    }

    @PostConstruct
    public void initialize() {
        loadAllCompanies();
    }

    @Transactional
    public void createCompany() {
        try {
            this.companyMapper.insert(companyToCreate);
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
        companyMapper.deleteByPrimaryKey(company.getId());
        loadAllCompanies();
    }

    public Company getById(Long id) {
        return companyMapper.selectByPrimaryKey(id);
    }

    @Inject
    private TrucksMyBatis trucks;

    @Inject
    private DriversMyBatis drivers;

    public void onCompanySelected() {
        if (selectedCompanyId != null) {
            trucks.loadSelectedTrucks();
            drivers.loadSelectedDrivers();
        }
    }
}
