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

    @ManyToOne
    private Customer customer;

    @Embedded
    private Address shippingAddress;

    @Embedded
    private Address billingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    
//  We can use `orphanRemoval = true` to delete OrderApproval when deleting OrderHeader. As this is unidirectional 
//  relation OrderHeader -> OrderApproval, OrderApproval's do not have a relation to OrderHeader, so there is a 
//  possibility to delete OrderHeader without deleting its OrderApproval. It is not a good practice, so we want to 
//  delete OrderApproval as well. If we used `CascadeType.REMOVE` the result would be the same. 
//    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true) //unidirectional
//    private OrderApproval orderApproval;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private OrderApproval orderApproval;
    
    @OneToMany(mappedBy = "orderHeader", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private Set<OrderLine> orderLines = new HashSet<>();

    public OrderHeader(Customer customer, Address shippingAddress, Address billingAddress, OrderStatus orderStatus,
                       String approvedBy) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.orderStatus = orderStatus;
        // helper method do set OrderHeader in OrderApproval via this
        this.orderApproval = new OrderApproval(approvedBy, this);
    }

    public void associateOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrderHeader(this);
    }

    public void associateOrderLine(Set<OrderLine> orderLines) {
        orderLines.forEach(this::associateOrderLine);
    }

}
