package at.kolleg.erplite.sharedkernel.dtos;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public record CartItem(
        @NotNull @Size(min = 10, max = 10, message = "Product number must have a size of 10!") String productNumber,
        @NotNull @Size(min = 1, message = "Product name must not be null and must have at least 1 Character!") String productName,
        @Positive(message = "Net price must be bigger than 0!") double priceNet,
        @Range(min = 0, max = 100, message = "Tax must be between 0 and 100!") int tax,
        @Min(value = 0, message = "Amount must be positive!") int amount) {
}
