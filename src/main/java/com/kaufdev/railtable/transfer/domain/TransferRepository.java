package com.kaufdev.railtable.transfer.domain;

import com.kaufdev.railtable.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
        
}
