package com.tiba.invoice.customer;

import com.tiba.invoice.common.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.save(request));
    }

    @GetMapping()
    public ResponseEntity<PageResponse<CustomerResponse>> findAllPaged(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(customerService.findAllPaged(page, size));
    }

}
