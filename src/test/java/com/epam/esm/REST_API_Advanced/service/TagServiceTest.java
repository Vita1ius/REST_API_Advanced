package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.entity.Tag;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.repository.TagRepository;
import com.epam.esm.REST_API_Advanced.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository tagRepository;
    @Mock
    private PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    @InjectMocks
    private TagServiceImpl tagService;
    private Tag tag;
    private TagDto tagDto;

    @BeforeEach
    void setUp() {
        tag = new Tag();
        tag.setName("tag1");

        tagDto = new TagDto();
        tagDto.setName("tag1");

    }
    @Test
    void testFindAllTagsShouldReturnListOfAllTags() {
        Page<Tag> tags = new PageImpl<>(List.of(new Tag()));

        when(tagRepository.findAll((Pageable) any())).thenReturn(tags);
        when(pagedResourcesAssembler.toModel(any(),
                (RepresentationModelAssembler<Tag, RepresentationModel<?>>) any()))
                .thenReturn(PagedModel.empty());

        tagService.getAll(0, 10);

        verify(tagRepository).findAll(PageRequest.of(0, 10, Sort.by("name")));
    }

    @Test
    void testFindByIdShouldReturnFoundTagWithGivenId() {
        final long ID_ONE = 1;
        when(tagRepository.findById(ID_ONE)).thenReturn(Optional.of(tag));
        TagDto actual = tagService.getById(ID_ONE);
        assertEquals(tagDto, actual);
    }

    @Test
    void testFindByIdShouldThrowExceptionIfTagNotFound() {
        when(tagRepository.findById(0L)).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> tagService.getById(0L));
    }

    @Test
    void findByid(){
        when(tagRepository.findById(1L)).thenReturn(Optional.ofNullable(tag));
        TagDto savedTag = tagService.getById(1L);
        Assertions.assertNotNull(savedTag);
    }

    @Test
    void shouldAddTag(){
        when(tagRepository.save(tag)).thenReturn(tag);
        Assertions.assertEquals(tagDto, tagService.create(tagDto));
        verify(tagRepository).save(tag);
    }

    @Test
    void testDeleteByIdShouldDeleteTagWithGivenId() {
        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(tag));

        tagService.deleteById(1L);
        tagService.deleteById(2L);

        verify(tagRepository, times(2)).deleteById(anyLong());
    }
    @Test
    void getMostWidelyUsedTag(){
        when(tagRepository.getMostWildlyUsedTag(5L)).thenReturn(anyLong());
        assertThatThrownBy(() -> tagService.getMostWidelyUsedTag(5L)).isInstanceOf(ApiException.class);
        Assertions.assertEquals(0,tagRepository.getMostWildlyUsedTag(5L));
        verify(tagRepository,times(2)).getMostWildlyUsedTag(5L);
    }
}