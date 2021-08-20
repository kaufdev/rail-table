package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.SectionEdgeAssembler;
import com.kaufdev.railtable.transfer.domain.SectionRepository;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.infrastracture.StationAssembler;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import com.kaufdev.railtable.transfer.infrastracture.graph.DijkstraPathFinderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InterchangeServiceTest {
    private final static Station KRK = new Station("Kraków Główny", "Kraków", "KRK");
    private final static Station POZ = new Station("Poznań Główny", "Ponzań", "POZ");

    private final static LocalDateTime TODAY = LocalDateTime.of(2020,5,5,0,0);

    @Mock
    SectionRepository sectionRepository;
    InterchangeService interchangeService;
    SectionEdgeAssembler sectionEdgeAssembler = new SectionEdgeAssembler();
    StationAssembler stationAssembler = new StationAssembler();

    @BeforeEach
    void setUp() {
        interchangeService = new InterchangeService(new DijkstraPathFinderImpl(),sectionRepository,sectionEdgeAssembler,stationAssembler);
    }

    @Test
    public void whenIsNoPathWithInterchangesEmptyListShouldBeReturned(){
        //given
        Section krk_poz = new Section(KRK, POZ, TODAY, TODAY, 2);
        ReflectionTestUtils.setField(krk_poz,"id",1L);
        when(sectionRepository.findSectionsInTimeRange(eq(TODAY),eq(TODAY.plusDays(1L))))
                .thenReturn(List.of(krk_poz));

        //when
        List<TransferDto> transfers = interchangeService.findTransfers("KRK", "NYC", TODAY);

        //then
        assertThat(transfers).isEmpty();
    }

}