package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.entity.Tag;
import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.entity.GiftCertificate;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.repository.GiftCertificateRepository;
import com.epam.esm.REST_API_Advanced.repository.TagRepository;
import com.epam.esm.REST_API_Advanced.service.impl.GiftCertificateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @Mock
    private PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;
    private GiftCertificate giftCertificate;
    private GiftCertificateDto giftCertificateDto;
    private Tag tag;
    private final String NAME = "name";
    private final String DESCRIPTION = "description";

    @BeforeEach
    void setUp() {
        giftCertificate = new GiftCertificate(
                1L, NAME, DESCRIPTION, new BigDecimal(20), 1, LocalDateTime.now(),
                LocalDateTime.now(), new HashSet<>()
        );


        giftCertificateDto = GiftCertificateDto.builder().id(giftCertificate.getId())
                .name(giftCertificate.getName())
                .description(giftCertificate.getDescription())
                .duration(giftCertificate.getDuration())
                .price(giftCertificate.getPrice()).build();

        tag = new Tag();
        tag.setId(1L);
        tag.setName("name");
    }

    @Test
    void shouldGetAll() {
        PageRequest name = PageRequest.of(0, 10, Sort.by("name"));
        Page<GiftCertificate> page = new PageImpl<>(List.of(new GiftCertificate()));

        when(giftCertificateRepository.findAll(name)).thenReturn(page);

        giftCertificateService.getAll(0, 10);

        verify(giftCertificateRepository).findAll(name);
    }

    @Test
    void shouldGetById() {
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));


        GiftCertificateDto byId = giftCertificateService.getById(1L);

        verify(giftCertificateRepository).findById(1L);

        assertThat(byId).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getById(2L)).isInstanceOf(ApiException.class);
    }

    @Test
    void shouldGetByName() {

        when(giftCertificateRepository.findFirstByNameLike(NAME))
                .thenReturn(java.util.Optional.ofNullable(giftCertificate));

        GiftCertificateDto byName = giftCertificateService.getByName(NAME);

        verify(giftCertificateRepository).findFirstByNameLike(NAME);

        assertThat(byName).isNotNull();
        assertThatThrownBy(() -> giftCertificateService.getByName("")).isInstanceOf(ApiException.class);
    }

    @Test
    void shouldCreate() {
        when(giftCertificateRepository.save(any())).thenReturn(giftCertificate);
        when(giftCertificateRepository.existsByName(NAME)).thenReturn(false);
        when(tagRepository.save(tag)).thenReturn(tag);

        TagDto tagDto = new TagDto();
        tagDto.setName("name");
        tagDto.setId(1L);

        Set<TagDto> tags = new HashSet<>();
        tags.add(tagDto);
        giftCertificateDto.setTags(tags);
        giftCertificateService.create(giftCertificateDto);

        verify(giftCertificateRepository).existsByName(giftCertificateDto.getName());
        verify(giftCertificateRepository).save(any());
    }

    @Test
    void shouldUpdate() {
        when(giftCertificateRepository.save(any())).thenReturn(giftCertificate);
        when(giftCertificateRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(giftCertificate));


        giftCertificateService.update(giftCertificateDto, 1L);
        giftCertificateDto.setName("newName");
        giftCertificateService.update(giftCertificateDto, 1L);

        verify(giftCertificateRepository, times(2)).save(any());

        assertThatThrownBy(() -> giftCertificateService.update(giftCertificateDto, 2L)).isInstanceOf(ApiException.class);
    }

    @Test
    void shouldDeleteById() {
        when(giftCertificateRepository.findById(anyLong())).thenReturn(Optional.of(giftCertificate));

        giftCertificateService.deleteById(1L);
        giftCertificateService.deleteById(2L);

        verify(giftCertificateRepository, times(2)).deleteById(anyLong());
    }
}
