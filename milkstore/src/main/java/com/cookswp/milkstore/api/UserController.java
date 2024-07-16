package com.cookswp.milkstore.api;

import com.cookswp.milkstore.exception.MissingRequiredFieldException;
import com.cookswp.milkstore.exception.RoleNotFoundException;
import com.cookswp.milkstore.exception.UnauthorizedAccessException;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserRegistrationDTO;
import com.cookswp.milkstore.pojo.dtos.UserModel.UserDTO;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.response.ResponseData;
import com.cookswp.milkstore.service.role.RoleService;
import com.cookswp.milkstore.service.user.UserService;
import com.cookswp.milkstore.utils.AuthorizationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper mapper;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper, RoleService roleService){
        this.userService = userService;
        this.mapper = mapper;
        this.roleService = roleService;
    }

    @GetMapping("/staffs")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserRegistrationDTO>> getStaffList(){
        AuthorizationUtils.checkAuthorization("ADMIN");
        return new ResponseData<>(HttpStatus.OK.value(),
                "List retrieved successfully!",
                userService.getInternalUserList().stream()
                        .map(user -> mapper.map(user, UserRegistrationDTO.class))
                        .toList());
    }

    @GetMapping("/members")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<List<UserDTO>> getMemberList(){
        AuthorizationUtils.checkAuthorization("ADMIN");
        return new ResponseData<>(HttpStatus.OK.value(),
                "List retrieved successfully!",
                userService.getMemberUserList().stream()
                        .map(user -> mapper.map(user, UserDTO.class))
                        .toList());
    }

    @GetMapping("/staffs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserRegistrationDTO> getStaff(@PathVariable int id){
        AuthorizationUtils.checkAuthorization("ADMIN");
        return new ResponseData<>(HttpStatus.OK.value(),
                "Staff retrieved successfully!",
                mapper.map(userService.getUserById(id), UserRegistrationDTO.class));
    }

    @GetMapping("/member")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserDTO> getMember(String email){
        AuthorizationUtils.checkAuthorization("ADMIN");
        return new ResponseData<>(HttpStatus.OK.value(),
                "List retrieved successfully!",
                mapper.map(userService.getUserByEmail(email), UserDTO.class));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseData<UserDTO> createStaff(UserRegistrationDTO userRegistrationDTO){
        AuthorizationUtils.checkAuthorization("ADMIN");
        if (userService.checkEmailExistence(userRegistrationDTO.getEmailAddress()))
            throw new DataIntegrityViolationException("Email existed, please try with another email");

        if (userRegistrationDTO.getEmailAddress() == null ||
        userRegistrationDTO.getPassword() == null ||
        userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        Set<String> allowedRoles = Set.of("MANAGER", "POST STAFF", "PRODUCT STAFF", "SELLER");
        if (!allowedRoles.contains(userRegistrationDTO.getRoleName())) {
            throw new RoleNotFoundException();
        }
        User userToAdd = mapper.map(userRegistrationDTO, User.class);
        userToAdd.setRole(roleService.getRoleByRoleName(userRegistrationDTO.getRoleName()));

        userService.registerStaff(userToAdd);

        return new ResponseData<>(HttpStatus.CREATED.value(),
                "User created successfully!",
                null);

    }

    @PutMapping("/staffs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<UserDTO> updateStaff(@PathVariable int id, UserRegistrationDTO userRegistrationDTO){
        AuthorizationUtils.checkAuthorization("ADMIN");
        if (userRegistrationDTO.getEmailAddress() == null ||
                userRegistrationDTO.getPassword() == null ||
                userRegistrationDTO.getUsername() == null)
            throw new MissingRequiredFieldException("Fields with asterisk");

        Set<String> allowedRoles = Set.of("MANAGER", "POST STAFF", "PRODUCT STAFF", "SELLER");
        if (!allowedRoles.contains(userRegistrationDTO.getRoleName())) {
            throw new RoleNotFoundException();
        }

        if (userRegistrationDTO.getPassword().equals(userService.getUserByEmail(userRegistrationDTO.getEmailAddress()).getPassword()))
            userService.updateUserBasicInformation(id, mapper.map(userRegistrationDTO, User.class));
        else {
            userService.updateUserBasicInformation(id, mapper.map(userRegistrationDTO, User.class));
            userService.updateUserPassword(id, userRegistrationDTO.getPassword());
        }
        return new ResponseData<>(HttpStatus.OK.value(),
                "User updated successfully!",
                mapper.map(userRegistrationDTO, UserDTO.class));
    }

    @DeleteMapping("/staffs/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> deleteStaff(@PathVariable int id){
        AuthorizationUtils.checkAuthorization("ADMIN");
        userService.deleteUser(id);
        return new ResponseData<>(HttpStatus.OK.value(), "Staff deleted successfully!", null);
    }

    @PutMapping("/members/ban/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> banMember(@PathVariable String email){
        AuthorizationUtils.checkAuthorization("ADMIN");
        userService.banMemberUser(userService.getUserByEmail(email).getUserId());
        return new ResponseData<>(HttpStatus.OK.value(), "Member banned successfully!", null);
    }

    @PutMapping("/members/unban/{email}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseData<String> unbanMember(@PathVariable String email){
        AuthorizationUtils.checkAuthorization("ADMIN");
        userService.unbanMemberUser(userService.getUserByEmail(email).getUserId());
        return new ResponseData<>(HttpStatus.OK.value(), "Member unbanned successfully!", null);
    }


}
