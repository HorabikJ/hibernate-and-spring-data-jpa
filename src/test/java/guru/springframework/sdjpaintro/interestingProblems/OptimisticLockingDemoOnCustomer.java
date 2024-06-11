package guru.springframework.sdjpaintro.interestingProblems;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Address;
import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Customer;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("local")
public class OptimisticLockingDemoOnCustomer {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void should_update_version_field_when_updating_customer() {
        Address address = new Address("xyz street", "xyz city", "xyz state", "xyz zip-code");
        Customer customer = new Customer("customer name", address, "phone number", "email");

        Customer customer0 = customerRepository.save(customer);

        assertThat(customer0.getVersion()).isEqualTo(0);

        customer0.setName("some different name");
        Customer customer1 = customerRepository.save(customer0);

        assertThat(customer1.getVersion()).isEqualTo(1);
        assertThat(customer0.getVersion()).isEqualTo(0);

        customer1.setName("some super different name");
        Customer customer2 = customerRepository.save(customer1);

        assertThat(customer2.getVersion()).isEqualTo(2);
        assertThat(customer1.getVersion()).isEqualTo(1);
        assertThat(customer0.getVersion()).isEqualTo(0);
    }

    @Test
    // This test is not @Transactional as it is in @SpringBootTest annotated class. So every `customerRepository` 
    // call inside this test runs in its own separate transaction. That is why optimistic locking demo is possible.
    public void should_throw_exception_when_updating_stale_entity() {
        Address address = new Address("xyz street", "xyz city", "xyz state", "xyz zip-code");
        Customer customer = new Customer("customer name", address, "phone number", "email");

        Customer customer0 = customerRepository.save(customer); //Saving customer for the first time, version set to 0.

        assertThat(customer0.getVersion()).isEqualTo(0);

        customer0.setName("some different name");
        customerRepository.save(customer0); //Updating customer name, version in db set to 1 as this is first update,
        // but below in the code we still use the reference to customer0 that has a version = 0.

        customer0.setName("some super different name");
        // Customer row in db has version = 1, because of the first name update. `customer0` reference in the code 
        // still has version = 0, as this is what was returned from fists save(). When we try to save the reference 
        // customer0 into the db, exception is thrown because of the version mismatch, optimistic locking worked.
        assertThatThrownBy(() -> customerRepository.save(customer0))
                .isInstanceOf(ObjectOptimisticLockingFailureException.class);
    }


}
