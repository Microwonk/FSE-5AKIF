package at.kolleg.erplite.ordermanagement.db;

import at.kolleg.erplite.ordermanagement.domain.valueobjects.OrderState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
class OrderDbEntity {
    @Id
    private String orderID;
    @Embedded
    private OrderCustomerDetailsDbEntity orderCustomerDetails;

    private LocalDateTime date;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<LineItemDbEntity> lineItems;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    private BigDecimal taxTotal;

    private BigDecimal netTotal;

    private BigDecimal grossTotal;

    @Version
    private Long version;

    public OrderDbEntity(String orderID, OrderCustomerDetailsDbEntity orderCustomerDetails, LocalDateTime date, OrderState state, BigDecimal taxTotal, BigDecimal netTotal, BigDecimal grossTotal) {
        this.orderID = orderID;
        this.orderCustomerDetails = orderCustomerDetails;
        this.date = date;
        this.state = state;
        this.taxTotal = taxTotal;
        this.netTotal = netTotal;
        this.grossTotal = grossTotal;
        this.lineItems = new ArrayList<>();
    }
}
