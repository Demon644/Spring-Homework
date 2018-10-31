package ua.logos.service;

import ua.logos.domain.BookDTO;

import java.util.List;

public interface BookService {

    void saveBook(BookDTO dto);

    BookDTO findBookById(Long id);

    List<BookDTO> findAllBooks();

    void addImageToBook (Long id, String fileName);

}
