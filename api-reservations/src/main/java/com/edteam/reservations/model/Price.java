package com.edteam.reservations.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private Long id;
    private BigDecimal totalPrice;

    private BigDecimal totalTax;

    private BigDecimal basePrice;

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(id, price.id) && Objects.equals(totalPrice, price.totalPrice) && Objects.equals(totalTax, price.totalTax) && Objects.equals(basePrice, price.basePrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, totalTax, basePrice);
    }
}
