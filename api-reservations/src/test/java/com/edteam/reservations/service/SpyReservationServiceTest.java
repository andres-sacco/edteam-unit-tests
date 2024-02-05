package com.edteam.reservations.service;

import com.edteam.reservations.connector.CatalogConnector;
import com.edteam.reservations.dto.ReservationDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static com.edteam.reservations.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags(@Tag("service"))
@DisplayName("Check the functionality of the service")
class SpyReservationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpyReservationServiceTest.class);

    @Spy
    ReservationRepository repository;

    @Mock
    ConversionService conversionService;

    @Mock
    CatalogConnector catalogConnector;

    @BeforeEach
    void initialize_each_test() {
        LOGGER.info("Initialize the context on each test");
        MockitoAnnotations.openMocks(this);
    }

    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(6L)).thenReturn(Optional.empty());

        // When
        EdteamException exception = assertThrows(EdteamException.class, () -> {
            service.getReservationById(6L);
        });

        // Then
        verify(repository, Mockito.atMostOnce()).getReservationById(6L);

        assertAll(() -> assertNotNull(exception),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getMessage(), exception.getDescription()),
                () -> assertEquals(APIError.RESERVATION_NOT_FOUND.getHttpStatus(), exception.getStatus()));
    }

    @Tag("success-case")
    @DisplayName("should return the information of the reservation")
    @Test
    void getReservation_should_return_the_information() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "EZE", "MIA");

        ReservationDTO reservationDTO = getReservationDTO(1L, "EZE", "MIA");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);

        // When
        ReservationDTO result = service.getReservationById(1L);

        // Then
        verify(repository, Mockito.atMostOnce()).getReservationById(1L);
        verify(conversionService, Mockito.atMostOnce()).convert(reservationModel, ReservationDTO.class);
        verify(catalogConnector, Mockito.never()).getCity(any());

        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "EZE", "MIA"), result));
    }
}