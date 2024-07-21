package com.isaac.testcxc.entities;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class CsvCatalog {
    @CsvBindByName(column = "agency_name")
    private String agencyName;

    @CsvBindByName(column = "class_title")
    private String classTitle;

    @CsvBindByName(column = "ethnicity")
    private String ethnicity;

    @CsvBindByName(column = "gender")
    private String gender;
}
