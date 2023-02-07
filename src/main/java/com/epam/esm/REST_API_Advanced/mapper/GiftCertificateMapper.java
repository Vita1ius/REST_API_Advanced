package com.epam.esm.REST_API_Advanced.mapper;

import com.epam.esm.REST_API_Advanced.entity.GiftCertificate;
import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {
    GiftCertificateMapper INSTANCE = Mappers.getMapper(GiftCertificateMapper.class);
    GiftCertificateDto certificateToCertificateDto(GiftCertificate certificate);

    GiftCertificate toEntity(GiftCertificateDto giftCertificateDto);
}
