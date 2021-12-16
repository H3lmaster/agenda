package com.has.agenda.console;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.has.agenda.builder.ContactBuilder;
import com.has.agenda.model.Contact;
import com.has.agenda.service.ContactService;
import com.has.agenda.validator.EmailValidator;

@Component
public class ContactConsoleReader {

    private static final Logger logger = LoggerFactory.getLogger(ContactConsoleReader.class);

    private static final String DECISION_YES = "Yy";

    private Scanner scanner;

    @Autowired
    private ContactService contactService;

    
    /**
     * Displays the prompt to create a new contact.
     */
    public void createContact() {
        Contact contact = readContactDetails(false);
        
        logger.info("Contact details: " + contact.toString());
        
        logger.info("Do you confirm the creation of this contact(Y - Yes, N - NO): ");
        String decision = scanner.next();
        if ( !Objects.isNull(decision) && DECISION_YES.contains(decision) && this.contactService.isValidContact(contact) ) {
            this.contactService.saveContact(contact);  
        }
    }
    
    /**
     * Displays the prompt to read the contact information.
     * @param isUpdate
     * @return
     */
    public Contact readContactDetails(boolean isUpdate) {
        ContactBuilder contactBuilder = new ContactBuilder();
        
        logger.info("Type the contact name: ");
        contactBuilder.withName(scanner.next());
        
        logger.info("Type the contact surname: ");
        String surname = scanner.next();
        surname += scanner.nextLine(); 
        contactBuilder.withSurname(surname);
        
        logger.info("Type the contact phone number: ");
        contactBuilder.withPhoneNumber(scanner.next());
        
        while(true) {
            logger.info("Type the contact email: ");
            String email = scanner.next();
            if ( EmailValidator.isValidEmail(email) ) {
                Contact contact = this.contactService.getContactByEmail(email);
                if ( !Objects.isNull(contact) && !isUpdate ) {
                    logger.warn("A contact with this email already exists.");
                } else {
                    contactBuilder.withEmail(email);
                    break;
                }
            } else {
                logger.warn("Invalid email.");
            }
        }
        
        return contactBuilder.build();
    }
    
    /**
     * Display all the contacts ordered by surname.
     */
    public void displayContacts() {
        List<Contact> contacts = this.contactService.getAll();
        for ( Contact contact : contacts ) {
            logger.info(contact.toString());
        }
    }

    /**
     * Show the prompt to read the contact details in order to update.
     * 
     */
    public void updateContact() {
        try {
            String email = this.readContactEmail();
            Contact contact = this.contactService.getContactByEmail(email);
            if ( Objects.isNull(contact) ) {
                logger.warn("Contact not found.");
            } else {
                Contact contactUpdated = readContactDetails(true);
                logger.info("Do you confirm the changes in the contact(Y - Yes, N - NO): ");
                String decision = scanner.next();
                if ( !Objects.isNull(decision) && DECISION_YES.contains(decision) && this.contactService.isValidContact(contactUpdated) ) {
                    this.contactService.updateContact(contactUpdated, email);   
                }
            }
        } catch (Exception e) {
            logger.warn("Error while updating the contact...");
        }
    }

    /**
     * Shows the prompt to read the contact email in order
     * to remove the contact.
     * 
     */
    public void removeContact() {
        try {
            
            String email = this.readContactEmail();
            Contact contact = this.contactService.getContactByEmail(email);
            if ( !Objects.isNull(contact) ) {
                logger.info("Do you confirm you want to remove the contact(Y - Yes, N - NO): ");
                String decision = scanner.next();
                if ( !Objects.isNull(decision) && DECISION_YES.contains(decision) ) {
                    this.contactService.removeContact(email);  
                }
            } else {
                logger.warn("Contact not found.");
            }

        } catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
    }

    private String readContactEmail() {
        String email = null;

        logger.info("Type the contact email: ");
        email = scanner.next();

        return email;
    }

    /**
     * System's main menu. Displays all the options available in the system.
     * 
     */
    public int captureMenuOption() {

        int menuOption = 0;

        do {
            try {
                logger.info("Please, choose one of the options below: ");
                logger.info("[1] - Create contact ");
                logger.info("[2] - Update contact ");
                logger.info("[3] - Remove contact ");
                logger.info("[4] - Display contacts ");
                logger.info("[5] - Exit ");
                menuOption = Integer.parseInt(scanner.next());   
            } catch (Exception ex) {
                logger.warn("The input must be a number between 1 and 5.");
                menuOption = 0;
            }
        } while (menuOption < 1 || menuOption > 5);

        return menuOption;
    }

    /**
     * Opens the scanner and keeps the same instance during all execution.
     * 
     */
    public void initConsoleReader() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Closes the scanner once the system is terminated.
     * 
     */
    public void closeConsoleReader() {
        try {
            if ( scanner != null ) {
                scanner.close();
            }
        } catch (Exception ex) {
            logger.error("An error occurred while closing the console reader...", ex);
        }

    }

}
