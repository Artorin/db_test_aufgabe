package com.example.db_test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class DefaultController {
    DefaultService DefaultService;

    DefaultController(DefaultService DefaultService) {
        this.DefaultService = DefaultService;
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "hello from home";
    }

    @GetMapping("station/{ril100}/train/{trainNumber}/waggon/{number}")
    public Map returnSmth(Model model, @PathVariable String ril100, @PathVariable int trainNumber, @PathVariable int number) {

        Map retVal = DefaultService.findSections(ril100,trainNumber,number);
        if (retVal.isEmpty()) throw new NumberNotFoundException();

        return retVal;
    }
}

