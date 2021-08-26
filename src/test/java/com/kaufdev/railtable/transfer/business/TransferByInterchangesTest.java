package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.domain.Transfer;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TransferByInterchangesTest {
    private final Station KRK_STATION = new Station("Kraków główny","Kraków", "KRK");
    private final Station WAW_STATION = new Station("Warszawa główny","Warszawa", "WAW");
    private final Station POZ_STATION = new Station("Poznan główny","Poznan", "POZ");
    private final Station LIZ_STATION = new Station("Lizut","Lizut", "LIZ");

    @Test
    public void shouldReturnEmptyInterchangesWhenInputIsEmpty(){
        //given
        TransferByInterchanges subject = new TransferByInterchanges(Collections.emptyList());

        //then
        assertThat(subject.getInterchanges()).isEmpty();
    }

    @Test
    public void shouldReturnOneInterchangeWhenThereAreSectionFromOneTransfer(){
        //given
        Transfer transfer = new Transfer("PKP", BigDecimal.ZERO, Collections.emptySet());
        ReflectionTestUtils.setField(transfer,"id",1L);
        Section section1 = new Section(KRK_STATION,WAW_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        Section section2 = new Section(WAW_STATION,POZ_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        ReflectionTestUtils.setField(section1,"transfer", transfer);
        ReflectionTestUtils.setField(section2,"transfer", transfer);

        TransferByInterchanges subject = new TransferByInterchanges(List.of(section1,section2));

        //when
        assertThat(subject.getInterchanges()).hasSize(1);
    }

    @Test
    public void interchangeCreatedFromCoupleOfSectionsShouldHaveProperProperties(){
        //given
        final LocalDateTime expectedStartingTime = LocalDateTime.of(2020,5,5,5,5);
        final LocalDateTime expectedEndingTime = LocalDateTime.of(2020,6,6,6,6);

        Transfer transfer = new Transfer("PKP", BigDecimal.TEN, Collections.emptySet());
        ReflectionTestUtils.setField(transfer,"id",1L);

        Section section3 = new Section(POZ_STATION,LIZ_STATION, LocalDateTime.MIN, expectedEndingTime,30);
        Section section2 = new Section(WAW_STATION,POZ_STATION, LocalDateTime.MIN, LocalDateTime.MIN,30);
        Section section1 = new Section(KRK_STATION,WAW_STATION, expectedStartingTime, LocalDateTime.MIN,30);

        updateSection(section1,transfer,section2);
        updateSection(section2,transfer,section3);
        updateSection(section3,transfer,null);

        TransferByInterchanges subject = new TransferByInterchanges(List.of(section1,section2, section3));

        //when
        assertThat(subject.getInterchanges()).hasSize(1)
                .flatExtracting("startStation","endStation","outboundTime","arrivalTime","firstClassCost","secondClassCost")
                .containsExactly(StationAssembler.assemble(KRK_STATION),
                        StationAssembler.assemble(LIZ_STATION),
                        expectedStartingTime,
                        expectedEndingTime,
                        BigDecimal.valueOf(900),
                        BigDecimal.valueOf(450.0)
                        );
    }

    @Test
    public void pathFromCoupleOfTransfersShouldCreateCoupleInterchanges(){
        //given
        Transfer transfer = new Transfer("PKP", BigDecimal.ZERO, Collections.emptySet());
        ReflectionTestUtils.setField(transfer,"id",1L);
        Transfer transfer2 = new Transfer("PKP", BigDecimal.ZERO, Collections.emptySet());
        ReflectionTestUtils.setField(transfer,"id",2L);

        Section section11 = new Section(KRK_STATION,WAW_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        Section section12 = new Section(WAW_STATION,POZ_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        ReflectionTestUtils.setField(section11,"transfer", transfer);
        ReflectionTestUtils.setField(section12,"transfer", transfer);

        Section section21 = new Section(KRK_STATION,WAW_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        Section section22 = new Section(WAW_STATION,POZ_STATION, LocalDateTime.MIN, LocalDateTime.MIN,0);
        ReflectionTestUtils.setField(section21,"transfer", transfer2);
        ReflectionTestUtils.setField(section22,"transfer", transfer2);

        TransferByInterchanges subject = new TransferByInterchanges(List.of(section11,section12,section21,section22));

        //when
        assertThat(subject.getInterchanges()).hasSize(2);
    }

    //TEST on money

    private void updateSection(Section subject, Transfer transfer, Section nextSection){
        ReflectionTestUtils.setField(subject,"transfer", transfer);
        ReflectionTestUtils.setField(subject,"nextSection", nextSection);
    }

}