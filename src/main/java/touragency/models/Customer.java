package touragency.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @NotBlank(message = "Фамилия обязательна")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "Имя обязательно")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Неверный формат телефона")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Неверный формат email")
    @Column(name = "email")
    private String email;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    // Конструкторы
    public Customer() {
        this.registrationDate = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }
}