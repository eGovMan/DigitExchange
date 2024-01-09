package org.digit.exchange.repository;


import org.digit.exchange.model.Organisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, String> {

    Organisation findByAgencyApiKey(String apiKey);
    Page<Organisation> findByAdministratorId(String adminId,Pageable pageable);
}
