package com.kaufdev.railtable.transfer.infrastracture;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.domain.Transfer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferControllerIT {

    private static LocalDate TODAY = LocalDate.of(2021,10,10);

    @Autowired
    EntityManager entityManager;

    @Autowired
    TransferController transferController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @Transactional
    public void shouldReturnTransferDtoFromDB(){
        //given
        Station krk = new Station("Krakow", "Krakow", "KRK");
        Station waw = new Station("Warszawa", "Warszawa", "WAW");
        entityManager.persist(krk);
        entityManager.persist(waw);
        Section krk_waw = new Section(krk, waw, TODAY.atTime(1,1), TODAY.atTime(2, 1), 1);
        Transfer transfer1 = new Transfer("INTERCITY", BigDecimal.ONE, Set.of(krk_waw));
        entityManager.persist(transfer1);
        entityManager.flush();
        //todo change properties of dto to acronyms, change on UI needed too!
        SearchTransferDto searchTransferDto = new SearchTransferDto("KRK", "WAW", TODAY.atTime(0, 1));

        //when
        List<TransferDto> transfers = transferController.findTransfers(searchTransferDto);

        //then
        assertThat(transfers).hasSize(1).extracting("operator").containsOnly("INTERCITY");
    }
}