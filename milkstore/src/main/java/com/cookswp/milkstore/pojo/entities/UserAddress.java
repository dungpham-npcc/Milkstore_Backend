package com.cookswp.milkstore.pojo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "district")
    private String district;

    @Column(name = "is_default")
    private boolean isDefault;

    @Column(name = "address_alias")
    private String addressAlias;
}
