package com.cookswp.milkstore.service.user;

import com.cookswp.milkstore.exception.UserInvisibilityException;
import com.cookswp.milkstore.exception.UserNotFoundException;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.user.UserRepository;
import com.cookswp.milkstore.service.role.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
//@EnableWebSecurity
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ModelMapper mapper;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder,
                       UserRepository userRepository,
                       RoleService roleService,
                       ModelMapper mapper
                       ){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    public User getUserByEmail(String email) throws UserInvisibilityException, DataIntegrityViolationException, UserNotFoundException {
        return userRepository.findByEmailAddress(email);
    }

    public User getUserById(int id){
        return userRepository.findByUserId(id);
    }


    public User registerUser(User user){
        if (userRepository.findByEmailAddress(user.getEmailAddress()) != null)
            throw new DataIntegrityViolationException("Email existed!");
        if (user.getPassword() != null)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.getRoleByRoleName("CUSTOMER"));
        user.setVisibilityStatus(true);
        return userRepository.save(user);
    }

    public User registerStaff(User user) throws DataIntegrityViolationException{
         if (userRepository.findByEmailAddress(user.getEmailAddress()) != null)
             throw new DataIntegrityViolationException("Email existed!");
         user.setPassword(passwordEncoder.encode(user.getPassword()));
         user.setRole(roleService.getRoleByRoleName(user.getRoleName()));
         user.setVisibilityStatus(true);
         return userRepository.save(user);
    }

    public boolean checkEmailExistence(String email){
        return userRepository.findByEmailAddress(email) != null;
    }

    public boolean checkPhoneNumberExistence(String phone){
        return userRepository.findByPhoneNumber(phone) != null;
    }

    public List<User> getInternalUserList(){
        return userRepository.findAllStaffs();
    }

    public List<User> getMemberUserList(){
        return userRepository.findAllMembers();
    }

    public void updateUserBasicInformation(int id, User user){
        User currentUser = userRepository.findByUserId(id);
        if (user != null){
            currentUser.setUsername(user.getUsername());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            userRepository.save(currentUser);
        }
    }

    public void updateUserPassword(int id, String newPassword){
        User user = userRepository.findByUserId(id);
        if (user != null){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }
    }

    public void deleteUser(int id){
        User user = userRepository.findByUserId(id);
        if (user != null){
            user.setVisibilityStatus(false);
            userRepository.save(user);
        }
    }

    public boolean checkVisibilityStatusByEmail(String email) {
        User user = userRepository.findByEmailAddress(email);
        if (user == null) {
            return false;
        }
        return user.isEnabled();
    }


    public User getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            String emailAddress = ((User) authentication.getPrincipal()).getEmailAddress();
            return userRepository.findByEmailAddress(emailAddress);
        }
        return null;
    }

    public void clearOtpInformationByEmail(String email){
        User user = userRepository.findByEmailAddress(email);
        if (user != null){
            user.setOtpCode(null);
            user.setOtpCreatedAt(null);
            user.setOtpExpiredAt(null);
            userRepository.save(user);
        }
    }

    public void banMemberUser(int id){
        User user = userRepository.findByUserId(id);
        if (user != null) {
            user.setProhibitStatus(true);
            userRepository.save(user);
        }
    }

    public void unbanMemberUser(int id){
        User user = userRepository.findByUserId(id);
        if (user != null) {
            user.setProhibitStatus(false);
            userRepository.save(user);
        }
    }


}
