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
public class Category {
    
    private CategoryName name;
    private FamilyRelationship description;
    private int yearsOfFriendship;

}
