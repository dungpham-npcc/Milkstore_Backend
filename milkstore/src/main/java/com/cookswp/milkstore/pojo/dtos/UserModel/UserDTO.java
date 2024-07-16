
package com.cookswp.milkstore.pojo.dtos.UserModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String emailAddress;
    private String phoneNumber;
    private String username;
    private boolean prohibitStatus;
}
