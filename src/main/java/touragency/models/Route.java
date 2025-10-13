package touragency.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "routes")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Long routeId;

    @NotBlank(message = "Название маршрута обязательно")
    @Column(name = "route_name", nullable = false)
    private String routeName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Min(value = 1, message = "Длительность должна быть не менее 1 дня")
    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @NotBlank(message = "Уровень сложности обязателен")
    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;

    @DecimalMin(value = "0.0", message = "Цена не может быть отрицательной")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Конструкторы, геттеры и сеттеры
    public Route() {}

    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDurationDays() { return durationDays; }
    public void setDurationDays(Integer durationDays) { this.durationDays = durationDays; }

    public String getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(String difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}