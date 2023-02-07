package com.epam.esm.REST_API_Advanced.dto;

import com.epam.esm.REST_API_Advanced.entity.GiftCertificate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagDto extends RepresentationModel<TagDto> {
    private Long id;
    private String name;
    @JsonIgnore
    private Set<GiftCertificate> giftCertificates;
}