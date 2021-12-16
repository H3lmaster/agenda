package com.has.agenda.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.has.agenda.model.Contact;

public class ContactBuilderTest {

    @Test
    public void buildContactTest() {
        String contactName = "Saint";
        String contactSurname = "Joseph";
        String contactEmail = "joseph@mail.com";
        String contactPhone = "5113141223";
        
        ContactBuilder builder = new ContactBuilder();
        builder.withName(contactName); 
        builder.withSurname(contactSurname);
        builder.withEmail(contactEmail);
        builder.withPhoneNumber(contactPhone);
        Contact contact = builder.build();
        
        assertThat(contact.getName()).isEqualTo(contactName);
        assertThat(contact.getSurname()).isEqualTo(contactSurname);
        assertThat(contact.getEmail()).isEqualTo(contactEmail);
        assertThat(contact.getPhoneNumber()).isEqualTo(contactPhone);
        
    }
}
