package com.in28minutes.microservices.camelmicroservicea.routes.b;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyFileRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("file:files/input") //pick up all the files from input folder
                .log("${body}")
        .to("file:files/output"); //move it to an output folder
    }
}



