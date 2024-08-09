package com.chlima.customer.domain.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter(AccessLevel.PRIVATE)
@Embeddable
public class Address {

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String zip;

    protected Address(){}

    public static Address create(String street, String number, String neighborhood, String city, String state, String zip){
        validate(zip);
        Address address = new Address();
        address.setStreet(street);
        address.setNumber(number);
        address.setNeighborhood(neighborhood);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        return address;
    }


    private static void validate(String zip) {
        if (!isValidZip(zip)) throw new IllegalArgumentException("Invalid ZIP code");
    }

    private static boolean isValidZip(String zip) {
        Pattern pattern = Pattern.compile("\\d{5}[-]*\\d{3}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(zip);
        return matcher.find();
    }

    public static class Builder {
        Address address = new Address();

        public Builder street(String street) {
            this.address.setStreet(street);
            return this;
        }

        public Builder number(String number) {
            this.address.setNumber(number);
            return this;
        }

        public Builder neighborhood(String neighborhood) {
            this.address.setNeighborhood(neighborhood);
            return this;
        }

        public Builder city(String city) {
            this.address.setCity(city);
            return this;
        }

        public Builder state(String state) {
            this.address.setState(state);
            return this;
        }

        public Builder zip(String zip) {
            this.address.setZip(zip);
            return this;
        }
        public Address build() {
            validate(address.getZip());
            return address;
        }


    }

}
