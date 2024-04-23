package at.kolleg.erplite.customermanagement.appservices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    @NotNull
    @Size(min = 1)
    private String street;
    @NotNull
    @Size(min = 1)
    private String zipcode;
    @NotNull
    @Size(min = 1)
    private String city;
    @NotNull
    @Size(min = 1)
    private String country;
}
