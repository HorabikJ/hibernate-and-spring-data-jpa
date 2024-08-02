package guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.callback;

import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.encoding.service.EncodingService;
import guru.springframework.sdjpaintro.interceptorsAndListenersAndJPACallbacks.jpaCallbacksAndConverters.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class EmployeeJpaCallback {

    private final EncodingService encodingService;

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(Employee employee) {
        log.info("Before insert or update");
        employee.setDataToEncode(encodingService.encode(employee.getDataToEncode()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void afterLoad(Employee employee) {
        log.info("After load");
        employee.setDataToEncode(encodingService.decode(employee.getDataToEncode()));
    }

}
