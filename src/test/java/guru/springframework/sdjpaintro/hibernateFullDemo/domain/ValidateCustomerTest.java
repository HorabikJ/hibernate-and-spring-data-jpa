package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class ValidateCustomerTest {

    @Test
    public void shouldFailValidation() {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Address address = new Address(
                    "too long address                                                1234",
                    "             ",
                    "too long state                                                      123",
                    "");

            Customer customer = new Customer(
                    "too long name                                                               ",
                    address,
                    "too long phone                                                             ",
                    "");

            Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

            assertThat(constraintViolations).size().isEqualTo(7);
        }
    }
}
