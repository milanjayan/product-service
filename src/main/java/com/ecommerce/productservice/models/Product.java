package com.ecommerce.productservice.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Product extends BaseModel {
    private String title;
    private String Description;
    private Double price;
    private String image;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
