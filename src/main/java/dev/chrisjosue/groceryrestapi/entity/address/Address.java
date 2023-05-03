package dev.chrisjosue.groceryrestapi.entity.address;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address_1")
    @NotBlank(message = "First Line Address is required.")
    private String firstLineAddress;

    @Column(name = "address_2")
    @NotBlank(message = "Second Line Address is required.")
    private String secondLineAddress;

    @NotBlank(message = "City Name is required.")
    private String city;

    @NotBlank(message = "State Name is required.")
    private String state;

    @NotBlank(message = "Country Name Address is required.")
    private String country;

    @Column(name = "zip_code")
    @NotBlank(message = "Zip Code is required.")
    private String zipCode;

    @Column(name = "is_active", columnDefinition = "boolean default true")
    private Boolean isActive;
}
