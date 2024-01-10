package com.edteam.reservations.repository;

import com.edteam.reservations.model.*;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReservationRepositoryTest {

    @Test
    void my_first_test() {

        ReservationRepository repository = new ReservationRepository();

        Optional<Reservation> result = repository.getReservationById(1L);

        assertEquals(1L, result.get().getId());
    }

}
