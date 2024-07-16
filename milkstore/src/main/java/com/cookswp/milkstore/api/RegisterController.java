
package com.cookswp.milkstore.api;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.exception.UnauthorizedAccessException;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@CrossOrigin(maxAge = 3600)
@ControllerAdvice
public class RegisterController {
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public RegisterController(UserService userService, ModelMapper mapper){
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> register(UserRegistrationDTO userRegistrationDTO){
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");

        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getPassword() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        User user = userService.registerUser(mapper.map(userRegistrationDTO, User.class));
        return new ResponseData<>(HttpStatus.CREATED.value(),
                "Registration successfully!",
                mapper.map(user, UserDTO.class));
    }

    @PostMapping("/complete-registration")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @CrossOrigin(maxAge = 3600)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> completeRegister(UserRegistrationDTO userRegistrationDTO) throws Exception {
        User user = userService.getUserByEmail(userRegistrationDTO.getEmailAddress());
        if (user == null)
            throw new Exception("Error processing the request");

        user.setPhoneNumber(userRegistrationDTO.getPhoneNumber());
        user.setUsername(userRegistrationDTO.getUsername());
        userService.updateUserBasicInformation(user.getUserId(), user);
        userService.updateUserPassword(user.getUserId(), userRegistrationDTO.getPassword());

        return new ResponseData<>(HttpStatus.CREATED.value(),
                "Registration completed!",
                mapper.map(user, UserDTO.class));
    }

    @ExceptionHandler(MissingRequiredFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<UserRegistrationDTO> handleNullFieldsException(MissingRequiredFieldException e){
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData<UserRegistrationDTO> handleRoleNotFoundException(RoleNotFoundException e){
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseData<String> handleUnauthorizedAccessException(UnauthorizedAccessException e){
        return new ResponseData<>(HttpStatus.FORBIDDEN.value(), e.getMessage(), null);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<String> handleIllegalArgumentException(IllegalArgumentException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseData<String> handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return new ResponseData<>(HttpStatus.CONFLICT.value(), e.getMessage(), null);
    }
}

