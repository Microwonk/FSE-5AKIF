package at.kolleg.erplite.ordermanagement.api;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

@ValueObjectMarker
record ApiErrorResponse(String errorCode, String errorMessage) {
}
