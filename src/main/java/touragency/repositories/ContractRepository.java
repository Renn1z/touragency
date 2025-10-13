package touragency.repositories;

import touragency.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    Optional<Contract> findByContractNumber(String contractNumber);
    List<Contract> findByContractStatus(String status);
    List<Contract> findByCustomerCustomerId(Long customerId);
    List<Contract> findByTourTourId(Long tourId);

    @Query("SELECT c FROM Contract c WHERE c.signDate BETWEEN :startDate AND :endDate")
    List<Contract> findContractsBetweenDates(@Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT c FROM Contract c WHERE c.totalAmount > c.paidAmount")
    List<Contract> findContractsWithOutstandingPayment();

    @Query("SELECT SUM(c.totalAmount) FROM Contract c WHERE c.signDate BETWEEN :start AND :end")
    Double getTotalRevenueForPeriod(@Param("start") LocalDate start,
                                    @Param("end") LocalDate end);
}