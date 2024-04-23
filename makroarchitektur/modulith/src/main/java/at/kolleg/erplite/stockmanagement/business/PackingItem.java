package at.kolleg.erplite.stockmanagement.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackingItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String productNumber;
    private String productName;
    private int amount;
    private boolean packed;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore //if missed
    private Packing packing;
}
