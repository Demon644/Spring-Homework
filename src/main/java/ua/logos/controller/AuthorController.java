package ua.logos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.logos.domain.AuthorDTO;
import ua.logos.domain.ErrorDTO;
import ua.logos.service.AuthorService;
import ua.logos.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AuthorDTO dto, BindingResult br) {

        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get().toString();

            ErrorDTO error = new ErrorDTO(errMsg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        authorService.saveAuthor(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<AuthorDTO> dtos = authorService.findAllAuthors();
        return new ResponseEntity<List<AuthorDTO>>(dtos, HttpStatus.OK);
    }

    @GetMapping("{authorId}")
    public ResponseEntity<?> getById(@PathVariable("authorId") Long id) {
        AuthorDTO dto = authorService.findAuthorById(id);
        return new ResponseEntity<AuthorDTO> (dto, HttpStatus.OK);
    }

}
