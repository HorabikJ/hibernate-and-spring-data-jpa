package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
