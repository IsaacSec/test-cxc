package com.isaac.testcxc.controllers;

import com.isaac.testcxc.entities.CsvEmployee;
import com.isaac.testcxc.responses.EmployeeResult;
import com.isaac.testcxc.services.EmployeeService;
import com.isaac.testcxc.utilities.CsvParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private CsvParser csvParser;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(CsvParser csvParser, EmployeeService employeeService) {
        this.csvParser = csvParser;
        this.employeeService = employeeService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<EmployeeResult> upload(@RequestPart("file") MultipartFile file) {

        log.info("Start employee upload");

        List<CsvEmployee> employees = csvParser.parseToEmployee(file);

        return employeeService.save(employees);
    }
}
