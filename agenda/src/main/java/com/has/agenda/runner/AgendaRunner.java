package com.has.agenda.runner;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.has.agenda.console.ContactConsoleReader;

@Component
public class AgendaRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(AgendaRunner.class);
    
    @Autowired
    private ContactConsoleReader contactConsoleReader;
    
    @Override
    public void run(String... args) throws Exception {
       
        if ( !Objects.isNull(args) && args.length > 0 ) {
            switch(args[0]) {
                case "run":
                    this.runAgenda();
                    break;
                default:
                    logger.info("Available execution options: run");
            }
        } else {
            logger.warn("You need to inform the arguments. Available argument options: run");
        }
        
       
    }
    
    private void runAgenda() {
        int option = 0;
        
        do {
            
            this.contactConsoleReader.initConsoleReader();
            option = this.contactConsoleReader.captureMenuOption();
            
            switch(option) {
                case 1:
                    logger.info("Creating new contact...");
                    this.contactConsoleReader.createContact();
                    break;
                case 2:
                    logger.info("Updating contact...");
                    this.contactConsoleReader.updateContact();
                    break;
                case 3:
                    logger.info("Removing contact...");
                    this.contactConsoleReader.removeContact();
                    break;
                case 4:
                    logger.info("Displaying the contacts...");
                    this.contactConsoleReader.displayContacts();
                    break;
                case 5:
                    logger.info("Exiting the system...");
                    break;
            }
        } while(option != 5);
        
        this.contactConsoleReader.closeConsoleReader();
    }

}
