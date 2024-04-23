package at.kolleg.erplite.ordermanagement.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.math.BigDecimal;


@Embeddable
@Data
@NoArgsConstructor
class LineItemDbEntity {
    private int orderPosition;
    private String productNumber;
    private String productName;
    private BigDecimal priceNet;
    private int tax;
    private int amount;
    private BigDecimal totalNetLine;
    private BigDecimal totalTaxLine;
    private BigDecimal totalGrossLine;

    public LineItemDbEntity(int orderPosition, String productNumber, String productName, BigDecimal priceNet, int tax, int amount, BigDecimal totalNetLine, BigDecimal totalTaxLine, BigDecimal totalGrossLine, OrderDbEntity orderDbEntity) {
        this.orderPosition = orderPosition;
        this.productNumber = productNumber;
        this.productName = productName;
        this.priceNet = priceNet;
        this.tax = tax;
        this.amount = amount;
        this.totalNetLine = totalNetLine;
        this.totalTaxLine = totalTaxLine;
        this.totalGrossLine = totalGrossLine;
    }
}
