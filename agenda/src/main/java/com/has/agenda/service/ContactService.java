package com.has.agenda.service;

import java.util.List;

import com.has.agenda.model.Contact;

public interface ContactService {

    public Contact saveContact(Contact contact);
    public List<Contact> getAll();
    public Contact removeContact(String email) throws Exception;
    public Contact updateContact(Contact contact, String email) throws Exception;
    public Contact getContactByEmail(String email);
    public void sort(List<Contact> contacts);
    public boolean isValidContact(Contact contact);
}
