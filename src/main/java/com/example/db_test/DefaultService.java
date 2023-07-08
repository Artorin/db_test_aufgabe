package com.example.db_test;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultService {
    private XmlParser xmlParser;

    DefaultService(XmlParser xmlParser) {
        this.xmlParser = xmlParser;
    }

    Map findSections(String ril100, int trainNumber, int number) {
        //test
        //HashMap<String, String[]> hm = new HashMap<String, String[]>();
        //hm.put("sections", new String[]{"A", "B", "C"});
        //return hm;

        return xmlParser.findPosition(ril100, trainNumber,number);


    }
}
