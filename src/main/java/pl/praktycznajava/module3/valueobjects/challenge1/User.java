package pl.praktycznajava.module3.valueobjects.challenge1;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class User {

    String id;
    String name;
    String surname;
    LocalDate dateOfBirth;
    Address address;

}
