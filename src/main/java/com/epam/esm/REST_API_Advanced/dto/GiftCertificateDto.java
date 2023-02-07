package com.epam.esm.REST_API_Advanced.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    @NotBlank(message = "You should provide a name for a new gift certificate")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private BigDecimal price;

    @Min(value = 0, message = "Duration should not be less than 0")
    @JsonProperty("duration")
    private Integer duration;

    @JsonProperty("create date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @JsonProperty("last update date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdateDate;
    @JsonProperty("tags")
    private Set<TagDto> tags;
//    public void setTags(Set<TagDto> tags) {
//        if (tags == null) {
//            return;
//        }
//        this.tags = tags;
//    }

}
