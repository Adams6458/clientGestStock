package com.m2i.paiement.repository;

import com.m2i.paiement.domain.Rapport;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Rapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {
    @Query("select rapport from Rapport rapport where rapport.user.login = ?#{authentication.name}")
    List<Rapport> findByUserIsCurrentUser();
}
