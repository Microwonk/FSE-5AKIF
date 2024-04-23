package at.kolleg.erplite.stockmanagement.adapter;

import at.kolleg.erplite.stockmanagement.business.Packing;
import at.kolleg.erplite.stockmanagement.business.PackingItem;
import at.kolleg.erplite.stockmanagement.db.PackingItemRepository;
import at.kolleg.erplite.stockmanagement.db.PackingRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@AllArgsConstructor
@RequestMapping("/stock")
public class PackingRestController {

    PackingItemRepository packingItemRepository;
    PackingRepository packingRepository;
    StockMessagePublisher stockMessagePublisher;

    @PostMapping("/setPackedForPacking/{packingItemId}")
    public void setPackingItemPackedForPacking(@PathVariable Long packingItemId) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling packing for item# " + packingItemId);

        Optional<PackingItem> optionalPackingItem = this.packingItemRepository.findById(packingItemId);
        if (optionalPackingItem.isPresent()) {
            PackingItem packingItem = optionalPackingItem.get();
            packingItem.setPacked(true);
            packingItemRepository.save(packingItem);

            Long packingId = packingItem.getPacking().getId();

            Optional<Packing> packing = this.packingRepository.findById(packingId);

            boolean allpaked = true;
            for (PackingItem item : packing.get().getPackingItemList()) {
                if (!item.isPacked()) allpaked = false;
            }
            if (allpaked) {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "All items for order# " + packing.get().getOrderId() + "packed. Publishing event ...");
                this.stockMessagePublisher.publishOrderPackedSpringEventForOrderId(packing.get().getOrderId());
            }
        }
    }

    @GetMapping("/packings/{id}")
    public ResponseEntity<Packing> getPackingById(@PathVariable Long id) {
        Optional<Packing> p = this.packingRepository.findById(id);
        if (p.isPresent()) {
            return ResponseEntity.ok(p.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/packings/whithorderid/{ordernr}")
    public ResponseEntity<Packing> getPackingByOrderNr(@PathVariable String ordernr) {
        Packing p = this.packingRepository.findByOrderId(ordernr);
        if (p != null) {
            return ResponseEntity.ok(p);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/packings")
    public ResponseEntity<List<Packing>> getAllPackings() {
        List<Packing> packingLists = this.packingRepository.findAll();
        return ResponseEntity.ok(packingLists);
    }
}
