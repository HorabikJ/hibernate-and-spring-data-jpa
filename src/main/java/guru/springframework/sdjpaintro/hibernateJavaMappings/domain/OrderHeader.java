package guru.springframework.sdjpaintro.hibernateJavaMappings.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AttributeOverrides({
        @AttributeOverride(
                name = "shippingAddress.address",
                column = @Column(name = "shipping_address")),
        @AttributeOverride(
                name = "shippingAddress.city",
                column = @Column(name = "shipping_city")),
        @AttributeOverride(
                name = "shippingAddress.state",
                column = @Column(name = "shipping_state")),
        @AttributeOverride(
                name = "shippingAddress.zipCode",
                column = @Column(name = "shipping_zip_code")),
        @AttributeOverride(
                name = "billingAddress.address",
                column = @Column(name = "billing_address")),
        @AttributeOverride(
                name = "billingAddress.city",
                column = @Column(name = "billing_city")),
        @AttributeOverride(
                name = "billingAddress.state",
                column = @Column(name = "billing_state")),
        @AttributeOverride(
                name = "billingAddress.zipCode",
                column = @Column(name = "billing_zip_code"))})
@Entity
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class OrderHeader extends BaseEntity {

    private String customer;
    @Embedded
    private Address shippingAddress;
    @Embedded
    private Address billingAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.PERSIST) //bidirectional
    @EqualsAndHashCode.Exclude
    private Set<OrderLine> orderLines = new HashSet<>();

    public OrderHeader(String customer, Address shippingAddress, Address billingAddress, OrderStatus orderStatus) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.orderStatus = orderStatus;
    }

    public void associateOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    public void associateOrderLine(Set<OrderLine> orderLines) {
        orderLines.forEach(this::associateOrderLine);
    }

}
