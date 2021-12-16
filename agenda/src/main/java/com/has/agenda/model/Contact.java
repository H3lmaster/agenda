package com.has.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper=false, includeFieldNames=true)
public class Contact {
    
    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private int age;
    private String hairColor;
    private Category category;

}
