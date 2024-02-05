package com.edteam.reservations.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.Objects;

public class ReservationDTO {

    private Long id;

    @Valid
    @NotEmpty(message = "You need at least one passenger")
    private List<PassengerDTO> passengers;

    @Valid
    private ItineraryDTO itinerary;

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public ItineraryDTO getItinerary() {
        return itinerary;
    }

    public void setItinerary(ItineraryDTO itinerary) {
        this.itinerary = itinerary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ReservationDTO that = (ReservationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(passengers, that.passengers)
                && Objects.equals(itinerary, that.itinerary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passengers, itinerary);
    }

    @Override
    public String toString() {
        return "ReservationDTO{" + "id=" + id + ", passengers=" + passengers + ", itinerary=" + itinerary + '}';
    }
}
