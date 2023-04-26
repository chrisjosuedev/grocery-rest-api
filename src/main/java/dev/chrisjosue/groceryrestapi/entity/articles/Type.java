package dev.chrisjosue.groceryrestapi.entity.articles;

import dev.chrisjosue.groceryrestapi.entity.persons.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "article_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Type is required.")
    private String type;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    private List<Article> articles;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}
