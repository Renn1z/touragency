package touragency.repositories;

import touragency.models.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    List<Tour> findByTourNameContainingIgnoreCase(String name);
    List<Tour> findByStatus(String status);
    List<Tour> findByStartDateAfter(LocalDate date);
    List<Tour> findByRouteRouteId(Long routeId);

    @Query("SELECT t FROM Tour t WHERE t.startDate BETWEEN :startDate AND :endDate")
    List<Tour> findToursBetweenDates(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);

    @Query("SELECT t FROM Tour t WHERE t.status = 'ACTIVE' AND t.numVacationers > 0")
    List<Tour> findAvailableTours();
}