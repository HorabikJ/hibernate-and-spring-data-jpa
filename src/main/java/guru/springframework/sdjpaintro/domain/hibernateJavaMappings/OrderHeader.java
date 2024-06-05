package guru.springframework.sdjpaintro.domain.hibernateJavaMappings;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
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
                column = @Column(name = "billing_zip_code"))
})
public class OrderHeader extends BaseEntity {

    private String customer;
    @Embedded
    private Address shippingAddress;
    @Embedded
    private Address billingAddress;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderHeader(String customer, Address shippingAddress, Address billingAddress, OrderStatus orderStatus) {
        this.customer = customer;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.orderStatus = orderStatus;
    }

    protected OrderHeader() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderHeader that = (OrderHeader) o;

        if (!Objects.equals(customer, that.customer)) return false;
        if (!Objects.equals(shippingAddress, that.shippingAddress))
            return false;
        if (!Objects.equals(billingAddress, that.billingAddress))
            return false;
        return orderStatus == that.orderStatus;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (shippingAddress != null ? shippingAddress.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }
}
