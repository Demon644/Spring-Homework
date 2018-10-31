package ua.logos.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class BookDTO {

    Long id;

    @NotNull(message = "Field 'NAME' can't be NULL")
    @Size(min = 2, max = 200, message = "'NAME' should be between 2 and 200 characters")
    private String name;

    private AuthorDTO author;

    private CategoryDTO category;

    private String image;
}
