package guru.springframework.sdjpaintro.hibernateFullDemo.service.optimisticlocking;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.Customer;
import guru.springframework.sdjpaintro.hibernateFullDemo.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class OptimisticLockingHelper {

    private CustomerRepository customerRepository;

    @Transactional
    public void updateCustomerTransactional(Long customerId, String newName, long milisToWait) {
        updateCustomer(customerId, newName, milisToWait);
    }

    public void updateCustomerNoTransactional(Long customerId, String newName, long milisToWait) {
        updateCustomer(customerId, newName, milisToWait);
    }

    @SneakyThrows
    void updateCustomer(Long customerId, String newName, long milisToWait) {
        Customer customer = customerRepository.getById(customerId);
        Thread.sleep(milisToWait);
        customer.setName(newName);
        customerRepository.save(customer);
    }

}
