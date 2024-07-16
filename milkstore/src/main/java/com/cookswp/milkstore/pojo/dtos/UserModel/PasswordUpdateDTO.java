package com.cookswp.milkstore.pojo.dtos.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdateDTO {
    private String oldPassword;
    private String newPassword;
}
