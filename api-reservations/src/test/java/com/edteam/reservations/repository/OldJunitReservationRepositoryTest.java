package com.edteam.reservations.repository;

import com.edteam.reservations.model.Reservation;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static org.junit.Assert.*;

public class OldJunitReservationRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OldJunitReservationRepositoryTest.class);

    @Before
    public void initialize_each_test() {
        LOGGER.info("Initialize the context on each test");
    }

    @After
    public void destroy_each_test() {
        LOGGER.info("Destroy the context on each test");
    }

    @BeforeClass
    public static void initialize_all_test() {
        LOGGER.info("Initialize the context on all test");
    }

    @AfterClass
    public static void destroy_all_test() {
        LOGGER.info("Destroy the context on all test");
    }

    @Test
    public void getReservation_should_return_the_information() throws InterruptedException {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(1L);

        // Then
        assertNotNull(result);
        assertTrue(result.isPresent());
        assertEquals(getReservation(1L, "EZE", "MIA"), result.get());
    }

    @Test
    public void getReservation_should_not_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();

        // When
        Optional<Reservation> result = repository.getReservationById(95L);

        // Then
        assertNotNull(result);
        assertFalse(result.isPresent());
    }

}
