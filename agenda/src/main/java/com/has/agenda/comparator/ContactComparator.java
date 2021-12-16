package com.has.agenda.comparator;

import java.util.Comparator;

import com.has.agenda.model.Contact;

public class ContactComparator implements Comparator<Contact> {

    @Override
    public int compare(Contact co1, Contact co2) {
        int compareSurname = co1.getSurname().compareTo(co2.getSurname());
        
        if ( compareSurname != 0 ) {
            return compareSurname;
        }
        
        return co1.getName().compareTo(co2.getName());
    }

}
