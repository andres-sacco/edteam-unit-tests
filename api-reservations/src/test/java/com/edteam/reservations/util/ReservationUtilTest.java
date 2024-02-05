package com.edteam.reservations.util;

import com.edteam.reservations.model.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tags(@Tag("util"))
@DisplayName("Check the functionality of the util")
class ReservationUtilTest {

    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void getReservation_should_return_the_information() {

        // When
        Reservation result = ReservationUtil.getReservation(1L, "MIA", "EZE");
        Segment segment = result.getItinerary().getSegment().get(0);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(1L, result.getId()),
                () -> assertEquals("MIA", segment.getOrigin()), () -> assertEquals("EZE", segment.getDestination()));
    }

    @Tag("success-case")
    @DisplayName("should return the information of the reservation using mocks")
    @Test
    void getReservation_should_return_the_information_with_mocks() {

        try (MockedStatic<ReservationUtil> utilities = Mockito.mockStatic(ReservationUtil.class)) {

            // Given
            utilities.when(() -> ReservationUtil.getReservation(1L, "BUE", "MIA"))
                    .thenReturn(getReservation(2L, "BUE", "MIA"));

            // When
            Reservation result = ReservationUtil.getReservation(1L, "BUE", "MIA");
            Segment segment = result.getItinerary().getSegment().get(0);

            // Then
            assertAll(() -> assertNotNull(result), () -> assertEquals(2L, result.getId()),
                    () -> assertEquals("BUE", segment.getOrigin()),
                    () -> assertEquals("MIA", segment.getDestination()));

        }
    }

    public static Reservation getReservation(Long id, String origin, String destination) {
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
