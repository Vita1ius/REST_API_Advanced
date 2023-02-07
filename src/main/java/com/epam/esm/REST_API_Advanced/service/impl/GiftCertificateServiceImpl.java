package com.epam.esm.REST_API_Advanced.service.impl;

import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.entity.GiftCertificate;
import com.epam.esm.REST_API_Advanced.entity.Tag;
import com.epam.esm.REST_API_Advanced.hateoas.assembler.GiftCertificateAssembler;
import com.epam.esm.REST_API_Advanced.mapper.GiftCertificateMapper;
import com.epam.esm.REST_API_Advanced.repository.GiftCertificateRepository;
import com.epam.esm.REST_API_Advanced.repository.TagRepository;
import com.epam.esm.REST_API_Advanced.service.GiftCertificateService;
import com.epam.esm.REST_API_Advanced.exception.ApiException;
import com.epam.esm.REST_API_Advanced.exception.Exceptions;
import com.epam.esm.REST_API_Advanced.mapper.TagMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.epam.esm.REST_API_Advanced.exception.Exceptions.*;


@Service
@Transactional
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository giftCertificateRepository;
    private final TagRepository tagRepository;
    private final GiftCertificateAssembler giftCertificateAssembler;
    private final PagedResourcesAssembler<GiftCertificate> pagedResourcesAssembler;



    @Override
    public GiftCertificateDto getById(Long id) {
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(Exceptions.CERTIFICATE_NOT_FOUND));
        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificate);
    }

    @Override
    public PagedModel<GiftCertificateDto> getAll(int page, int size) {
        Page<GiftCertificate> giftCertificates = giftCertificateRepository
                .findAll(PageRequest.of(page, size, Sort.by("name")));
        if (page > giftCertificates.getTotalPages()) {
            throw new ApiException(PAGE_DOESNT_EXIST);
        }

        return pagedResourcesAssembler.toModel(giftCertificates, giftCertificateAssembler);
    }

    @Override
    public GiftCertificateDto getByName(String name) {
        GiftCertificate giftCertificate = giftCertificateRepository.findFirstByNameLike(name)
                .orElseThrow(() ->
                        new ApiException(Exceptions.CERTIFICATE_NOT_FOUND));
        return toDtoWithTags(GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificate),
                giftCertificate.getId());
    }

    @Override
    public void deleteById(Long id) {
        if (giftCertificateRepository.findById(id).isPresent()){
            giftCertificateRepository.deleteById(id);
        }else throw new ApiException(Exceptions.CERTIFICATE_NOT_FOUND);
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateRepository.existsByName(giftCertificateDto.getName())){
            throw new ApiException(CERTIFICATE_ALREADY_EXIST);
        }
        manageTags(giftCertificateDto);
        GiftCertificate newCertificate =
                giftCertificateRepository.save(GiftCertificateMapper.INSTANCE.toEntity(giftCertificateDto));
        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(newCertificate);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id) {

        manageTags(giftCertificateDto);
        GiftCertificate giftCertificate = giftCertificateRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(CERTIFICATE_ALREADY_EXIST));

        giftCertificate = updateTheGIftCertificate(giftCertificate, giftCertificateDto);

        return GiftCertificateMapper.INSTANCE.certificateToCertificateDto(giftCertificateRepository.
                save(giftCertificate));
    }

    public Set<TagDto> getTags(Long id) {
        List<Long> tagIds = giftCertificateRepository.getTagsId(id);
        Set<TagDto> tags =new HashSet<>();
        for (Long num: tagIds){
            TagDto optional = TagMapper.INSTANCE.toDto(tagRepository.findById(num).get());
            tags.add(optional);
        }
        return tags;
    }
    public GiftCertificateDto toDtoWithTags(GiftCertificateDto giftCertificateDto,
                                            Long certificateId) {


        Set<TagDto> tags = getTags(certificateId);
        if (!(tags.size() == 0)) {
            giftCertificateDto.setTags(tags);
        }
        return giftCertificateDto;
    }
    private void manageTags(GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getTags() != null) {
            for (TagDto tag : giftCertificateDto.getTags()) {
                Optional<Tag> tagOptional = tagRepository.findFirstByName(tag.getName());
                if (tagOptional.isPresent()) {
                    tag.setId(tagOptional.get().getId());
                } else {
                    Tag savedTag = tagRepository.save(TagMapper.INSTANCE.toEntity(tag));
                    tag.setId(savedTag.getId());
                }
            }
        }
    }
    public GiftCertificate updateTheGIftCertificate(GiftCertificate giftCertificate,
                                                    GiftCertificateDto giftCertificateDto) {
        if (giftCertificateDto.getName() != null) giftCertificate.setName(giftCertificateDto.getName());
        if (giftCertificateDto.getDescription() != null)
            giftCertificate.setDescription(giftCertificateDto.getDescription());
        if (giftCertificateDto.getDuration() != null) giftCertificate.setDuration(giftCertificateDto.getDuration());
        if (giftCertificateDto.getPrice() != null) giftCertificate.setPrice(giftCertificateDto.getPrice());
        if (giftCertificateDto.getTags() != null) giftCertificate.setTags(TagMapper.INSTANCE
                .mapToEntity(giftCertificateDto.getTags()));

        return giftCertificate;
    }

}