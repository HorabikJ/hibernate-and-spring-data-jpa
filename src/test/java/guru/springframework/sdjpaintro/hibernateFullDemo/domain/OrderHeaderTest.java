package guru.springframework.sdjpaintro.hibernateFullDemo.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderHeaderTest {

    @Test
    void shouldBeEqual() {
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(1L);

        assertThat(orderHeader1).isEqualTo(orderHeader2);
    }

    @Test
    void shouldNotBeEqual() {
        OrderHeader orderHeader1 = new OrderHeader();
        orderHeader1.setId(1L);

        OrderHeader orderHeader2 = new OrderHeader();
        orderHeader2.setId(2L);

        assertThat(orderHeader1).isNotEqualTo(orderHeader2);
    }


}
