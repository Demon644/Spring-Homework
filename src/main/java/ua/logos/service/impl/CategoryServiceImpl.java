package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.logos.domain.CategoryDTO;
import ua.logos.entity.CategoryEntity;
import ua.logos.exception.ResourceNotFoundException;
import ua.logos.repository.CategoryRepository;
import ua.logos.service.CategoryService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapperUtils categoryMapper;

    @Override
    public void saveCategory(CategoryDTO dto) {

        CategoryEntity entity = categoryMapper.map(dto, CategoryEntity.class);
        categoryRepository.save(entity);
    }

    @Override
    public CategoryDTO findCategoryById(Long id) {

        CategoryEntity entity = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record with id[" + id + "] not found")
        );

        CategoryDTO dto = categoryMapper.map(entity, CategoryDTO.class);
        return dto;
    }

    @Override
    public List<CategoryDTO> findAllCategories() {
        List<CategoryEntity> entities = categoryRepository.findAll();
        List<CategoryDTO> dtos = categoryMapper.mapAll(entities, CategoryDTO.class);
        return dtos;
    }
}
