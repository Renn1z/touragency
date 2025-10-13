package touragency.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tour_id")
    private Long tourId;

    @NotBlank(message = "Название тура обязательно")
    @Column(name = "tour_name", nullable = false)
    private String tourName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @Future(message = "Дата начала должна быть в будущем")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Future(message = "Дата окончания должна быть в будущем")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Min(value = 1, message = "Количество участников должно быть не менее 1")
    @Column(name = "num_vacationers", nullable = false)
    private Integer numVacationers;

    @DecimalMin(value = "0.0", message = "Цена тура не может быть отрицательной")
    @Column(name = "tour_price", nullable = false)
    private BigDecimal tourPrice;

    @NotBlank(message = "Статус обязателен")
    @Column(name = "status", nullable = false)
    private String status; // PLANNED, ACTIVE, COMPLETED, CANCELLED

    // Конструкторы, геттеры и сеттеры
    public Tour() {}

    public Long getTourId() { return tourId; }
    public void setTourId(Long tourId) { this.tourId = tourId; }

    public String getTourName() { return tourName; }
    public void setTourName(String tourName) { this.tourName = tourName; }

    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getNumVacationers() { return numVacationers; }
    public void setNumVacationers(Integer numVacationers) { this.numVacationers = numVacationers; }

    public BigDecimal getTourPrice() { return tourPrice; }
    public void setTourPrice(BigDecimal tourPrice) { this.tourPrice = tourPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}