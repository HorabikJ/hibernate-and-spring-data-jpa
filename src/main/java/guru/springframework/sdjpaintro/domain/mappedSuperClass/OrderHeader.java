package guru.springframework.sdjpaintro.domain.mappedSuperClass;

import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class OrderHeader extends BaseEntity {

    private String customer;

    public OrderHeader(String customer) {
        this.customer = customer;
    }

    protected OrderHeader() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        OrderHeader that = (OrderHeader) o;

        return Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        return result;
    }
}
