package com.edteam.reservations.dto;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Objects;

public class ItineraryDTO {

    @Valid
    private List<SegmentDTO> segment;

    private PriceDTO price;

    public List<SegmentDTO> getSegment() {
        return segment;
    }

    public void setSegment(List<SegmentDTO> segment) {
        this.segment = segment;
    }

    public PriceDTO getPrice() {
        return price;
    }

    public void setPrice(PriceDTO price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItineraryDTO that = (ItineraryDTO) o;
        return Objects.equals(segment, that.segment) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(segment, price);
    }

    @Override
    public String toString() {
        return "ItineraryDTO{" +
                "segment=" + segment +
                ", price=" + price +
                '}';
    }
}
