package com.cookswp.milkstore.repository.Refund;

import com.cookswp.milkstore.pojo.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {

    Refund findByUserId(Integer integer);
}
