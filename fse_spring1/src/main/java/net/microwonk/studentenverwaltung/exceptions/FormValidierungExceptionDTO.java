package net.microwonk.studentenverwaltung.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class FormValidierungExceptionDTO {

    String code;
    private HashMap<String,String> formValidationErrors;

    public FormValidierungExceptionDTO(String code) {
        this.code = code;
        this.formValidationErrors = new HashMap<>();
    }

    public void addFormValidationError(String fieldName, String fieldErrorMessage) {
        this.formValidationErrors.put(fieldName,fieldErrorMessage);
    }

}
