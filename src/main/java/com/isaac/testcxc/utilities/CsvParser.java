package com.isaac.testcxc.utilities;

import com.isaac.testcxc.entities.CsvCatalog;
import com.isaac.testcxc.entities.CsvEmployee;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Slf4j
@Component
public class CsvParser {

    public List<CsvCatalog> parseToCatalog(MultipartFile file) {

        return this.parse(file, ';', CsvCatalog.class);
    }

    public List<CsvEmployee> parseToEmployee(MultipartFile file) {

        return this.parse(file, ',', CsvEmployee.class);
    }

    private <T> List<T> parse(MultipartFile file, char separator, Class<T> type) {

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                .withType(type)
                .withIgnoreLeadingWhiteSpace(true)
                .withSeparator(separator)
                .withIgnoreEmptyLine(true)
                .build();

            return csvToBean.parse();
        } catch (Exception ex) {
            log.error("Failed to parse csv ", ex);

            throw new IllegalArgumentException(ex.getCause().getMessage());
        }
    }
}
