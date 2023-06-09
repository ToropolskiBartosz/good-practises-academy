package pl.praktycznajava.module3.valueobjects.challenge1;

import lombok.Value;

@Value(staticConstructor = "of")
public class Address {
    String street;
    String postalCode;
    String city;
    String country;
}
