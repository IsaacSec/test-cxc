package com.isaac.testcxc.services;

import com.isaac.testcxc.entities.*;
import com.isaac.testcxc.repositories.AgencyRepository;
import com.isaac.testcxc.repositories.EthnicityRepository;
import com.isaac.testcxc.repositories.GenderRepository;
import com.isaac.testcxc.repositories.ProfessionRepository;
import com.isaac.testcxc.responses.CatalogResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class CatalogService {

    private AgencyRepository agencyRepository;
    private EthnicityRepository ethnicityRepository;
    private GenderRepository genderRepository;
    private ProfessionRepository professionRepository;

    @Autowired
    public CatalogService(
        AgencyRepository agencyRepository,
        EthnicityRepository ethnicityRepository,
        GenderRepository genderRepository,
        ProfessionRepository professionRepository
    ) {
        this.agencyRepository = agencyRepository;
        this.ethnicityRepository = ethnicityRepository;
        this.genderRepository = genderRepository;
        this.professionRepository = professionRepository;
    }

    public CatalogResult saveCatalog(Stream<Catalog> catalogs) {
        List<Agency> agencies = new ArrayList<>();
        List<Ethnicity> ethnicities = new ArrayList<>();
        List<Gender> genders = new ArrayList<>();
        List<Profession> professions = new ArrayList<>();

        catalogs.forEach(c -> {
            if (this.isNotNullAndNotEmpty(c.getAgencyName())) {
                Agency agency = new Agency();
                agency.setName(c.getAgencyName());

                agencies.add(agency);
            }

            if (this.isNotNullAndNotEmpty(c.getEthnicity())) {
                Ethnicity ethnicity = new Ethnicity();
                ethnicity.setName(c.getEthnicity());

                ethnicities.add(ethnicity);
            }

            if (this.isNotNullAndNotEmpty(c.getGender())) {
                Gender gender = new Gender();
                gender.setName(c.getGender());

                genders.add(gender);
            }

            if (this.isNotNullAndNotEmpty(c.getClassTitle())) {
                Profession profession = new Profession();
                profession.setName(c.getClassTitle());

                professions.add(profession);
            }
        });

        agencyRepository.saveAll(agencies);
        ethnicityRepository.saveAll(ethnicities);
        genderRepository.saveAll(genders);
        professionRepository.saveAll(professions);

        CatalogResult catalogResult = new CatalogResult();

        catalogResult.setAgencies(agencies.stream().map(Agency::getName).toList());
        catalogResult.setNewAgencies(agencies.size());

        return catalogResult;
    }

    private boolean isNotNullAndNotEmpty(String s) {
        return s != null && !s.isEmpty();
    }
}
