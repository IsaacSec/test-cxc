package com.isaac.testcxc.entities;

import com.isaac.testcxc.exceptions.CatalogNotFoundException;
import jakarta.xml.bind.DatatypeConverter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class EmployeeBuilder {

    private Employee employee;
    private CsvEmployee csvEmployee;

    public EmployeeBuilder(CsvEmployee csvEmployee) throws CatalogNotFoundException {
        this.csvEmployee = csvEmployee;
        this.employee = new Employee();

        if (csvEmployee.getFirstName() == null || csvEmployee.getLastName() == null || csvEmployee.getMonthlySalary() == null) {
            throw new CatalogNotFoundException();
        }

        this.employee.setName(csvEmployee.getFirstName());
        this.employee.setLastName(csvEmployee.getLastName());
        this.employee.setMonthlySalary(csvEmployee.getMonthlySalary());
    }

    public EmployeeBuilder addAgency(Map<String, Agency> agencies) throws CatalogNotFoundException {
        Agency agency = agencies.get(csvEmployee.getAgencyName());

        if (agency != null) {
            this.employee.setAgencyId(agency.getId());
        } else {
            throw new CatalogNotFoundException();
        }

        return this;
    }

    public EmployeeBuilder addEthnicity(Map<String, Ethnicity> ethnicities) throws CatalogNotFoundException {
        Ethnicity ethnicity = ethnicities.get(csvEmployee.getEthnicity());

        if (ethnicity != null) {
            this.employee.setEthnicityId(ethnicity.getId());
        } else {
            throw new CatalogNotFoundException();
        }

        return this;
    }

    public EmployeeBuilder addGender(Map<String, Gender> genders) throws CatalogNotFoundException {
        Gender gender = genders.get(csvEmployee.getGender());

        if (gender != null) {
            this.employee.setGenderId(gender.getId());
        } else {
            throw new CatalogNotFoundException();
        }

        return this;
    }

    public EmployeeBuilder addProfession(Map<String, Profession> professions) throws CatalogNotFoundException {
        Profession profession = professions.get(csvEmployee.getClassTitle());

        if (profession != null) {
            this.employee.setProfessionId(profession.getId());
        } else {
            throw new CatalogNotFoundException();
        }

        return this;
    }


    public Employee build() throws CatalogNotFoundException, NoSuchAlgorithmException {

        if (this.employeeHasMissingValues()) {

            throw new CatalogNotFoundException();
        } else {
            MessageDigest generator = MessageDigest.getInstance("MD5");
            byte[] key = this.getTextToHash().getBytes();

            String checksum = DatatypeConverter.printHexBinary(generator.digest(key));
            employee.setMd5(checksum);
        }

        return this.employee;
    }

    private String getTextToHash() {

        return employee.getName()
                + "_" + employee.getLastName()
                + "_" + employee.getProfessionId()
                + "_" + employee.getEthnicityId()
                + "_" + employee.getGenderId();
    }

    private boolean employeeHasMissingValues() {

        return employee.getAgencyId() == null
                || employee.getGenderId() == null
                || employee.getEthnicityId() == null
                || employee.getProfessionId() == null;
    }
}
