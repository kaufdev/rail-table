package com.kaufdev.railtable.transfer.business;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.kaufdev.railtable.transfer.domain.Section;
import com.kaufdev.railtable.transfer.domain.Station;
import com.kaufdev.railtable.transfer.infrastracture.graph.SectionEdge;
import com.kaufdev.railtable.transfer.infrastracture.graph.DijkstraPathFinderImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

class DijkstraPathFinderImplTest {

    private final Station KRK_STATION = new Station("Kraków główny","Kraków", "KRK");
    private final Station WAW_STATION = new Station("Warszawa główny","Warszawa", "WAW");
    private final Station POZ_STATION = new Station("Poznan główny","Poznan", "POZ");
    private final Station LDZ_STATION = new Station("Lodz główny","Łódź", "LDZ");

    @Test()
    public void whenPathIsImpossibleEmptyOptionalShouldBeReturned(){
        //given
        Section waw_krk = new Section(WAW_STATION,
                KRK_STATION,
                LocalDateTime.of(2000, 12, 1, 9, 0),
                LocalDateTime.of(2000, 12, 1, 10, 0),
                10);
        DijkstraPathFinderImpl pathFinder = new DijkstraPathFinderImpl();
        //when
        List<Section> sectionPath = pathFinder.getPath(Set.of(waw_krk), "WAW", "POZ");
        //then
        Assertions.assertThat(sectionPath).isEmpty();

    }

    @Test
    public void whenTwoMultiPathsAsPossibleQuickerShouldBeChosen(){
        //given
        Section waw_krk = new Section(KRK_STATION,
                WAW_STATION,
                LocalDateTime.of(2000, 12, 1, 9, 0),
                LocalDateTime.of(2000, 12, 1, 10, 0),
                10);


        Section waw_poz = new Section(WAW_STATION,
                POZ_STATION,
                LocalDateTime.of(2000, 12, 1, 10, 10),
                LocalDateTime.of(2000, 12, 1, 10, 40),
                10);

        Section krk_ldz = new Section(KRK_STATION,LDZ_STATION,LocalDateTime.of(2000, 12, 1, 9, 20),
                LocalDateTime.of(2000, 12, 1, 9, 22),10);

        Section ldz_poz = new Section(LDZ_STATION, POZ_STATION,LocalDateTime.of(2000, 12, 1, 9, 30),
                LocalDateTime.of(2000, 12, 1, 10, 0),10);

        ReflectionTestUtils.setField(waw_krk,"id",1L);
        ReflectionTestUtils.setField(waw_poz,"id",2L);
        ReflectionTestUtils.setField(krk_ldz,"id",3L);
        ReflectionTestUtils.setField(ldz_poz,"id",4L);


        DijkstraPathFinderImpl pathFinder = new DijkstraPathFinderImpl();

        //when
        List<Section> path = pathFinder.getPath(Set.of(waw_krk,waw_poz,krk_ldz,ldz_poz), "KRK", "POZ");

        //then
        org.assertj.core.api.Assertions.assertThat(path).extracting("id").containsExactly(3L,4L);
    }
}