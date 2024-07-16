package com.cookswp.milkstore.repository.transactionLog;


import com.cookswp.milkstore.pojo.entities.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

    TransactionLog findByTransactionNo(String transactionNo);

    @Query("SELECT t.transactionStatus FROM TransactionLog t WHERE t.txnRef = :txnRef")
    String findTransactionNoByTxnRef(@Param("txnRef") String txnRef);
}
