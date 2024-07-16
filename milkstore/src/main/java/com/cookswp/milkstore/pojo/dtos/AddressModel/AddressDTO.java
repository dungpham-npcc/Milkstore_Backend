package com.cookswp.milkstore.pojo.dtos.AddressModel;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class AddressDTO {
    private int id;
    private int userId;
    private String addressLine;
    private String district;
    private boolean isDefault;
    private String addressAlias;
}
