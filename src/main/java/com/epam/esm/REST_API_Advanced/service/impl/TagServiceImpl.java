package com.epam.esm.REST_API_Advanced.service.impl;

import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.entity.Tag;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.exception.Exceptions;
import com.epam.esm.REST_API_Advanced.hateoas.assembler.TagAssembler;
import com.epam.esm.REST_API_Advanced.mapper.TagMapper;
import com.epam.esm.REST_API_Advanced.repository.TagRepository;
import com.epam.esm.REST_API_Advanced.service.TagService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


@Service
@Transactional
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagAssembler tagAssembler;
    @Override
    public PagedModel<TagDto> getAll(int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size, Sort.by("name"));
        Page<Tag> tags = tagRepository.findAll(pageRequest);

        if (page > tags.getTotalPages()) {
            throw new ApiException(Exceptions.PAGE_DOESNT_EXIST);
        }

        return pagedResourcesAssembler.toModel(tags, tagAssembler);
    }

    @Override
    public TagDto getById(Long id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new ApiException(Exceptions.TAG_NOT_FOUND));
        return TagMapper.INSTANCE.toDto(tag);
    }

    @Override
    public TagDto create(TagDto tagDto) {
        Tag savedTag = tagRepository.save(TagMapper.INSTANCE.toEntity(tagDto));
        return TagMapper.INSTANCE.toDto(savedTag);
    }

    @Override
    public void deleteById(Long id) {
        if (tagRepository.findById(id).isPresent()) {
            tagRepository.deleteById(id);
        } else {
            throw new ApiException(Exceptions.TAG_NOT_FOUND);
        }
    }

    @Override
    public TagDto getMostWidelyUsedTag(Long idUser) {
        Tag tag = tagRepository.findById(tagRepository.getMostWildlyUsedTag(idUser)).
                orElseThrow(() -> new ApiException(Exceptions.TAG_NOT_FOUND));
        return TagMapper.INSTANCE.toDto(tag);
    }
}
