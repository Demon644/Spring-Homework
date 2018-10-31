package ua.logos.domain;

import lombok.Data;
import ua.logos.validation.CheckCategoryExists;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Data
public class CategoryDTO {

    private Long id;


    @CheckCategoryExists(message = "Category already exists")
    @NotNull(message = "Field 'CATEGORY' can't be NULL")
    @Size(min = 2, max = 200, message = "'NAME' length should be 2 characters and more")
    private String category;
}
