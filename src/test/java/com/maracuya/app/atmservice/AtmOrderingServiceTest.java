package com.maracuya.app.atmservice;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.maracuya.app.atmservice.RequestType.FAILURE_RESTART;
import static com.maracuya.app.atmservice.RequestType.PRIORITY;
import static com.maracuya.app.atmservice.RequestType.SIGNAL_LOW;
import static com.maracuya.app.atmservice.RequestType.STANDARD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AtmOrderingServiceTest {

    private final AtmOrderingService atmOrderingService = new AtmOrderingService();

    @Test
    void regionWithLowerIdShouldTakePrecedence() {
        List<ServiceTask> tasks = List.of(
            new ServiceTask(3, STANDARD, 1),
            new ServiceTask(2, STANDARD, 1),
            new ServiceTask(1, STANDARD, 1)
        );

        List<AtmDetails> detailsList = atmOrderingService.calculateOrder(tasks).atms();

        assertThat(detailsList)
            .extracting(AtmDetails::region)
            .containsExactly(1, 2, 3);
    }

    @Test
    void tasksInTheSameRegionShouldBeProcessedAccordingToRequestTypePriority() {
        List<ServiceTask> tasks = List.of(
            new ServiceTask(1, PRIORITY, 2),
            new ServiceTask(1, FAILURE_RESTART, 1),
            new ServiceTask(1, STANDARD, 3),
            new ServiceTask(1, SIGNAL_LOW, 4)
        );

        List<AtmDetails> detailsList = atmOrderingService.calculateOrder(tasks).atms();

        assertThat(detailsList)
            .extracting(AtmDetails::atmId)
            .containsExactly(1, 2, 4, 3);
    }

    @Test
    void eachAtmShouldBeProcessedAtMostOnceInGivenRegion() {
        List<ServiceTask> tasks = List.of(
            new ServiceTask(1, STANDARD, 1),
            new ServiceTask(1, FAILURE_RESTART, 1),
            new ServiceTask(1, SIGNAL_LOW, 1),
            new ServiceTask(2, STANDARD, 1)
        );

        List<AtmDetails> detailsList = atmOrderingService.calculateOrder(tasks).atms();

        assertThat(detailsList)
            .extracting(AtmDetails::region, AtmDetails::atmId)
            .containsExactly(
                tuple(1, 1),
                tuple(2, 1)
            );
    }
}
