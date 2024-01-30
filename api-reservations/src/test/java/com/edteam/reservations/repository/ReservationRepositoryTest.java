package com.edteam.reservations.repository;

import com.edteam.reservations.model.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tags(@Tag("repository"))
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("Check the functionality of the repository")
class ReservationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationRepositoryTest.class);

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

    @Order(2)
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
        assertAll(
                () -> assertNotNull(reservation),
                () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                () -> assertEquals(destination, reservation.getItinerary().getSegment().get(0).getDestination())
        );
    }

    @Order(2)
    @Tag("success-case")
    @DisplayName("should return the information of all the reservations using CSV")
    @ParameterizedTest
    @CsvSource({"MIA,AEP", "BUE,SCL", "BUE,MIA"})
    void save_should_return_the_information_using_csv(String origin, String destination) {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Reservation reservation = repository.save(getReservation(null, origin, destination));

        // Then
        assertAll(
                () -> assertNotNull(reservation),
                () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                () -> assertEquals(destination, reservation.getItinerary().getSegment().get(0).getDestination())
        );
    }

    @Order(2)
    @Tag("success-case")
    @DisplayName("should return the information of all the reservations using parameters")
    @ParameterizedTest
    @ValueSource(strings = {"AEP", "MIA"})
    void save_should_return_the_information_using_parameters(String origin) {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Reservation reservation = repository.save(getReservation(null, origin, "MIA"));

        // Then
        assertAll(
                () -> assertNotNull(reservation),
                () -> assertEquals(origin, reservation.getItinerary().getSegment().get(0).getOrigin()),
                () -> assertEquals("MIA", reservation.getItinerary().getSegment().get(0).getDestination())
        );
    }


    @Order(1)
    @Tag("success-case")
    @DisplayName("should return the information of all the reservations")
    @Test
    void getReservations_should_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();
        repository.save(getReservation(null, "AEP", "MIA"));

        // When
        List<Reservation> result = repository.getReservations();

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertThat(result, hasSize(2)),
                () -> assertThat(getReservation(2L, "AEP", "MIA"), in(result)),
                () -> assertThat(result.get(0), hasProperty("id")),
                () -> assertThat(result.get(0).getPassengers().get(0).getFirstName(), stringContainsInOrder("A","s")),
                () -> assertThat(result.get(0).getPassengers().get(0).getFirstName(), matchesRegex("[a-zA-Z]+"))
        );
    }


    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void getReservation_should_return_the_information() throws InterruptedException {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
    }

    @Disabled
    @Tag("error-case")
    @DisplayName("should not return the information of the reservation by timeout problem")
    @Test
    @Timeout(value = 1L, unit = TimeUnit.MILLISECONDS)
    void getReservation_should_not_return_the_information_by_timeout() throws InterruptedException {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertAll(
                () -> assertNotNull(result),
                () -> assertTrue(result.isPresent()),
                () -> assertEquals(getReservation(1L, "EZE", "MIA"), result.get()));
    }

    @Order(1)
    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(6L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertTrue(result.isEmpty()));
    }

    private Reservation getReservation(Long id, String origin, String destination) {
        Passenger passenger = new Passenger();
        passenger.setFirstName("Andres");
        passenger.setLastName("Sacco");
        passenger.setId(1L);
        passenger.setDocumentType("DNI");
        passenger.setDocumentNumber("12345678");
        passenger.setBirthday(LocalDate.of(1985, 1, 1));

        Price price = new Price();
        price.setBasePrice(BigDecimal.ONE);
        price.setTotalTax(BigDecimal.ZERO);
        price.setTotalPrice(BigDecimal.ONE);

        Segment segment = new Segment();
        segment.setArrival("2025-01-01");
        segment.setDeparture("2024-12-31");
        segment.setOrigin(origin);
        segment.setDestination(destination);
        segment.setCarrier("AA");
        segment.setId(1L);

        Itinerary itinerary = new Itinerary();
        itinerary.setId(1L);
        itinerary.setPrice(price);
        itinerary.setSegment(List.of(segment));

        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setPassengers(List.of(passenger));
        reservation.setItinerary(itinerary);

        return reservation;
    }

}
