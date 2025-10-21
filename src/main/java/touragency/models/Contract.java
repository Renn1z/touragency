package touragency.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "contracts")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @NotBlank(message = "Номер договора обязателен")
    @Column(name = "contract_number", unique = true, nullable = false)
    private String contractNumber;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contract_customers",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    // @NotEmpty(message = "Должен быть выбран хотя бы один клиент")
    private Set<Customer> customers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "contract_tours",
            joinColumns = @JoinColumn(name = "contract_id"),
            inverseJoinColumns = @JoinColumn(name = "tour_id")
    )
    // @NotEmpty(message = "Должен быть выбран хотя бы один тур")
    private Set<Tour> tours = new HashSet<>();

    @Column(name = "sign_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate signDate;

    @DecimalMin(value = "0.0", message = "Общая сумма не может быть отрицательной")
    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @DecimalMin(value = "0.0", message = "Оплаченная сумма не может быть отрицательной")
    @Column(name = "paid_amount", nullable = false)
    private BigDecimal paidAmount;

    @NotBlank(message = "Статус договора обязателен")
    @Column(name = "contract_status", nullable = false)
    private String contractStatus; // DRAFT, SIGNED, PAID, CANCELLED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    // Конструкторы, геттеры и сеттеры
    public Contract() {
        this.signDate = LocalDate.now();
        this.paidAmount = BigDecimal.ZERO;
        this.customers = new HashSet<>();
        this.tours = new HashSet<>();
    }

    public Long getContractId() { return contractId; }
    public void setContractId(Long contractId) { this.contractId = contractId; }

    public String getContractNumber() { return contractNumber; }
    public void setContractNumber(String contractNumber) { this.contractNumber = contractNumber; }

    public Set<Customer> getCustomers() { return customers; }
    public void setCustomers(Set<Customer> customers) { this.customers = customers; }

    public Set<Tour> getTours() { return tours; }
    public void setTours(Set<Tour> tours) { this.tours = tours; }

    public LocalDate getSignDate() { return signDate; }
    public void setSignDate(LocalDate signDate) { this.signDate = signDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public BigDecimal getPaidAmount() { return paidAmount; }
    public void setPaidAmount(BigDecimal paidAmount) { this.paidAmount = paidAmount; }

    public String getContractStatus() { return contractStatus; }
    public void setContractStatus(String contractStatus) { this.contractStatus = contractStatus; }

    public Discount getDiscount() { return discount; }
    public void setDiscount(Discount discount) { this.discount = discount; }
}