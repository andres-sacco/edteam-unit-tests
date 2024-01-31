package com.edteam.reservations.service;

import com.edteam.reservations.dto.*;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.*;

import static com.edteam.reservations.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;

@Tags(@Tag("service"))
@DisplayName("Check the functionality of the service")
class ReservationServiceTest {

    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();
        ReservationService service = new ReservationService(repository, null, null);

        // When
        EdteamException exception = assertThrows(EdteamException.class, () -> {
            service.getReservationById(6L);
        });

        // Then
        assertAll(() -> assertNotNull(exception),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()));
    }

    @Disabled
    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void getReservation_should_return_the_information() {

        // Given
        ReservationRepository repository = new ReservationRepository();
        ReservationService service = new ReservationService(repository, null, null);

        // When
        ReservationDTO result = service.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "EZE", "MIA"), result));
    }
}
