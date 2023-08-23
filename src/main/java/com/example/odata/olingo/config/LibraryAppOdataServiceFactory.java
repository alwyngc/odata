package com.example.odata.olingo.config;

import org.springframework.stereotype.Component;

@Component
public class LibraryAppOdataServiceFactory extends CustomODataServiceFactory{
    //need this wrapper class for the spring framework, otherwise we face issues when auto wiring directly the CustomODataServiceFactory
}
