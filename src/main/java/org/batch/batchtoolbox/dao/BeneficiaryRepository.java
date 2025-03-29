package org.batch.batchtoolbox.dao;

import org.batch.batchtoolbox.bo.Beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary , Long> {
    Slice<Beneficiary> findAllBy(Pageable pageable);

    @Query("SELECT b FROM Beneficiary b WHERE b.state = :state AND b.sector = :sector ORDER BY b.depositDate DESC")
    Page<Beneficiary> findActiveBeneficiariesByField(
            @Param("state") String state,
            @Param("sector") String sector,
            Pageable pageable);

}
