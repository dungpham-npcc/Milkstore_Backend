package com.cookswp.milkstore.repository.user;

import com.cookswp.milkstore.pojo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    User findByEmailAddress(String emailAddress);

    User findByPhoneNumber(String phoneNumber);

    User findByUserId(int userId);

    @Query("SELECT u FROM User u WHERE u.visibilityStatus = true AND u.role.roleId IN (2, 3, 4, 5)")
    List<User> findAllStaffs();

    @Query("SELECT u FROM User u WHERE u.visibilityStatus = true AND u.role.roleId = 1")
    List<User> findAllMembers();

    //    @Modifying
//    @Query("UPDATE User u SET u.phoneNumber = :phone, u.username = :username WHERE u.userId = :id")
//    int updateUser(@Param("id") int id,
//                    @Param("phone") String phone,
//                    @Param("username") String username);    //return number of rows affected
//    @Modifying
//    @Query("UPDATE User u SET u.password = :password WHERE u.userId = :id")
//    int updateUserPassword(@Param("id") int id,
//                   @Param("password") String password);
//    @Modifying
//    @Query("UPDATE User u SET u.visibilityStatus = false WHERE u.userId = :id")
//    void deleteUser(@Param("id") int id);
//
//    @Query("SELECT u.visibilityStatus FROM User u WHERE u.emailAddress = :email")
//    boolean isVisible(@Param("email") String email);
}
