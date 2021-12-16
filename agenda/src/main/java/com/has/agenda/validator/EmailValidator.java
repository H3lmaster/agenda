package com.has.agenda.validator;

import java.util.Objects;

public class EmailValidator {


    public static boolean isValidEmail(String input) {
        boolean result = false;
       
        if ( !Objects.isNull(input) && !input.isEmpty() ) {
            result = org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(input);
        } 
        
        return result;
    }
    
    
}
