package com.edteam.reservations.service;

import com.edteam.reservations.connector.CatalogConnector;
import com.edteam.reservations.dto.*;
import com.edteam.reservations.enums.APIError;
import com.edteam.reservations.exception.EdteamException;
import com.edteam.reservations.repository.ReservationRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionService;

import static com.edteam.reservations.util.ReservationUtil.getReservationDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Tags(@Tag("service"))
@DisplayName("Check the functionality of the service")
class ReservationServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationServiceTest.class);
    ReservationRepository repository;
    ConversionService conversionService;
    CatalogConnector catalogConnector;

    @BeforeEach
    void initialize_each_test() {
        LOGGER.info("Initialize the context on each test");
        repository = mock(ReservationRepository.class);
        conversionService = mock(ConversionService.class);
        catalogConnector = mock(CatalogConnector.class);
    }


    @Tag("error-case")
    @DisplayName("should not return the information of the reservation")
    @Test
    void getReservation_should_not_return_the_information() {

        // Given
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

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
        ReservationService service = new ReservationService(repository, conversionService, catalogConnector);

        // When
        ReservationDTO result = service.getReservationById(1L);

        // Then
        assertAll(() -> assertNotNull(result), () -> assertEquals(getReservationDTO(1L, "EZE", "MIA"), result));
    }
}
