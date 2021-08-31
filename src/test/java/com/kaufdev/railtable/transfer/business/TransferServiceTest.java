package com.kaufdev.railtable.transfer.business;

import com.kaufdev.railtable.transfer.domain.Transfer;
import com.kaufdev.railtable.transfer.domain.TransferRepository;
import com.kaufdev.railtable.transfer.infrastracture.SearchTransferDto;
import com.kaufdev.railtable.transfer.infrastracture.StationDto;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    private final LocalDate TODAY = LocalDate.of(2020,5,6);

    @Mock
    TransferRepository transferRepository;

    @Mock
    TransferAssembler transferAssembler;

    @Mock
    InterchangeService interchangeService;

    @Test
    public void shouldReturnTransferDtosInAscOrderingRegardingArrivalTime(){
        //given
        TransferDto earlierArrivedTransferDto = new TransferDto(TODAY.atTime(1, 20),
                TODAY.atTime(16, 20),
                new StationDto("Kraków Podgórze", "Krakow", "KRKP"),
                new StationDto("Warszawa Centralna", "Warszawa", "WAWC"),
                "Intercity",
                BigDecimal.valueOf(80),
                BigDecimal.valueOf(40.0),
                emptyList(), Collections.emptySet(), 0, 0, 0, 0);

        TransferDto laterArrivedTransferDto = new TransferDto(TODAY.atTime(1, 20),
                TODAY.atTime(18, 20),
                new StationDto("Kraków Podgórze", "Krakow", "KRKP"),
                new StationDto("Warszawa Centralna", "Warszawa", "WAWC"),
                "Intercity",
                BigDecimal.valueOf(80),
                BigDecimal.valueOf(40.0),
                emptyList(), Collections.emptySet(), 0, 0, 0, 0);

        when(transferRepository.findTransfers(any(), any(), any()))
                .thenReturn(List.of(new Transfer(), new Transfer()));
        when(transferAssembler.assemble(any(),any(),any())).thenReturn(laterArrivedTransferDto,earlierArrivedTransferDto);

        TransferService transferService = new TransferService(transferRepository, transferAssembler, interchangeService);

        //when
        List<TransferDto> transfers = transferService.findTransfers(new SearchTransferDto("krk", "waw", TODAY.atTime(0, 1)));

        //then
        assertThat(transfers).containsExactly(earlierArrivedTransferDto,laterArrivedTransferDto);
    }

}