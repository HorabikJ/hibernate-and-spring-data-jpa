package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.repository;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacks.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
