package com.cookswp.milkstore.repository.address;

import com.cookswp.milkstore.pojo.entities.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<UserAddress, Integer> {
    List<UserAddress> findByUserId(int userId);
    UserAddress findById(int addressId);
}
