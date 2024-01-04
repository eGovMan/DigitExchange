package org.digit.exchange.repository;

import org.digit.exchange.model.Individual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IndividualRepository extends JpaRepository<Individual, String> {

}
