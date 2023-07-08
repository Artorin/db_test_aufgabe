package com.example.db_test;

import java.io.File;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

@Component
public class XmlParser {


    public Map findPosition(String starts_with, int train_number, int wagen_number) {

        Map<String,String[]> return_sect = new HashMap<>();
        int index = 0;

        String directory = "src/main/resources/dataInput/";
        String ends_with = ".xml";

        File dir = new File(directory);
        File[] found_files = dir.listFiles((dir1, name) -> name.startsWith(starts_with +"_") && name.endsWith(ends_with));

        if (Arrays.stream(found_files).findAny().isEmpty()) {
            System.out.println("no files found");
        }
        else {
            try {

                SAXReader reader = new SAXReader();
                Document document = reader.read( found_files[0] );

                List<Node> trains_with_number_found = new ArrayList<>();
                List<Node> list_of_all_trains = document.selectNodes("//trainNumber");

                for (Node node_train_num : list_of_all_trains) {

                    if (node_train_num.hasContent()) {
                        if (node_train_num.getText().equals(String.valueOf(train_number)) ) {
                            trains_with_number_found.add(node_train_num);
                            //take only first occurrence
                            //break;
                        }
                        else {
                            System.out.println("wrong train number, looking for further");
                        }
                    }
                }

                for (Node node_train : trains_with_number_found) {
                    List<Node> nodes_waggons = node_train.getParent().getParent().selectNodes("./waggons/waggon");
                    // pro train all wagons
                    List<String> lst_of_identifiers = new ArrayList<>();
                    // waggons
                    for (Node node : nodes_waggons) {
                        Node node_wagon_number = node.selectSingleNode("./number"); // by convention always one number waggon have

                        if (node_wagon_number.hasContent() ) { // only if have value
                            if (node_wagon_number.getText().equals(String.valueOf(wagen_number))   ) { // was with int exception if empty
                                index +=1;
                                List<Node> nds_identifier = node_wagon_number.getParent().selectNodes("./sections/identifier");

                                for (Node node_ident : nds_identifier) {
                                    lst_of_identifiers.add(node_ident.getText());
                                }

                                String[] arr_nodes = lst_of_identifiers.toArray(new String[lst_of_identifiers.size()]);

                                if (return_sect.isEmpty()) {
                                    return_sect.put("sections",arr_nodes);
                                } else {
                                    return_sect.put("sections" + String.valueOf(index),arr_nodes);
                                }

                                return_sect.forEach((key, value) -> System.out.println(key + " " + value.length));

                                // find only one and ok - if unique in xml
                                //break;
                            }
                        }
                    }
                    if (lst_of_identifiers.isEmpty()) {
                        System.out.println("err: not found waggon number cos number was empty or wrong input val");
                    }
                }
            }
            catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        // print map
        System.out.println("result: ");
        return_sect.forEach((key, value) -> System.out.println(key + " " + value.length));

        return return_sect;
    }

    //
    //debug
    public static void main(String[] args) {

        // input vars
        String starts_with = "AA"; // ABCH
        int train_number = 71; //179
        int wagen_number = 1; //200

        Map<String,String[]> return_sect = new HashMap<>();
        int index = 0;


        String directory = "src/main/resources/dataInput/";
        String ends_with = ".xml";

        File dir = new File(directory);
        File[] found_files = dir.listFiles((dir1, name) -> name.startsWith(starts_with +"_") && name.endsWith(ends_with));

        System.out.println("files: ");
        System.out.println(Arrays.toString(found_files));

        if (found_files.length < 1)
        {System.out.println("no files found");}
        else {
            try {
                //File inputFile = new File("src/main/resources/dataInput/" + name_file);// found_file

                SAXReader reader = new SAXReader();
                Document document = reader.read( found_files[0] );

                System.out.println("Root element :" + document.getRootElement().getName());

                List<Node> nodes_of_trains_number = new ArrayList<>();
                List<Node> list_of_trains = document.selectNodes("//trainNumber");

                for (Node node_train_num : list_of_trains) {
                    System.out.println(node_train_num.getText() + node_train_num.getName());

                    if (node_train_num.hasContent()) {
                        if (node_train_num.getText().equals(String.valueOf(train_number)) ) {
                            nodes_of_trains_number.add(node_train_num);
                            //take only first occurrence
                            //break;
                        }
                        else {
                            System.out.println("err not found train number");
                        }
                    }
                }
                System.out.println("nodes_of_trains_number size:");
                System.out.println(nodes_of_trains_number.size());

                System.out.println("name");
                System.out.println(nodes_of_trains_number.get(0).getParent().getParent().getName());
                //System.out.println(nodes_of_trains_number.get(0).getParent().getParent().getText());

                for (Node node_train : nodes_of_trains_number) {
                    List<Node> nodes_waggons = node_train.getParent().getParent().selectNodes("./waggons/waggon");
                    // pro train all wagons
                    List<String> lst_of_identifiers = new ArrayList<>();
                    // waggons
                    for (Node node : nodes_waggons) {
                        Node node_wagon_number = node.selectSingleNode("./number"); // by convention always one number waggon have
                        System.out.println(node_wagon_number.getName() + node_wagon_number.getText());


                        if (node_wagon_number.hasContent() ) { // only if have value
                            if (node_wagon_number.getText().equals(String.valueOf(wagen_number))   ) { // was with int exception if empty
                                index +=1;
                                List<Node> nds_identifier = node_wagon_number.getParent().selectNodes("./sections/identifier");

                                System.out.println("node wagon number: ");
                                System.out.println(nds_identifier.get(0).getName() + " " + nds_identifier.get(0).getText());


                                for (Node node_ident : nds_identifier) {
                                    System.out.println("identifier: ");
                                    System.out.println(node_ident.getText());
                                    lst_of_identifiers.add(node_ident.getText());
                                }


                                String[] arr_nodes = lst_of_identifiers.toArray(new String[lst_of_identifiers.size()]);

                                if (return_sect.isEmpty()) {
                                    return_sect.put("sections",arr_nodes);
                                    System.out.println("ret sec");
                                } else {
                                    return_sect.put("sections" + String.valueOf(index),arr_nodes);
                                }


                                return_sect.forEach((key, value) -> System.out.println(key + " " + value.length));

                                System.out.println("size of list");
                                System.out.println(lst_of_identifiers.size());

                                // find only one and ok
                                //break;
                            }
                        }
                    }
                    if (lst_of_identifiers.isEmpty()) {
                        System.out.println("err: not found waggon number cos number was empty or wrong input val");
                    }
                }
            }
            catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        // print map
        System.out.println("result: ");
        return_sect.forEach((key, value) -> System.out.println(key + " " + value.length));
    }
}
