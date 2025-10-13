package touragency.repositories;

import touragency.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    List<Customer> findByEmailContainingIgnoreCase(String email);
    Optional<Customer> findByPhone(String phone);

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<touragency.models.Customer> searchCustomers(@Param("search") String search);
}