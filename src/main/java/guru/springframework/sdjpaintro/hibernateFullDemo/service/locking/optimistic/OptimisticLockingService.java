package guru.springframework.sdjpaintro.hibernateFullDemo.service.locking.optimistic;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Address;
import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Customer;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OptimisticLockingService {

    // To be honest I do not know how to invoke a situation with OptimisticLocking Exception from a application code 
    // using threads. As the below 2 methods `optimisticLockingTest_NoTransactional` and 
    // `optimisticLockingTest_Transactional` does not work. 

    private CustomerRepository customerRepository;
    private OptimisticLockingHelper optimisticLockingHelper;

    public void optimisticLockingTest_NoTransactional() throws Exception {
        Customer customer = customerRepository.save(createCustomer());

        Runnable r1 = () -> optimisticLockingHelper.updateCustomerNoTransactional(
                customer.getId(),
                "new name 1",
                3000L);
        Runnable r2 = () -> optimisticLockingHelper.updateCustomerNoTransactional(
                customer.getId(),
                "new name 2",
                0L);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        Thread.sleep(1000L);
        t2.start();

        t1.join();
        t2.join();
    }

    public void optimisticLockingTest_Transactional() throws Exception {
        Customer customer = customerRepository.save(createCustomer());

        Runnable r1 = () -> optimisticLockingHelper.updateCustomerTransactional(
                customer.getId(),
                "new name 1",
                3000L);
        Runnable r2 = () -> optimisticLockingHelper.updateCustomerTransactional(
                customer.getId(),
                "new name 2",
                0L);

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.start();
        Thread.sleep(1000L);
        t2.start();

        t1.join();
        t2.join();
    }

    private static Customer createCustomer() {
        Address address = new Address("xyz street", "xyz city", "xyz state", "xyz zip-code");
        return new Customer("customer name", address, "phone number", "email");
    }

}
