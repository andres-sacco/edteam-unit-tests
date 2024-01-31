package com.edteam.reservations.repository;

import com.edteam.reservations.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("repository"))
@DisplayName("Check the functionality of the repository")
class NestedReservationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NestedReservationRepositoryTest.class);

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

    @Nested
    class GetReservation {
        @Tag("success-case")
        @DisplayName("should return the information of the reservation")
        @Test
        void getReservation_should_return_the_information() throws InterruptedException {

            // Given
            ReservationRepository repository = new ReservationRepository();

            // When
            Optional<Reservation> result = repository.getReservationById(1L);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isPresent()),
                    () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
        }

        @Tag("error-case")
        @DisplayName("should not return the information of the reservation")
        @Test
        void getReservation_should_not_return_the_information() {

            // Given
            ReservationRepository repository = new ReservationRepository();

            // When
            Optional<Reservation> result = repository.getReservationById(50L);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
        }
    }

    @Nested
    class SaveReservation {

        @Tag("success-case")
        @DisplayName("should return the information of all the reservations using external files")
        @ParameterizedTest
        @CsvFileSource(resources = "/save-repository.csv")
        void save_should_return_the_information_using_external_file(String origin, String destination) {

            // Given
            ReservationRepository repository = new ReservationRepository();

            // When
            Reservation reservation = repository.save(getReservation(null, origin, destination));

            // Then
            assertAll(() -> assertNotNull(reservation),
                    () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals(destination, reservation.getItinerary().getSegment().get(0).getDestination()));
        }

        @Tag("success-case")
        @DisplayName("should return the information of all the reservations using CSV")
        @ParameterizedTest
        @CsvSource({ "MIA,AEP", "BUE,SCL", "BUE,MIA" })
        void save_should_return_the_information_using_csv(String origin, String destination) {

            // Given
            ReservationRepository repository = new ReservationRepository();

            // When
            Reservation reservation = repository.save(getReservation(null, origin, destination));

            // Then
            assertAll(() -> assertNotNull(reservation),
                    () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals(destination, reservation.getItinerary().getSegment().get(0).getDestination()));
        }

        @Tag("success-case")
        @DisplayName("should return the information of all the reservations using parameters")
        @ParameterizedTest
        @ValueSource(strings = { "AEP", "MIA" })
        void save_should_return_the_information_using_parameters(String origin) {

            // Given
            ReservationRepository repository = new ReservationRepository();

            // When
            Reservation reservation = repository.save(getReservation(null, origin, "MIA"));

            // Then
            assertAll(() -> assertNotNull(reservation),
                    () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                    () -> assertEquals("MIA", reservation.getItinerary().getSegment().get(0).getDestination()));
        }
    }

}
