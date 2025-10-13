package touragency.repositories;

import touragency.models.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByIsActiveTrue();
    List<Route> findByRouteNameContainingIgnoreCase(String name);
    List<Route> findByDifficultyLevel(String difficultyLevel);

    @Query("SELECT r FROM Route r WHERE r.isActive = true AND " +
            "r.price BETWEEN :minPrice AND :maxPrice")
    List<Route> findByPriceRange(@Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);

    @Query("SELECT r FROM Route r WHERE r.isActive = true AND " +
            "r.durationDays BETWEEN :minDays AND :maxDays")
    List<Route> findByDurationRange(@Param("minDays") Integer minDays,
                                    @Param("maxDays") Integer maxDays);
}