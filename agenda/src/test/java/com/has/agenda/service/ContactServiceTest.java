package com.has.agenda.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.has.agenda.builder.ContactBuilder;
import com.has.agenda.model.Contact;

@SpringBootTest
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private FileService fileService;
    
    @BeforeEach
    public void setupTest() {
        System.out.println("Preparing the test");
        // Clears the file content
        this.fileService.clearContent();
    }
    
    @Test
    public void createContactTest() {
        ContactBuilder builder = new ContactBuilder();
        builder.withName("Kros");
        builder.withSurname("Lorenzo");
        Contact contact = builder.build();
        
        Contact contactResult = this.contactService.saveContact(contact);
        assertThat(contactResult).isNotNull();
    }
    
    @Test
    public void removeContactTest() throws Exception {
        // Build the contact
        ContactBuilder builder = new ContactBuilder();
        builder.withName("Kros");
        builder.withSurname("Lorenzo");
        builder.withEmail("test@test.com");
        Contact contactBuild = builder.build();    
        // Save the contact
        Contact contact = this.contactService.saveContact(contactBuild);
        assertThat(contact).isNotNull();
        // Remove the contact
        Contact contactRemoved = this.contactService.removeContact("test@test.com");
        assertThat(contactRemoved).isNotNull();
        assertThat(contactRemoved.getEmail()).isEqualTo(contactBuild.getEmail());
    }
    
    @Test
    public void removeContactErrorTest() throws Exception {
        
        try {
            this.contactService.removeContact("albert1@test.com");
        } catch (Exception ex) {
            assertThat(ex).isNotNull();
            assertThat(ex.getMessage()).isEqualTo("Contact not found.");
        }
        
    }
    
    @Test
    public void updateContactTest() throws Exception {
        
        ContactBuilder builder = new ContactBuilder();
        builder.withName("Hugo");
        builder.withSurname("Alves");
        builder.withEmail("hugo@alves.com"); 
        
        Contact savedContact = this.contactService.saveContact(builder.build());
        assertThat(savedContact).isNotNull();
        
        // Update contact details
        ContactBuilder builder2 = new ContactBuilder();
        builder2.withName("Alberto");
        builder2.withEmail("hugo.alves@updated.com");
        builder2.withSurname("Alves Zech");
        
        Contact updatedContact = this.contactService.updateContact(builder2.build(), "hugo@alves.com");
        
        assertThat(updatedContact).isNotNull();
        assertThat(updatedContact.getEmail()).isEqualTo("hugo.alves@updated.com");
        assertThat(updatedContact.getSurname()).isEqualTo("Alves Zech");
    }
    
    @Test
    public void displayContactsTest() {
        ContactBuilder builder = new ContactBuilder();
        builder.withName("Hugo");
        builder.withSurname("Alves");
        builder.withEmail("hugo@alves.com"); 
        
        ContactBuilder builder2 = new ContactBuilder();
        builder2.withName("Alberto");
        builder2.withSurname("Carlos");
        builder2.withEmail("carlos@mail.com"); 
        
        this.contactService.saveContact(builder.build());
        this.contactService.saveContact(builder2.build());
        
        List<Contact> contacts = this.contactService.getAll();
        assertThat(contacts).isNotNull();
        assertThat(contacts.size()).isEqualTo(2);
        assertThat(contacts.get(0).getEmail()).isEqualTo("hugo@alves.com");
    }
}
