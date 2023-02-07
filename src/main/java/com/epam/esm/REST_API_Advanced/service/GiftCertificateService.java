package com.epam.esm.REST_API_Advanced.service;

import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import org.springframework.hateoas.PagedModel;

public interface GiftCertificateService {
    /**
     * returns the GiftCertificate by id
     * @param id id of the requested entity
     * @return the requested entity
     */
    GiftCertificateDto getById(Long id);

    /**
     * <p>
     * returns the Page, comprised of Gift Certificates
     * </p>
     *
     * @param page page number
     * @param size page size
     * @return Page representation of Gift Certificates
     */
    PagedModel<GiftCertificateDto> getAll(int page, int size);


    /**
     * returns the GiftCertificate by name
     * @param name Name field of the requested entity
     * @return the requested entity
     */
    GiftCertificateDto getByName(String name);
    /**
     * deletes the specified GiftCertificate by id
     * @param id id of the GiftCertificate that needs to be deleted
     */
    void deleteById(Long id);
    GiftCertificateDto create(GiftCertificateDto giftCertificateDto);
    GiftCertificateDto update(GiftCertificateDto giftCertificateDto, Long id);
}

