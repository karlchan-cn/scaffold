package com.scaffold.heracles.web.controller;

import com.scaffold.heracles.domain.ConfigExcelModel;
import com.scaffold.heracles.service.ExcelExtractor;
import com.scaffold.heracles.service.HeraclesConfExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController(value = "HelloController")
public class HeraclesExtractController {

    @Value("${ad-show-gateway-service.secrect}")
    private String secrect;
    @Autowired
    private Environment environment;
    @Autowired
    private HeraclesConfExtractService heraclesConfExtractService;

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!secrect is :" + secrect + "," +
                "env screct is:" +
                environment.getProperty("ad-show-gateway-service.secrect");
    }

    @GetMapping("/properties/details")
    public void getPropertiesDetailsByAppId(@RequestParam(value = "appId") String appId, @RequestParam(value = "isTest") boolean isTest,
                                            HttpServletResponse response) {
        final List<ConfigExcelModel> result = heraclesConfExtractService.getAllPropertiesByAppID(appId, isTest);
        ExcelExtractor.writeExcel(response, result, ConfigExcelModel.class, appId, appId + "-details-" + System.currentTimeMillis() + ".xls");
    }

    @GetMapping("/properties/list/compressed")
    public void listAllCompressableProperties(@RequestParam(value = "appId") String appId, @RequestParam(value = "isTest") boolean isTest,
                                              HttpServletResponse response) {
        ExcelExtractor.writeExcel(response, heraclesConfExtractService.listAllCompressableProperties(appId, isTest),
                ConfigExcelModel.class, appId, appId + "-compressable-" + System.currentTimeMillis() + ".xls");
    }

}