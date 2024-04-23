package at.kolleg.erplite.stockmanagement.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Packing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String orderId;
    @Embedded
    private DeliveryData deliveryData;
    @OneToMany(mappedBy = "packing", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PackingItem> packingItemList;

}
