package at.kolleg.erplitestock.stockmanagement.adapter;

import at.kolleg.erplitestock.stockmanagement.business.Packing;
import at.kolleg.erplitestock.stockmanagement.business.PackingItem;
import at.kolleg.erplitestock.stockmanagement.db.PackingItemRepository;
import at.kolleg.erplitestock.stockmanagement.db.PackingRepository;
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
    public ResponseEntity setPackingItemPackedForPacking(@PathVariable Long packingItemId) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling packing for item# " + packingItemId);

        Optional<PackingItem> optionalPackingItem = this.packingItemRepository.findById(packingItemId);

        if(!optionalPackingItem.isPresent())
        {
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Packing for item# " + packingItemId + " not possible, item not present!");
            return ResponseEntity.badRequest().body("Packing for item# " + packingItemId + " not possible, item not present!");
        } else {
            PackingItem packingItem = optionalPackingItem.get();

            if(packingItem.isPacked())
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Packing for item# " + packingItemId + " not possible, item already packed!");
                return ResponseEntity.badRequest().body("Packing for item# " + packingItemId + " not possible, item already packed!");
            } else
            {
                packingItem.setPacked(true);
                packingItemRepository.save(packingItem);

                Long packingId = packingItem.getPacking().getId();

                Optional<Packing> packing = this.packingRepository.findById(packingId);

                //check if all are packed, when one new item is packed (above) -> only works because we leave if item to pack that is already packed, see above
                boolean allpaked = true;
                for (PackingItem item : packing.get().getPackingItemList()) {
                    if (!item.isPacked()) allpaked = false;
                }
                String returnMessage = "";
                if (allpaked) {
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "All items for order# " + packing.get().getOrderId() + "packed. Publishing event ...");
                    this.stockMessagePublisher.publishOrderPackedEventForOrderId(packing.get().getOrderId());
                    returnMessage += "All items for order# " + packing.get().getOrderId() + "packed.";
                }
                returnMessage = "Packing for item# " + packingItemId + " done!" + returnMessage;
                return ResponseEntity.ok().body(returnMessage);
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
        Optional<Packing> p = this.packingRepository.findByOrderId(ordernr);
        if (p.isPresent()) {
            return ResponseEntity.ok(p.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/packings")
    public ResponseEntity<List<Packing>> getAllPackings() {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Handling getAllPackings API-Request ...");

        List<Packing> packingLists = this.packingRepository.findAll();
        return ResponseEntity.ok(packingLists);
    }
}
