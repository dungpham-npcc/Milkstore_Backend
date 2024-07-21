package com.cookswp.milkstore.repository.Refund;

import com.cookswp.milkstore.pojo.entities.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Integer> {

    List<Refund> findByUserId(Integer integer);
}
