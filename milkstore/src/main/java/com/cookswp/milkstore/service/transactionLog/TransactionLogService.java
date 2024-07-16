package com.cookswp.milkstore.service.transactionLog;

import com.cookswp.milkstore.pojo.entities.TransactionLog;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionLogService implements ITransactionLogService {

    private final TransactionLogRepository transactionLogRepository;

    public TransactionLogService(TransactionLogRepository transactionLogRepository) {
        this.transactionLogRepository = transactionLogRepository;
    }

    @Override
    public TransactionLog findByTransactionNo(String transactionNo) {
        return transactionLogRepository.findByTransactionNo(transactionNo);
    }

    @Override
    public TransactionLog save(TransactionLog transactionLog) {
        return transactionLogRepository.save(transactionLog);
    }
}
