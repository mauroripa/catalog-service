package com.polarbookshop.catalog_service;

import com.polarbookshop.catalog_service.config.PolarProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    /**
     * bean per accedere alle proprietà custom iniettate
     * attraverso l'autowiring del costruttore
     */
    private final PolarProperties polarProperties;

    /**
     * il costruttore di cui parlavamo
     * @param polarProperties il bean passato nel costruttore di HomeController
     */
    public HomeController(PolarProperties polarProperties) {
        this.polarProperties = polarProperties;
    }

    @GetMapping("/")
    public String getGreeting(){
        return polarProperties.getGreeting();
    }


}
