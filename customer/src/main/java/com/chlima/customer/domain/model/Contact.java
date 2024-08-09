package com.chlima.customer.domain.model;

import com.chlima.customer.domain.model.base.ValueObject;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Embeddable
public class Contact implements ValueObject {
    private String email;
    private String phone;

    protected Contact(){}

    public static Contact create(String email, String phone){
        validate(email, phone);
        Contact contact = new Contact();
        contact.setEmail(email);
        contact.setPhone(phone);
        return contact;
    }
    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean isValidPhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+[1-9]{1}[0-9]{1,14}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    private static void validate(String email, String phone) {
        if ( !isValidEmail(email) ) throw new IllegalArgumentException("Invalid email");
        if ( !isValidPhone(phone) ) throw new IllegalArgumentException("Invalid phone");
    }
}
