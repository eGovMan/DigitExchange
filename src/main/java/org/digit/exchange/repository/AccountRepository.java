package org.digit.exchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.digit.exchange.models.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
