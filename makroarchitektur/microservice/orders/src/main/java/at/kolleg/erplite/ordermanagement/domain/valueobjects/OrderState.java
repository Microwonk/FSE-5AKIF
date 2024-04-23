package at.kolleg.erplite.ordermanagement.domain.valueobjects;

import at.kolleg.erplite.ordermanagement.marker.ValueObjectMarker;

@ValueObjectMarker
public enum OrderState {
    PLACED, PAYMENT_VERIFIED, PREPARING_FOR_DELIVERY, IN_DELIVERY, DELIVERED, CANCELED
}