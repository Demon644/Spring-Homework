package ua.logos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.logos.domain.AuthorDTO;
import ua.logos.entity.AuthorEntity;
import ua.logos.exception.ResourceNotFoundException;
import ua.logos.repository.AuthorRepository;
import ua.logos.service.AuthorService;
import ua.logos.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private ObjectMapperUtils authorMapper;

    @Override
    public void saveAuthor(AuthorDTO dto) {

        AuthorEntity entity = authorMapper.map(dto, AuthorEntity.class);
        authorRepository.save(entity);
    }

    @Override
    public AuthorDTO findAuthorById(Long id) {
        AuthorEntity entity = authorRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Record with id[" + id + "] not found")
        );

        AuthorDTO dto = authorMapper.map(entity, AuthorDTO.class);
        return dto;
    }

    @Override
    public List<AuthorDTO> findAllAuthors() {
        List<AuthorEntity> entities = authorRepository.findAll();
        List<AuthorDTO> dtos = authorMapper.mapAll(entities, AuthorDTO.class);
        return dtos;
    }

    @Override
    public List<AuthorDTO> findAuthorsByPage(Pageable pageable) {
        PageRequest request = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        List<AuthorEntity> models = authorRepository.findAll(request).getContent();
        return authorMapper.mapAll(models, AuthorDTO.class);
    }
}
