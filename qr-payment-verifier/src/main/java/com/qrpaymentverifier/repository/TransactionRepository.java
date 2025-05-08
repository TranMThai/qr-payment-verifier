package com.qrpaymentverifier.repository;

import com.qrpaymentverifier.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = """
            SELECT t 
            FROM Transaction t 
            ORDER BY t.transactionDate desc
            LIMIT 1
            """)
    Transaction getOldestNotReadTransaction();
}
