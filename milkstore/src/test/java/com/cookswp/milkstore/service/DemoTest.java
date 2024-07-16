//package com.cookswp.milkstore.service;
//
//import com.cookswp.milkstore.pojo.entities.Role;
//import com.cookswp.milkstore.pojo.entities.User;
//import com.cookswp.milkstore.repository.RoleRepository.RoleRepository;
//import com.cookswp.milkstore.repository.UserRepository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.mockito.*;
//import org.junit.jupiter.api.Test;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//
//
//public class DemoTest {
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private RoleService roleService;
//
//    @Mock
//    private RoleRepository roleRepository;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    public void setUp(){
//        MockitoAnnotations.openMocks(this);
//    }
//
//
//    // T1: Add Staff Account - Email must be unique
//
//    @Test
//    public void testAddStaffAccountEmailUnique_HappyCase() {
//        User userEntity = new User();
//        Role role = new Role();
//        role.setRoleId(1);
//        role.setRoleName("SELLER");
//        userEntity.setEmailAddress("test@domain.com");
//        userEntity.setPassword("password123");
//        userEntity.setRole(role);
//        when(roleService.getRoleByRoleName(Mockito.any())).thenReturn(role);
//        when(userRepository.save(any(User.class))).thenReturn(userEntity);
//
//        User savedUser = userService.registerStaff(userEntity);
//        assertNotNull(savedUser);
//        assertEquals("SELLER", userEntity.getRoleName());
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//}