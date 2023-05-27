package com.example.sample1app;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

    private boolean onlyNumber;

    @Override
    public void initialize(Phone phone) {
        // TODO 自動生成されたメソッド・スタブ
        onlyNumber = phone.onlyNumber();
    }

    @Override
    public boolean isValid(String input, ConstraintValidatorContext context) {
        // TODO 自動生成されたメソッド・スタブ
        if (input == null) {
            return false;
        }
        if (onlyNumber) {
            return input.matches("[0-9]*");
        } else {
            return input.matches("[0-9()-]*");
        }
    }

}
