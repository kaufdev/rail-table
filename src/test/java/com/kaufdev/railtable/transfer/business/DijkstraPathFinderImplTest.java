package com.kaufdev.railtable.transfer.business;

import com.google.common.graph.MutableNetwork;
import com.google.common.graph.NetworkBuilder;
import com.kaufdev.railtable.transfer.infrastracture.graph.SectionEdge;
import com.kaufdev.railtable.transfer.infrastracture.TransferNotFoundException;
import com.kaufdev.railtable.transfer.infrastracture.graph.DijkstraPathFinderImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class DijkstraPathFinderImplTest {

    @Test()
    public void whenPathIsImpossibleEmptyOptionalShouldBeReturned(){
        //given
        MutableNetwork<String, SectionEdge> network = NetworkBuilder.undirected().build();
        network.addEdge("WAW","KRK",new SectionEdge(1L,
                LocalDateTime.of(2000,12,1,9,0),
                LocalDateTime.of(2000,12,1,10,0)));

        DijkstraPathFinderImpl pathFinder = new DijkstraPathFinderImpl();
        //when
        List<Long> pathOptional = pathFinder.getPath(network, "WAW", "POZ");
        //then
        Assertions.assertThat(pathOptional).isEmpty();

    }

    @Test
    public void whenTwoMultiPathsAsPossibleQuickerShouldBeChosen(){
        //given
        SectionEdge krk_waw = new SectionEdge(1L,
                LocalDateTime.of(2000, 12, 1, 9, 0),
                LocalDateTime.of(2000, 12, 1, 10, 0));

        SectionEdge waw_poz = new SectionEdge(2L,
                LocalDateTime.of(2000, 12, 1, 10, 10),
                LocalDateTime.of(2000, 12, 1, 10, 40));

        SectionEdge krk_ldz = new SectionEdge(3L,
                LocalDateTime.of(2000, 12, 1, 9, 20),
                LocalDateTime.of(2000, 12, 1, 9, 22));

        SectionEdge ldz_poz = new SectionEdge(4L,
                LocalDateTime.of(2000, 12, 1, 9, 30),
                LocalDateTime.of(2000, 12, 1, 10, 0));

        MutableNetwork<String, SectionEdge> network = NetworkBuilder.undirected().build();
        network.addEdge("KRK","WAW", krk_waw);
        network.addEdge("WAW","POZ", waw_poz);
        network.addEdge("KRK","LDZ", krk_ldz);
        network.addEdge("LDZ","POZ", ldz_poz);

        DijkstraPathFinderImpl pathFinder = new DijkstraPathFinderImpl();

        //when
        List<Long> path = pathFinder.getPath(network, "KRK", "POZ");

        //then
        org.assertj.core.api.Assertions.assertThat(path).containsExactly(3L,4L);
    }
}