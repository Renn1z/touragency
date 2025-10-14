package touragency.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_id")
    private Long discountId;

    @NotBlank(message = "Название скидки обязательно")
    @Column(name = "discount_name", nullable = false)
    private String discountName;

    @NotBlank(message = "Тип скидки обязателен")
    @Column(name = "discount_type", nullable = false)
    private String discountType; // PERCENTAGE, FIXED_AMOUNT

    @DecimalMin(value = "0.0", message = "Значение скидки не может быть отрицательным")
    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @DecimalMin(value = "0.0", message = "Минимальная сумма не может быть отрицательной")
    @Column(name = "min_amount")
    private BigDecimal minAmount;

    @Column(name = "start_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Column(name = "end_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Column(name = "is_active")
    private Boolean isActive = true;

    // Конструкторы, геттеры и сеттеры
    public Discount() {}

    public Long getDiscountId() { return discountId; }
    public void setDiscountId(Long discountId) { this.discountId = discountId; }

    public String getDiscountName() { return discountName; }
    public void setDiscountName(String discountName) { this.discountName = discountName; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public BigDecimal getDiscountValue() { return discountValue; }
    public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }

    public BigDecimal getMinAmount() { return minAmount; }
    public void setMinAmount(BigDecimal minAmount) { this.minAmount = minAmount; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}