package com.edteam.reservations.service;

import com.edteam.reservations.connector.CatalogConnector;
import com.edteam.reservations.dto.ReservationDTO;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.model.Reservation;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import java.util.Optional;

import static com.edteam.reservations.util.ReservationUtil.getReservation;
import static com.edteam.reservations.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tags(@Tag("parallel"))
@DisplayName("Check the functionality of the service")
class ParallelReservationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParallelReservationServiceTest.class);

    @Mock
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
    @ResourceLock(value = "reservation")
    void getReservation_should_not_return_the_information() throws InterruptedException {

        LOGGER.info("Running getReservation_should_not_return_the_information");

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);
        when(repository.getReservationById(6L)).thenReturn(Optional.empty());

        Thread.sleep(500);

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
    @ResourceLock(value = "reservation")
    void getReservation_should_return_the_information() throws InterruptedException {

        LOGGER.info("Running getReservation_should_return_the_information");

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "BUE", "MAD");
        when(repository.getReservationById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);

        Thread.sleep(500);

        // When
        ReservationDTO result = service.getReservationById(1L);

        // Then
        verify(repository, Mockito.atMostOnce()).getReservationById(1L);
        verify(conversionService, Mockito.atMostOnce()).convert(reservationModel, ReservationDTO.class);
        verify(catalogConnector, Mockito.never()).getCity(any());

        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "BUE", "MAD"), result));
    }

    @Tag("success-case")
    @DisplayName("should return remove a reservation")
    @Test
    void delete_should_remove_a_reservation() throws InterruptedException {

        LOGGER.info("Running delete_should_remove_a_reservation");

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        Reservation reservationModel = getReservation(1L, "BUE", "MAD");
        when(repository.getReservationById(1L)).thenReturn(Optional.of(reservationModel));

        ReservationDTO reservationDTO = getReservationDTO(1L, "BUE", "MAD");
        when(conversionService.convert(reservationModel, ReservationDTO.class)).thenReturn(reservationDTO);

        doNothing().when(repository).delete(1L);

        Thread.sleep(500);

        // When
        service.delete(1L);

        // Then
        verify(repository, Mockito.atMostOnce()).delete(1L);

        verify(repository, Mockito.atMostOnce()).getReservationById(1L);
        verify(conversionService, Mockito.atMostOnce()).convert(reservationModel, ReservationDTO.class);
        verify(catalogConnector, Mockito.never()).getCity(any());
    }
}
