package com.tiba.invoice.category;

import com.tiba.invoice.common.PageResponse;
import com.tiba.invoice.exception.DuplicateEntityException;
import com.tiba.invoice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public Long save(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new DuplicateEntityException("CATEGORY_ALREADY_EXISTS");
        }

        return categoryRepository.save(categoryMapper.toCategory(categoryRequest)).getId();
    }

    public PageResponse<CategoryResponse> findAllPaged(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Category> categoryPage = categoryRepository.findAll(pageRequest);
        List<CategoryResponse> categoryResponseList = categoryPage.stream().map(categoryMapper::toResponse).toList();
        return new PageResponse<>(categoryResponseList, categoryPage.getNumber(), categoryPage.getSize(), categoryPage.getTotalElements(), categoryPage.getTotalPages(), categoryPage.isFirst(), categoryPage.isLast());
    }

    public Long update(CategoryRequest categoryRequest) {
        Category existingCategory = categoryRepository.findById(categoryRequest.id())
                .orElseThrow(() -> new NotFoundException("CATEGORY_NOT_FOUND"));

        if (!existingCategory.getName().equals(categoryRequest.name()) &&
                categoryRepository.existsByName(categoryRequest.name())) {
            throw new DuplicateEntityException("CATEGORY_ALREADY_EXISTS");
        }

        return categoryRepository.save(categoryMapper.toCategory(categoryRequest)).getId();
    }
}
