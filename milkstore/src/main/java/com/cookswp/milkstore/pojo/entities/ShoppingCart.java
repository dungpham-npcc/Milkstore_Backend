package com.cookswp.milkstore.pojo.entities;

import com.cookswp.milkstore.enums.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table (name = "shopping_cart")
@Getter
@Setter
public class ShoppingCart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id", unique = true, nullable = false)
    private int userId;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    @Column(name = "user_id", unique = true, nullable = false)
    @JsonManagedReference
    private List<ShoppingCartItem> items;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
