package com.has.agenda.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    
    private CategoryName name;
    private FamilyRelationship description;
    private int yearsOfFriendship;

}
