package com.has.agenda.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.has.agenda.comparator.ContactComparator;
import com.has.agenda.model.Contact;
import com.has.agenda.validator.EmailValidator;

@Service
public class ContactServiceImpl implements ContactService {
    
    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);
    
    private static final String EMPTY =  "";
    private static final Gson gson = new Gson();
    
    @Autowired
    private FileService fileService;
    
    @Override
    public Contact saveContact(Contact contact) {
        fileService.write(gson.toJson(contact));
        return contact;
    }
    
    /**
     * Save all contacts again in the file, replacing the old values.
     * @param contacts
     */
    private void saveAll(List<Contact> contacts) {

        // Clear the file content ir oder to save all contacts again
        fileService.clearContent();
        
        if ( !Objects.isNull(contacts) && contacts.size() > 0 ) {
            // Save all contacts again replacing the old values
            for ( Contact contact : contacts ) {
                this.saveContact(contact);
            }
        }
    }

    @Override
    public List<Contact> getAll() {
        List<String> fileData = this.fileService.read();
        List<Contact> contacts = convertFromJsonListToContactList(fileData);
        this.sort(contacts);
        return contacts;
    }
    
    /**
     * Converts from Json list to contacts list.
     * @param jsonInputList
     * @return
     */
    private List<Contact> convertFromJsonListToContactList(List<String> jsonInputList) {
        List<Contact> contacts = new ArrayList<>();
        
        try {
            // Converts the data from json to contact object and insert in the list
            if ( jsonInputList != null && jsonInputList.size() > 0 ) {
                for ( String contactString : jsonInputList ) {
                    if ( contactString != null && !EMPTY.equals(contactString) ) {
                        Contact contact = gson.fromJson(contactString, Contact.class);
                        contacts.add(contact);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("An error occurred while converting the contacts...", ex);
        }
        
        return contacts;
    }

    @Override
    public Contact removeContact(String email) throws Exception {
        List<Contact> contacts = this.getAll();
        Contact contact = this.findByEmail(email, contacts);
        if ( Objects.isNull(contact) ) {
            throw new Exception("Contact not found.");
        }
        contacts.remove(contact);
        this.saveAll(contacts);
        return contact;
    }

    @Override
    public Contact getContactByEmail(String email) {
        
        List<Contact> contacts = this.getAll();
        Contact contact = this.findByEmail(email, contacts);
        
        return contact;
    }
    
    /**
     * Search, in the given contact list, the contact that has the email
     * passed by param.
     * @param email
     * @param contacts
     * @return
     */
    private Contact findByEmail(String email, List<Contact> contacts) {
        if ( !Objects.isNull(email) && !EMPTY.equals(email) 
                && !Objects.isNull(contacts) && contacts.size() > 0 ) {
            for ( Contact contact : contacts ) {
                if ( contact.getEmail().equals(email) ) {
                    return contact;
                }
            }
        }
        
        return null;
    }

    /**
     * Sort the contactss first by surname and then by name.
     */
    @Override
    public void sort(List<Contact> contacts) {
        Collections.sort(contacts, new ContactComparator());
    }

    /**
     * Updates the contact removed the old one from the list and adding the new one.
     * If the new contact email is already in use by another contact an exception 
     * will be thrown.
     */
    @Override
    public Contact updateContact(Contact contact, String email) throws Exception {
        List<Contact> contacts = this.getAll();
        // Checks if the current contact email exists 
        Contact contactToremove = this.findByEmail(email, contacts);
        if ( Objects.isNull(contactToremove) ) {
            throw new Exception("Contact not found.");
        }
        // Removes the contact from the list to be saved
        contacts.remove(contactToremove);
        
        // Checks if the new email is already in use
        Contact contactEmailAlreadyExits = this.findByEmail(contact.getEmail(), contacts);
        if ( !Objects.isNull(contactEmailAlreadyExits) ) {
            throw new Exception("Contact email already in use.");
        }
        // Add contact to the list to be saved
        contacts.add(contact);
        this.saveAll(contacts);
        return contact;
    }

    @Override
    public boolean isValidContact(Contact contact) {
        boolean result = true; 
        
        if ( Objects.isNull(contact) ) {
            result = false;
        } else {

            if ( Objects.isNull(contact.getName()) || EMPTY.equals(contact.getName()) ) {
                logger.warn("The contact name is invalid");
                result = false;
            }
            
            if ( Objects.isNull(contact.getSurname()) || EMPTY.equals(contact.getSurname()) ) {
                logger.warn("The contact surname is invalid");
                result = false;
            }
            
            if ( Objects.isNull(contact.getPhoneNumber()) || EMPTY.equals(contact.getPhoneNumber()) ) {
                logger.warn("The contact phone number is invalid");
                result = false;
            }
            
            if ( Objects.isNull(contact.getEmail()) || EMPTY.equals(contact.getEmail()) || !EmailValidator.isValidEmail(contact.getEmail()) ) {
                logger.warn("The contact email is invalid");
                result = false;
            }
        }
        
        return result;
    }

}
