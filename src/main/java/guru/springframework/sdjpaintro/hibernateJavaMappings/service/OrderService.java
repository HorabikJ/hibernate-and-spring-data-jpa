package guru.springframework.sdjpaintro.hibernateJavaMappings.service;

import guru.springframework.sdjpaintro.hibernateJavaMappings.domain.*;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.CustomerRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderHeaderRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.OrderLineRepository;
import guru.springframework.sdjpaintro.hibernateJavaMappings.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderHeaderRepository orderHeaderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderLineRepository orderLineRepository;

    @Transactional
    public OrderHeader saveOrderHeader(Long customerId,
                                       Address billingAddress,
                                       Address shippingAddress,
                                       List<ImmutablePair<Long, Integer>> productQuantityList) {
//      We do not have to first save Set<OrderLine>, as this field has CascadeType.PERSIST in OrderHeader entity. 
//      List<OrderLine> savedOrderLines = orderLineRepository.saveAll(orderLines);

        Customer customer = customerRepository.getById(customerId);

        List<OrderLine> orderLines = productQuantityList.stream()
                .map(pair -> new OrderLine(pair.getRight(), productRepository.getById(pair.getLeft())))
                .collect(Collectors.toList());

        OrderHeader orderHeader = new OrderHeader(
                customer,
                shippingAddress,
                billingAddress,
                OrderStatus.NEW);
        orderHeader.associateOrderLine(Set.copyOf(orderLines));
        return orderHeaderRepository.save(orderHeader);
    }

    @Transactional(readOnly = true)
    public OrderHeader fetchOrderHeaderById(Long id) {
        return orderHeaderRepository.getById(id);
    }

}
