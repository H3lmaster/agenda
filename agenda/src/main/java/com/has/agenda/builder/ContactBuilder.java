package com.has.agenda.builder;

import com.has.agenda.model.Category;
import com.has.agenda.model.CategoryName;
import com.has.agenda.model.Contact;
import com.has.agenda.model.FamilyRelationship;

public class ContactBuilder {

    private String name;
    private String surname;
    private String phoneNumber;
    private String email;
    private int age;
    private String hairColor;
    private Category category;
    private CategoryName categoryName;
    private FamilyRelationship categoryDescription;
    private int yearsOfFriendship;
    
    public ContactBuilder withName(String name)  {
        this.name = name;
        return this;
    }
    
    public ContactBuilder withSurname(String surname)  {
        this.surname = surname;
        return this;
    }
    
    public ContactBuilder withPhoneNumber(String phoneNumber)  {
        this.phoneNumber = phoneNumber;
        return this;
    }
    
    public ContactBuilder withEmail(String email)  {
        this.email = email;
        return this;
    }
    
    public ContactBuilder withCategoryName(CategoryName categoryName)  {
        this.categoryName = categoryName;
        return this;
    }
    
    public ContactBuilder withFamilyRelationship(FamilyRelationship categoryDescription)  {
        this.categoryDescription = categoryDescription;
        return this;
    }
    public ContactBuilder withYearsOfFriendship(int yearsOfFriendship)  {
        this.yearsOfFriendship = yearsOfFriendship;
        return this;
    }
    
    public Contact build() {
        category = new Category(categoryName, categoryDescription, yearsOfFriendship);
        return new Contact(name, surname, phoneNumber, email, age, hairColor, category);
    }
    
    public String toString() {
        return "Name: " + this.name + ", Surname: " + this.surname + ", Phone: " + this.phoneNumber + ", Email: " + this.email;
    }
}
