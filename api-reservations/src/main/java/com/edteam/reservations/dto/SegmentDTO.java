package com.edteam.reservations.dto;

import com.edteam.reservations.validator.CityFormatConstraint;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class SegmentDTO {

    @CityFormatConstraint
    private String origin;

    @CityFormatConstraint
    private String destination;

    @NotBlank(message = "departure is mandatory")
    private String departure;

    @NotBlank(message = "arrival is mandatory")
    private String arrival;

    @NotBlank(message = "carrier is mandatory")
    private String carrier;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentDTO that = (SegmentDTO) o;
        return Objects.equals(origin, that.origin) && Objects.equals(destination, that.destination) && Objects.equals(departure, that.departure) && Objects.equals(arrival, that.arrival) && Objects.equals(carrier, that.carrier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, departure, arrival, carrier);
    }

    @Override
    public String toString() {
        return "SegmentDTO{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", departure='" + departure + '\'' +
                ", arrival='" + arrival + '\'' +
                ", carrier='" + carrier + '\'' +
                '}';
    }
}
