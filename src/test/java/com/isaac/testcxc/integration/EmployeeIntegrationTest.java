package com.isaac.testcxc.integration;

import com.isaac.testcxc.controllers.CatalogController;
import com.isaac.testcxc.controllers.EmployeeController;
import com.isaac.testcxc.entities.Employee;
import com.isaac.testcxc.repositories.EmployeeRepository;
import com.isaac.testcxc.responses.CatalogResult;
import com.isaac.testcxc.responses.EmployeeResult;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

@SpringBootTest
@Transactional
public class EmployeeIntegrationTest {

    @Autowired
    CatalogController catalogController;

    @Autowired
    EmployeeController employeeController;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        String catalogCsv = """
agency_name;class_title;ethnicity;gender;
AGENCY A;TEACHER;HISPANIC;MALE;
AGENCY B;WRITER;WHITE;FEMALE;
AGENCY C;FIREFIGHTER;BLACK;OTHER;
""";
        CatalogResult catalogResult = catalogController.upload(new MockMultipartFile("file", catalogCsv.getBytes())).block();
        Assertions.assertThat(catalogResult).isNotNull();
    }

    @Test
    void uploadEmployees_persistValuesInDB() {

        String employeeCsv = """
agency,agency_name,last_name,first_name,mi,class_code,class_title,ethnicity,gender,employee_type,hire_eate,rate,hrswkd,monthly,annual,statenum,duplicated,multiple_full_time_jobs,combined_multiple_jobs,hide_from_search,summed_annual_salary
551,AGENCY A,LONA,MANUEL,,1934,WRITER,HISPANIC,MALE,A,3/1/24,0,40,5565,66780,449,true,true,,,
101,AGENCY B,KENNY,PAT,,7104,TEACHER,WHITE,FEMALE,X,2/3/05,0,41,5699.58,68394.96,37375,true,,true,,
529,AGENCY C,VEIT,ALICIA,E,1986,FIREFIGHTER,WHITE,FEMALE,CRF,3/31/24,0,40,6500,78000,47067,true,true,,,
241,AGENCY B,SPECIA JR,JOHN,J,JD25,WRITER,WHITE,MALE,URP,2/1/20,75.9615,29,9545.82,114549.84,59115,true,,,,132249.96
""";

        EmployeeResult employeeResult = employeeController.upload(new MockMultipartFile("file", employeeCsv.getBytes())).block();
        Assertions.assertThat(employeeResult).isNotNull();

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees).hasSize(4);
        System.out.println("employees: " + employees);

        Assertions.assertThat(employees.stream().map(Employee::getName))
                .contains("MANUEL", "PAT", "ALICIA", "JOHN");
    }

    @Test
    void uploadEmployees_whenAreDuplicatedEntries_avoidToPersistDuplicates() {

        String employeeCsv = """
agency,agency_name,last_name,first_name,mi,class_code,class_title,ethnicity,gender,employee_type,hire_eate,rate,hrswkd,monthly,annual,statenum,duplicated,multiple_full_time_jobs,combined_multiple_jobs,hide_from_search,summed_annual_salary
551,AGENCY A,LONA,MANUEL,,1934,WRITER,HISPANIC,MALE,A,3/1/24,0,40,5565,66780,449,true,true,,,
101,AGENCY B,KENNY,PAT,,7104,TEACHER,WHITE,FEMALE,X,2/3/05,0,41,5699.58,68394.96,37375,true,,true,,
529,AGENCY C,VEIT,ALICIA,E,1986,FIREFIGHTER,WHITE,FEMALE,CRF,3/31/24,0,40,6500,78000,47067,true,true,,,
551,AGENCY A,LONA,MANUEL,,1934,WRITER,HISPANIC,MALE,A,3/1/24,0,40,5565,66780,449,true,true,,,
551,AGENCY B,LONA,MANUEL,,1934,WRITER,HISPANIC,MALE,AFDSAFSA,3/1/24,0,40,5565,66780,449,true,true,,,
""";

        EmployeeResult employeeResult = employeeController.upload(new MockMultipartFile("file", employeeCsv.getBytes())).block();
        Assertions.assertThat(employeeResult).isNotNull();

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees).hasSize(3);
        System.out.println("employees: " + employees);

        Assertions.assertThat(employees.stream().map(Employee::getName))
                .contains("MANUEL", "PAT", "ALICIA");
    }

    @Test
    void uploadEmployees_whenEmployeeDoesNotHaveValidGenderEthnicityProfessionOrAgency_doNotPersist() {

        String employeeCsv = """
agency,agency_name,last_name,first_name,mi,class_code,class_title,ethnicity,gender,employee_type,hire_eate,rate,hrswkd,monthly,annual,statenum,duplicated,multiple_full_time_jobs,combined_multiple_jobs,hide_from_search,summed_annual_salary
551,INVALID AGENCY,LONA,MANUEL,,1934,WRITER,HISPANIC,MALE,A,3/1/24,0,40,5565,66780,449,true,true,,,
101,AGENCY B,KENNY,PAT,,7104,INVALID PROFESSION,WHITE,FEMALE,X,2/3/05,0,41,5699.58,68394.96,37375,true,,true,,
529,AGENCY C,VEIT,ALICIA,E,1986,FIREFIGHTER,INVALID ETHNICITY,FEMALE,CRF,3/31/24,0,40,6500,78000,47067,true,true,,,
241,AGENCY B,SPECIA JR,JOHN,J,JD25,WRITER,WHITE,INVALID GENDER,URP,2/1/20,75.9615,29,9545.82,114549.84,59115,true,,,,132249.96
551,AGENCY A,JOHNSON,JOHN,,1934,WRITER,HISPANIC,MALE,A,3/1/24,0,40,5565,66780,449,true,true,,,
""";

        EmployeeResult employeeResult = employeeController.upload(new MockMultipartFile("file", employeeCsv.getBytes())).block();
        Assertions.assertThat(employeeResult).isNotNull();

        List<Employee> employees = employeeRepository.findAll();

        Assertions.assertThat(employees).hasSize(1);
        System.out.println("employees: " + employees);

        Assertions.assertThat(employees.stream().map(Employee::getName))
                .contains("JOHN");
    }
}
