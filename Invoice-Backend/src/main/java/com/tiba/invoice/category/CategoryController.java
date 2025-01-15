package com.tiba.invoice.category;

import com.tiba.invoice.common.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Long> createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok( categoryService.save(categoryRequest));
    }

    @GetMapping
    public ResponseEntity<PageResponse<CategoryResponse>> findAllPaged(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ){
        return ResponseEntity.ok( categoryService.findAllPaged(page, size));
    }

    @PutMapping
    public ResponseEntity<Long> updateCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok().body( categoryService.update(categoryRequest));
    }
}
