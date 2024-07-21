package com.isaac.testcxc.controllers;

import com.isaac.testcxc.entities.CsvCatalog;
import com.isaac.testcxc.responses.CatalogResult;
import com.isaac.testcxc.services.CatalogService;
import com.isaac.testcxc.utilities.CsvParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private CsvParser csvParser;
    private CatalogService catalogService;

    @Autowired
    public CatalogController(CsvParser csvParser, CatalogService catalogService) {
        this.csvParser = csvParser;
        this.catalogService = catalogService;
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<CatalogResult> upload(@RequestPart("file") MultipartFile file) {

        log.info("Start catalog upload");

        List<CsvCatalog> catalogs = csvParser.parseToCatalog(file);
        CatalogResult result = catalogService.saveCatalog(catalogs.stream());

        return Mono.just(result);
    }

}
