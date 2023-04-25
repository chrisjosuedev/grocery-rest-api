package dev.chrisjosue.groceryrestapi.entity.articles;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand_name")
    @NotBlank(message = "Brand name is required.")
    private String brandName;

    private boolean isActive = true;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Article> articles;
}
