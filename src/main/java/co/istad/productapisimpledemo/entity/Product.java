package co.istad.productapisimpledemo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@Entity(name = "product_tbl")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private float price;
    private int userId; // user that create the product !

}
