package com.kaufdev.railtable.transfer;

import com.kaufdev.railtable.transfer.business.TransferAssembler;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.domain.Transfer;
import com.kaufdev.railtable.transfer.infrastracture.StationDto;
import com.kaufdev.railtable.transfer.infrastracture.TransferDto;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;

class TransferAssemblerTest {
    private  final Station HEL = new Station("Hel", "Hel", "HEL");
    private final Station KRAKOW_PODGORZE = new Station("Kraków Podgórze", "Krakow", "KRKP");
    private final Station WARSZAWA_CENTRALNA = new Station("Warszawa Centralna", "Warszawa", "WAWC");
    private final Station GDANSK_GLOWNY = new Station("Gdańsk Główny", "Gdansk", "GG");
    private final LocalDate TODAY = LocalDate.of(2020,5,6);
    private final String PKP_INTERCITY = "PKP Intercity";

    TransferAssembler transferAssembler = new TransferAssembler();

    @Test
    public void shouldReturnTransferFromOneSection(){
        //given
        Section krk_waw_section = new Section(KRAKOW_PODGORZE, WARSZAWA_CENTRALNA, TODAY.atTime(1, 20), TODAY.atTime(2, 20), 40)
                .setNextSection(null);
        ReflectionTestUtils.setField(krk_waw_section,"id",1L);
        Transfer transfer = new Transfer(PKP_INTERCITY, BigDecimal.valueOf(2), Set.of(krk_waw_section));
        ReflectionTestUtils.setField(krk_waw_section,"transfer",transfer);


        //when
        TransferDto assembledTransferDto = transferAssembler.assemble(transfer, "KRKP", "WAWC");

        //then
        assertThat(assembledTransferDto)
                .isEqualTo(new TransferDto(TODAY.atTime(1, 20),
                        TODAY.atTime(2, 20),
                        new StationDto("Kraków Podgórze", "Krakow", "KRKP"),
                        new StationDto("Warszawa Centralna", "Warszawa", "WAWC"),
                        PKP_INTERCITY,
                        BigDecimal.valueOf(80),
                        BigDecimal.valueOf(40.0),
                        emptyList(),
                        Set.of(krk_waw_section.getId())));
    }

    @Test
    public void shouldReturnTransferFromCoupleSections(){
        //given
        Section gds_hel_section = new Section(GDANSK_GLOWNY, HEL, TODAY.atTime(5, 10), TODAY.atTime(9, 0), 30);
        Section waw_gds_section = new Section(WARSZAWA_CENTRALNA, GDANSK_GLOWNY, TODAY.atTime(2, 30), TODAY.atTime(5, 0), 200).setNextSection(gds_hel_section);
        Section krk_waw_section = new Section(KRAKOW_PODGORZE, WARSZAWA_CENTRALNA, TODAY.atTime(1, 20), TODAY.atTime(2, 20), 40).setNextSection(waw_gds_section);
        Transfer transferEntity = new Transfer(PKP_INTERCITY,BigDecimal.valueOf(4),Set.of(krk_waw_section,waw_gds_section, gds_hel_section));

        ReflectionTestUtils.setField(gds_hel_section,"transfer",transferEntity);
        ReflectionTestUtils.setField(waw_gds_section,"transfer",transferEntity);
        ReflectionTestUtils.setField(krk_waw_section,"transfer",transferEntity);

        //when
        TransferDto assembledTransferDto = transferAssembler.assemble(transferEntity, "KRKP", "HEL");

        //then
        assertThat(assembledTransferDto.getArrivalTime()).isEqualTo(TODAY.atTime(9,0));
        assertThat(assembledTransferDto.getOperator()).isEqualTo(PKP_INTERCITY);
        assertThat(assembledTransferDto.getOutboundTime()).isEqualTo(TODAY.atTime(1,20));
        assertThat(assembledTransferDto.getFirstClassPrice()).isEqualByComparingTo(BigDecimal.valueOf(1080));
        assertThat(assembledTransferDto.getSecondClassPrice()).isEqualByComparingTo(BigDecimal.valueOf(540));
    }
}