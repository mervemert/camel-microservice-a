package com.in28minutes.microservices.camelmicroservicea.routes.a;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Component
public class MyFirstTimerRouter extends RouteBuilder {

    @Autowired
    private GetCurrentTimeBean getCurrentTimeBean;

    @Autowired
    private SimpleLoggingProcessingComponent loggingComponent;

    @Override
    public void configure() throws Exception {
        //create routes
        //timer
        //transformation
        //database
        from("timer:first-timer") //timer endpoint
                .log("${body}")
                .transform().constant("My Constant Message")
                .log("${body}")
                //.transform().constant("Time now is " + LocalDateTime.now())
                
                //transformation yapması için spring bean'i kullanıyoruz
                //bu bean bu spesific port'a her mesaj geldiğinde çağırılacak
                .bean(getCurrentTimeBean)
                .log("${body}")
                .bean(loggingComponent)
                .log("${body}")
                .process(new SimpleLoggingProcessor())
                .to("log:first-timer"); //log endpoint
    }
}


@Component
class GetCurrentTimeBean {
    public String getCurrentTime() {
        return "Time now is " +LocalDateTime.now();
    }
}

@Component
class SimpleLoggingProcessingComponent {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessingComponent.class);

    public void process(String message) { //processinf component'in bir sey return etmesine gerek yok
        logger.info("SimpleLoggingProcessingComponent {}", message);
    }
}


//Component annotation'ı koymadık çünkü doğrudan bir instance oluşturucaz.
class SimpleLoggingProcessor implements org.apache.camel.Processor {
    private Logger logger = LoggerFactory.getLogger(SimpleLoggingProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("SimpleLoggingProcessingComponent {}", exchange.getMessage().getBody());
    }
}





