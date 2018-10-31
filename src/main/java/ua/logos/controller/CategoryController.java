package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.logos.domain.CategoryDTO;
import ua.logos.domain.ErrorDTO;
import ua.logos.service.CategoryService;
import ua.logos.service.FileStorageService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CategoryDTO dto, BindingResult br) {

        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get().toString();

            ErrorDTO error = new ErrorDTO(errMsg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        categoryService.saveCategory(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<CategoryDTO> dtos = categoryService.findAllCategories();
        return new ResponseEntity<List<CategoryDTO>>(dtos, HttpStatus.OK);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<?> getById(@PathVariable("authorId") Long id) {
        CategoryDTO dto = categoryService.findCategoryById(id);
        return new ResponseEntity<CategoryDTO> (dto, HttpStatus.OK);
    }
}
