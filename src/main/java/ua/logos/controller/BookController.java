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
import ua.logos.domain.BookDTO;
import ua.logos.domain.ErrorDTO;
import ua.logos.service.BookService;
import ua.logos.service.FileStorageService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<BookDTO> dtos = bookService.findAllBooks();
        return new ResponseEntity<List<BookDTO>>(dtos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookDTO dto, BindingResult br) {

        if (br.hasErrors()) {
            System.out.println("Validation error");
            String errMsg = br.getFieldErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .findFirst().get().toString();

            ErrorDTO error = new ErrorDTO(errMsg);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        bookService.saveBook(dto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @GetMapping("{bookId}")
    public ResponseEntity<?> getById(@PathVariable("bookId") Long id) {
        BookDTO dto = bookService.findBookById(id);
        return new ResponseEntity<BookDTO> (dto, HttpStatus.OK);
    }

    @PostMapping("{bookId}/image")
    public ResponseEntity<Void> uploadImage(
            @PathVariable("bookId") Long id,
            @RequestParam("file") MultipartFile file) {

        System.out.println(file.getOriginalFilename());
        String fileName = fileStorageService.storeFile(file);
        bookService.addImageToBook(id, fileName);
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    @GetMapping("image/{fileName}")
    public ResponseEntity<Resource> getFile(
            @PathVariable("fileName") String fileName,
            HttpServletRequest request) {

        Resource resource = fileStorageService.loadFile(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline: filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
