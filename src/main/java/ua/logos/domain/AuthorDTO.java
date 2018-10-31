package ua.logos.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AuthorDTO {

    private Long id;

    @NotNull(message = "Field 'NAME' can't be NULL")
    @Size(min = 2, max = 200, message = "'NAME' length should be 2 characters and more")
    private String name;

    private String life;
}
