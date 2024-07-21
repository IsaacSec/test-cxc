package com.isaac.testcxc.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CsvEmployee {

    @CsvBindByName(column = "first_name")
    private String firstName;

    @CsvBindByName(column = "last_name")
    private String lastName;

    @CsvBindByName(column = "agency_name")
    private String agencyName;

    @CsvBindByName(column = "class_title")
    private String classTitle;

    @CsvBindByName(column = "gender")
    private String gender;

    @CsvBindByName(column = "ethnicity")
    private String ethnicity;

    @CsvBindByName(column = "monthly")
    private Double monthlySalary;
}
