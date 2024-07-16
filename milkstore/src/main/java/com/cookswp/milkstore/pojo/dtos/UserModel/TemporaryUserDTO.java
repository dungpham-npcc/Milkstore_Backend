package com.cookswp.milkstore.pojo.dtos.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TemporaryUserDTO {
    private String emailAddress;
    private String otpCode;
}
