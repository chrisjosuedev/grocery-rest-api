package dev.chrisjosue.groceryrestapi.entity.article;

import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required.")
    private String description;

    private int stock;

    @Column(name = "unit_price")
    @NotNull(message = "Unit price is required.")
    @PositiveOrZero(message = "Unit price must be greater or equal to zero.")
    private Double unitPrice;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
