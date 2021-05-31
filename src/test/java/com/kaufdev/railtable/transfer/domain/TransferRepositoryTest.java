package com.kaufdev.railtable.transfer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class TransferRepositoryTest {

    private static LocalDate TODAY = LocalDate.of(2021,10,10);
    private static LocalDate TOMMOROW = LocalDate.of(2021,10,11);

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    TransferRepository transferRepository;

    Station krk;
    Station waw;
    Station gds;
    Station hel;

    @BeforeEach
    void setUp() {
        krk = entityManager.persist(new Station("Krakow", "Krakow", "KRK"));
        waw = entityManager.persist(new Station("Warszawa", "Warszawa", "WAW"));
        gds = entityManager.persist(new Station("Gdansk", "Gdansk", "GDS"));
        hel = entityManager.persist(new Station("Hel", "Hel", "HEL"));
    }

    @Test
    public void shouldFindProperTransferByStartAndEndCityInOneSection(){
        //given
        Section krk_waw = new Section(krk, waw, TODAY.atTime(1,1), TODAY.atTime(2, 1), 1);
        Transfer transfer1 = new Transfer("INTERCITY",BigDecimal.ONE, Set.of(krk_waw));
        entityManager.persist(transfer1);
        entityManager.flush();
        //when
        List<Transfer> transferFromDb = transferRepository.findTransfers("KRK","WAW", TODAY.atTime(0,0));
        //then
        assertThat(transferFromDb).hasSize(1).extracting("sections").hasSize(1);
    }
    @Test
    public void shouldFindProperTransferByStartAndEndCityInCoupleSection(){
        //given
        Section waw_gds = new Section(waw, gds, TODAY.atTime(3,1), TODAY.atTime(4, 1), 1);
        Section gds_hel = new Section(gds, hel, TODAY.atTime(5,1), TODAY.atTime(6, 1), 1);
        Transfer transfer1 = new Transfer("INTERCITY",BigDecimal.ONE, Set.of(waw_gds,gds_hel));
        entityManager.persist(transfer1);
        entityManager.flush();

        //when
        List<Transfer> transferFromDb = transferRepository.findTransfers("WAW","HEL", TODAY.atTime(0,0));
        //then
        assertThat(transferFromDb).hasSize(1).flatExtracting("sections").hasSize(2);
    }

    @Test
    public void shouldReturnEmptyListForNoneTransfers(){
        //given
        Section waw_gds = new Section(waw, gds, TODAY.atTime(3,1), TODAY.atTime(4, 1), 1);
        Section gds_hel = new Section(gds, hel, TODAY.atTime(5,1), TODAY.atTime(6, 1), 1);
        Transfer transfer1 = new Transfer("INTERCITY",BigDecimal.ONE, Set.of(waw_gds,gds_hel));
        entityManager.persist(transfer1);
        entityManager.flush();

        //when
        List<Transfer> transferFromDb = transferRepository.findTransfers("TOR","BYD", TODAY.atTime(0,0));
        //then
        assertThat(transferFromDb).isEmpty();
    }

    @Test
    public void shouldReturnEmptyListWhenThereIsNoTransferForSpecificOubboundTime(){
        //given
        Section waw_gds = new Section(waw, gds, TODAY.atTime(3,1), TODAY.atTime(4, 1), 1);
        Section gds_hel = new Section(gds, hel, TODAY.atTime(5,1), TODAY.atTime(6, 1), 1);
        Transfer transfer1 = new Transfer("INTERCITY",BigDecimal.ONE, Set.of(waw_gds,gds_hel));
        entityManager.persist(transfer1);
        entityManager.flush();

        //when
        List<Transfer> transferFromDb = transferRepository.findTransfers("WAW","HEL", TOMMOROW.atTime(0,0));
        //then
        assertThat(transferFromDb).isEmpty();
    }
}