package com.cookswp.milkstore.repository.user;

import com.cookswp.milkstore.pojo.entities.TemporaryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TemporaryUserRepository extends JpaRepository<TemporaryUser, Integer> {
    TemporaryUser findByEmailAddress(String emailAddress);

    void deleteByEmailAddress(String emailAddress);

}
