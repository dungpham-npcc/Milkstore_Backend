package com.cookswp.milkstore.service.transactionLog;


import com.cookswp.milkstore.pojo.entities.TransactionLog;

public interface ITransactionLogService {

    TransactionLog findByTransactionNo(String transactionNo);

    TransactionLog save(TransactionLog transactionLog);
}
