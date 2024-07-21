package com.isaac.testcxc.services;

import com.isaac.testcxc.entities.*;
import com.isaac.testcxc.repositories.*;
import com.isaac.testcxc.responses.EmployeeResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private AgencyRepository agencyRepository;
    private EthnicityRepository ethnicityRepository;
    private GenderRepository genderRepository;
    private ProfessionRepository professionRepository;

    public EmployeeService(
            AgencyRepository agencyRepository,
            EthnicityRepository ethnicityRepository,
            GenderRepository genderRepository,
            ProfessionRepository professionRepository,
            EmployeeRepository employeeRepository) {
        this.agencyRepository = agencyRepository;
        this.ethnicityRepository = ethnicityRepository;
        this.genderRepository = genderRepository;
        this.professionRepository = professionRepository;
        this.employeeRepository = employeeRepository;
    }

    public Mono<EmployeeResult> save(List<CsvEmployee> employees) {

        Map<String, Agency> agencies = this.agencyRepository.findAll()
                .stream().collect(Collectors.toMap(Agency::getName, a -> a));
        Map<String, Ethnicity> ethnicities = this.ethnicityRepository.findAll()
                .stream().collect(Collectors.toMap(Ethnicity::getName, e -> e));
        Map<String, Gender> genders = this.genderRepository.findAll()
                .stream().collect(Collectors.toMap(Gender::getName, g -> g));
        Map<String, Profession> professions = this.professionRepository.findAll()
                .stream().collect(Collectors.toMap(Profession::getName, p -> p));

        List<Employee> employeesToSave = new ArrayList<>();

        employees.forEach(e -> {
            try {
                EmployeeBuilder builder = new EmployeeBuilder(e);
                Employee employee = builder.addAgency(agencies)
                        .addEthnicity(ethnicities)
                        .addGender(genders)
                        .addProfession(professions)
                        .build();

                employeesToSave.add(employee);
            } catch (Exception ex) {
                log.error("Failed to build employee: {}", e, ex);
            }
        });

        List<Employee> saved = employeeRepository.saveAll(employeesToSave.stream().distinct().toList());

        EmployeeResult result = new EmployeeResult();
        result.setNewEmployees(saved.size());

        return Mono.just(result);
    }

}
