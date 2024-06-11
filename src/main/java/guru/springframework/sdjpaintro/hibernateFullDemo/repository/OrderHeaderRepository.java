package guru.springframework.sdjpaintro.hibernateFullDemo.repository;

import guru.springframework.sdjpaintro.hibernateFullDemo.domain.OrderHeader;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderHeaderRepository extends JpaRepository<OrderHeader, Long> {

    List<OrderHeader> findAllByCustomer_Name(String customerName);

    //1st solution for N+1 problem
    @EntityGraph(attributePaths = {"orderLines"})
    @Query("SELECT oh FROM OrderHeader oh WHERE oh.customer.name = :customerName")
    List<OrderHeader> findAllByCustomer_Name_withEntityGraph(String customerName);

    //2nd solution for N+1 problem
    @Query("SELECT oh FROM OrderHeader oh LEFT JOIN FETCH oh.orderLines WHERE oh.customer.name = :customerName")
    List<OrderHeader> findAllByCustomer_Name_withLeftJoinFetch(String customerName);

}
