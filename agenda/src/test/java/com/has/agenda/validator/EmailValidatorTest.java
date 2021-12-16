package com.has.agenda.validator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class EmailValidatorTest {

    @Test
    public void isValidEmailTest() {
        boolean result = EmailValidator.isValidEmail("test@email.com");
        assertThat(result).isTrue();
    }
    
    @Test
    public void isWrongEmailTest() {
        boolean result = EmailValidator.isValidEmail("test.@email.com");
        assertThat(result).isFalse();
    }
}
