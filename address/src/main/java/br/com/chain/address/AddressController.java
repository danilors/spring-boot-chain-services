package br.com.chain.address;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<Address> getAllAddress() {
        return addressService.getAllAddress();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        log.info("[START] getting address by id {}", id);
        var result = addressService.getAddressById(id);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("[END] getting address result: {}", result);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> createAddress(@RequestBody Address address) {
        return ResponseEntity.ok(addressService.createAddress(address));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        var result = addressService.updateAddress(id, address);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok().build();
    }
}