package com.isaac.testcxc.integration;

import com.isaac.testcxc.controllers.CatalogController;
import com.isaac.testcxc.entities.Agency;
import com.isaac.testcxc.entities.Ethnicity;
import com.isaac.testcxc.entities.Gender;
import com.isaac.testcxc.entities.Profession;
import com.isaac.testcxc.repositories.AgencyRepository;
import com.isaac.testcxc.repositories.EthnicityRepository;
import com.isaac.testcxc.repositories.GenderRepository;
import com.isaac.testcxc.repositories.ProfessionRepository;
import com.isaac.testcxc.responses.CatalogResult;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

@SpringBootTest
@Transactional
public class CatalogIntegrationTest {

    @Autowired
    CatalogController controller;

    @Autowired
    AgencyRepository agencyRepository;

    @Autowired
    EthnicityRepository ethnicityRepository;

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    ProfessionRepository professionRepository;

    @Test
    void whenUploadCatalog_persistValuesInDB() {
        String csv = """
agency_name;class_title;ethnicity;gender;
AGENCY A;TEACHER;HISPANIC;MALE;
AGENCY B;WRITER;WHITE;FEMALE;
AGENCY C;FIREFIGHTER;BLACK;OTHER;
        """;

        CatalogResult res = controller.upload(new MockMultipartFile("file", csv.getBytes())).block();
        Assertions.assertThat(res).isNotNull();

        List<Agency> agencies = agencyRepository.findAll();
        Assertions.assertThat(agencies).isNotNull();
        Assertions.assertThat(agencies.stream().map(Agency::getName)).contains("AGENCY A", "AGENCY B", "AGENCY C");

        List<Ethnicity> ethnicities = ethnicityRepository.findAll();
        Assertions.assertThat(ethnicities).isNotNull();
        Assertions.assertThat(ethnicities.stream().map(Ethnicity::getName)).contains("HISPANIC", "WHITE", "BLACK");

        List<Gender> genders = genderRepository.findAll();
        Assertions.assertThat(genders).isNotNull();
        Assertions.assertThat(genders.stream().map(Gender::getName)).contains("MALE", "FEMALE", "OTHER");

        List<Profession> professions = professionRepository.findAll();
        Assertions.assertThat(professions).isNotNull();
        Assertions.assertThat(professions.stream().map(Profession::getName)).contains("TEACHER", "WRITER", "FIREFIGHTER");
    }

    @Test
    void uploadCatalog_whenValuesAreEmpty_doesNothing() {
        String csv = """
agency_name;class_title;ethnicity;gender;
;;;;
;;;;
;;;;
        """;

        CatalogResult res = controller.upload(new MockMultipartFile("file", csv.getBytes())).block();
        Assertions.assertThat(res).isNotNull();

        List<Agency> agencies = agencyRepository.findAll();
        Assertions.assertThat(agencies).isEmpty();

        List<Ethnicity> ethnicities = ethnicityRepository.findAll();
        Assertions.assertThat(ethnicities).isEmpty();

        List<Gender> genders = genderRepository.findAll();
        Assertions.assertThat(genders).isEmpty();

        List<Profession> professions = professionRepository.findAll();
        Assertions.assertThat(professions).isEmpty();
    }

}
