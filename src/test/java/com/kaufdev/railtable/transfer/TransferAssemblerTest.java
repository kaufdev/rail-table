package com.kaufdev.railtable.transfer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

class TransferAssemblerTest {
    private final Station KRAKOW_PODGORZE = new Station("Kraków Podgórze", "Krakow", "KRKP");
    private final Station WARSZAWA_CENTRALNA = new Station("Warszawa Centralna", "Warszawa", "WAWC");
    private final Station GDANSK_GLOWNY = new Station("Gdańsk Główny", "Gdansk", "GG");
    private final LocalDate TODAY = LocalDate.of(2020,5,6);
    private final String PKP_INTERCITY = "PKP Intercity";

    TransferAssembler transferAssembler = new TransferAssembler();

    @Test
    public void shouldReturnTransferFromOneSection(){
        //given
        final Section krk_waw_section = new Section(KRAKOW_PODGORZE, WARSZAWA_CENTRALNA, TODAY.atTime(1, 20), TODAY.atTime(2, 20), 40)
                .setNextSection(null);
        final Transfer transfer = new Transfer(PKP_INTERCITY, BigDecimal.valueOf(2), Set.of(krk_waw_section));

        //when
        TransferDto assembledTransferDto = transferAssembler.assemble(transfer, "KRKP", "WAWC");

        //then
        Assertions.assertThat(assembledTransferDto)
                .isEqualTo(new TransferDto(TODAY.atTime(1, 20),
                        TODAY.atTime(2, 20),
                        "KRKP",
                        "WAWC",
                        "Kraków Podgórze",
                        "Warszawa Centralna",
                        PKP_INTERCITY,
                        BigDecimal.valueOf(80),
                        BigDecimal.valueOf(40.0)));
    }
}