package com.m2i.paiement.repository;

import com.m2i.paiement.domain.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Transaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select transaction from Transaction transaction where transaction.user.login = ?#{authentication.name}")
    List<Transaction> findByUserIsCurrentUser();
}
