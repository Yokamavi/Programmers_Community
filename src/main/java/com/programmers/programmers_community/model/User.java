package com.programmers.programmers_community.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

// Model class: it represents a user of the app
// Lombok writes the getters, setters, toString, equals and hashCode for us (@Data)
@Data
// Empty constructor, JPA needs it to create the objects when it reads them from the database
@NoArgsConstructor
// Constructor with the four attributes: new User(username, password, gender, language)
@AllArgsConstructor
// @Entity means that the objects of this class are saved in the database
@Entity
public class User {
    // The username is the primary key, so there can't be two users with the same one
    @Id
    private String username;
    // TODO: the password is saved as plain text, it should be saved hashed (for example with BCrypt)
    private String password;
    private String gender;
    private String language;
}