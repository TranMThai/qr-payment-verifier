package com.qrpaymentverifier.repository;

import com.qrpaymentverifier.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query(value = """
            SELECT t 
            FROM Transaction t 
            ORDER BY t.transactionDate desc
            LIMIT 1
            """)
    Transaction getOldestNotReadTransaction();

    @Query(value = """
                SELECT t
                FROM Transaction t
                WHERE t.transactionDate<=:date
                ORDER BY t.transactionDate DESC
            """)
    List<Transaction> getTransactionsByDate(LocalDateTime date, Pageable pageable);

    @Query("""
    SELECT SUM(t.amountIn)
    FROM Transaction t
    WHERE FUNCTION('DATE', t.transactionDate) = :date
    """)
    BigDecimal getRevenueDaily(LocalDate date);
}
