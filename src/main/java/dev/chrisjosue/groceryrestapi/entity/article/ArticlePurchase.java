package dev.chrisjosue.groceryrestapi.entity.article;

import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.chrisjosue.groceryrestapi.entity.person.Employee;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "article_purchase")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "CAI is required.")
    private String cai;

    @Column(updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @CreatedDate
    private Date date;

    @OneToOne
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @JsonBackReference
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, mappedBy = "articlePurchase")
    private List<ArticlePurchaseDetail> articlePurchaseDetail;
}