package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.Address;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderHeader;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderLine;
import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.OrderStatus;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderHeaderRepository orderHeaderRepository;
    private OrderLineRepository orderLineRepository;

    @Transactional
    public OrderHeader saveOrderHeader(String customer,
                                       Address billingAddress,
                                       Address shippingAddress,
                                       Set<OrderLine> orderLines) {
//      We do not have to first save Set<OrderLine>, as this field has CascadeType.PERSIST in OrderHeader entity. 
//      List<OrderLine> savedOrderLines = orderLineRepository.saveAll(orderLines);
        OrderHeader orderHeader = new OrderHeader(
                customer,
                shippingAddress,
                billingAddress,
                OrderStatus.NEW,
                Set.copyOf(orderLines));
        return orderHeaderRepository.save(orderHeader);
    }

    @Transactional(readOnly = true)
    public OrderHeader fetchOrderHeaderById(Long id) {
        return orderHeaderRepository.getById(id);
    }

}
