package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import org.junit.jupiter.api.*;
import org.quickperf.annotation.ExpectMaxExecutionTime;
import org.quickperf.junit5.QuickPerfTest;
import org.quickperf.jvm.annotations.MeasureHeapAllocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static org.junit.jupiter.api.Assertions.*;

@QuickPerfTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("performance"))
@DisplayName("Check the functionality of the repository")
class PerformanceReservationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceReservationRepositoryTest.class);

    @BeforeEach
    void initialize_each_test() {
        LOGGER.info("Initialize the context on each test");
    }

    @AfterEach
    void destroy_each_test() {
        LOGGER.info("Destroy the context on each test");
    }

    @BeforeAll
    static void initialize_all_test() {
        LOGGER.info("Initialize the context on all test");
    }

    @AfterAll
    static void destroy_all_test() {
        LOGGER.info("Destroy the context on all test");
    }

    @MeasureHeapAllocation
    @ExpectMaxExecutionTime(seconds = 2)
    @Tag("success-case")
    @DisplayName("should return the information of the reservation with heap")
    @Test
    void getReservation_should_return_the_information_with_heap() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
    }
}
