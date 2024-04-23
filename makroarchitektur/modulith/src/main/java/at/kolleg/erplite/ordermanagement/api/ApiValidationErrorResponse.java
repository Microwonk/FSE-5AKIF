package at.kolleg.erplite.ordermanagement.api;

import at.kolleg.erplite.sharedkernel.marker.ValueObjectMarker;

import java.util.Map;

@ValueObjectMarker
record ApiValidationErrorResponse(String errorCode, Map<String, String> errorMessages) {

}
