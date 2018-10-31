package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.logos.domain.BookDTO;
import ua.logos.entity.BookEntity;
import ua.logos.exception.ResourceNotFoundException;
import ua.logos.repository.BookRepository;
import ua.logos.service.BookService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectMapperUtils bookMapper;

    @Override
    public void saveBook(BookDTO dto) {
        BookEntity entity = bookMapper.map(dto, BookEntity.class);
        bookRepository.save(entity);
    }

    @Override
    public BookDTO findBookById(Long id) {
        BookEntity entity = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record with id[" + id + "] not found")
        );

        BookDTO dto = bookMapper.map(entity,BookDTO.class);
        return dto;
    }

    @Override
    public List<BookDTO> findAllBooks() {
        List<BookEntity> entities = bookRepository.findAll();
        List<BookDTO> dtos = bookMapper.mapAll(entities, BookDTO.class);
        return dtos;
    }

    @Override
    public void addImageToBook(Long id, String fileName) {
        BookEntity book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record with id[" + id + "] not found")
        );
        book.setImage(fileName);
        bookRepository.save(book);
    }

}
