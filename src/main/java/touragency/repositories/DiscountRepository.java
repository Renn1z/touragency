package touragency.repositories;

import touragency.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> findByIsActiveTrue();
    List<Discount> findByDiscountNameContainingIgnoreCase(String name);

    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND " +
            "(d.startDate IS NULL OR d.startDate <= :date) AND " +
            "(d.endDate IS NULL OR d.endDate >= :date)")
    List<Discount> findActiveDiscounts(@Param("date") LocalDate date);

    List<Discount> findByDiscountType(String discountType);
}