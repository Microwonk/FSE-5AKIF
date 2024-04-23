package at.kolleg.erplite.customermanagement.appservices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    @NotNull
    @Size(min = 10, max = 10)
    private String id;
    @NotNull
    @Size(min = 1)
    private String firstname;
    @Size(min = 1)
    @NotNull
    private String lastname;
    @NotNull
    @Email
    private String email;
    @NotNull
    private List<AddressDto> addressList;
}
