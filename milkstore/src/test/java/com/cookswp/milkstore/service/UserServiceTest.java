//package com.cookswp.milkstore.service;
//
//import com.cookswp.milkstore.configuration.CustomAuthenticationProvider;
//import com.cookswp.milkstore.exception.UserInvisibilityException;
//import com.cookswp.milkstore.exception.UserNotFoundException;
//import com.cookswp.milkstore.pojo.entities.Role;
//import com.cookswp.milkstore.pojo.entities.User;
//import com.cookswp.milkstore.repository.RoleRepository.RoleRepository;
//import com.cookswp.milkstore.repository.UserRepository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.*;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.*;
//
//
//public class UserServiceTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private RoleService roleService;
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private CustomAuthenticationProvider customAuthenticationProvider;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    // T1: Add Staff Account - Email must be unique
//    @Test
//    public void testAddStaffAccountEmailUnique_HappyCase() {
//        User user = new User();
//        user.setEmailAddress("test@domain.com"); // Unique email
//        Role role = new Role();
//        user.setRole(role);
//
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User savedUser = userService.registerStaff(user);
//
//        assertNotNull(savedUser);
//        assertEquals("test@domain.com", savedUser.getEmailAddress()); // Verify email
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//
//    @Test
//    public void testAddStaffAccountEmailUnique_BadCase() {
//        User user = new User();
//        Role role = new Role();
//        role.setRoleId(1);
//        role.setRoleName("SELLER");
//        user.setEmailAddress("test@domain.com");
//        user.setRole(role);
//        when(roleService.getRoleByRoleName(Mockito.any())).thenReturn(role);
//
//        // Mock email check - return null for happy case
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User savedUser = userService.registerStaff(user);
//        assertNotNull(savedUser); // Verify happy case creation
//
//        // Mock email check - return existing user for duplicate test
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(new User());
//
//        assertThrows(DataIntegrityViolationException.class, () -> userService.registerStaff(user));
//        verify(userRepository, times(1)).save(any(User.class)); // Verify only 1 save for happy case
//    }
//
//    // T16: Register - Email must be unique and not associated with any other account
//    @Test
//    public void testRegisterEmailUnique_HappyCase() {
//        User user = new User();
//        user.setEmailAddress("test@domain.com"); // Unique email
//        Role role = new Role();
//        user.setRole(role);
//
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User savedUser = userService.registerUser(user);
//
//        assertNotNull(savedUser);
//        assertEquals("test@domain.com", savedUser.getEmailAddress()); // Verify email
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void testRegisterEmailUnique_BadCase() {
//        User user = new User();
//        Role role = new Role();
//        role.setRoleId(1);
//        user.setEmailAddress("test@domain.com");
//        user.setRole(role);
//        when(roleService.getRoleByRoleName(Mockito.any())).thenReturn(role);
//
//        // Mock email check - return null for happy case
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(null);
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        User savedUser = userService.registerUser(user);
//        assertNotNull(savedUser); // Verify happy case creation
//
//        // Mock email check - return existing user for duplicate test
//        when(userRepository.findByEmailAddress(user.getEmailAddress())).thenReturn(new User());
//
//        assertThrows(DataIntegrityViolationException.class, () -> userService.registerUser(user));
//    }
//
//
//    // T22: Login - Email must exist in the system
//    @Test
//    public void testLoginEmailExists_HappyCase() {
//        String email = "test@example.com";
//
//        User user = new User();
//        user.setEmailAddress(email);
//
//        when(userRepository.findByEmailAddress(email)).thenReturn(user);
//
//        assertNotNull(userService.getUserByEmail(email)); // Ensure getUserByEmail returns the expected user
//
//    }
//
//    @Test
//    public void testLoginEmailExists_BadCase() {
//        String nonExistentEmail = "nonexistent@domain.com";
//
//        when(userRepository.findByEmailAddress(nonExistentEmail)).thenReturn(null);
//
//        // Assertions
//        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(nonExistentEmail));
//    }
//
//
//
//    // T9: Ban User Account - Banned users must not be able to log in
//    @Test
//    public void testBanUserAccount_HappyCase() {
//        String email = "test@example.com";
//
//        User user = new User();
//        user.setProhibitStatus(false);
//        user.setEmailAddress(email);
//
//        when(userRepository.findByEmailAddress(email)).thenReturn(user);
//
//        // Perform assertions to verify the behavior
//        assertNotNull(userService.getUserByEmail(email));
//
//    }
//
//    @Test
//    public void testBanUserAccount_BadCase() {
//        String email = "test@example.com";
//
//        User user = new User();
//        user.setProhibitStatus(true);
//        user.setEmailAddress(email);
//
//        when(userRepository.findByEmailAddress(email)).thenReturn(user);
//
//        // Perform assertions to verify the behavior
//        assertThrows(UserInvisibilityException.class, () -> userService.getUserByEmail(email));
//    }
//}
