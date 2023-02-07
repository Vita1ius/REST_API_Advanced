package com.epam.esm.REST_API_Advanced.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto extends RepresentationModel<UserDto> {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    @NotBlank(message = "Provide a name for User!")
    private String name;

    @JsonProperty("ordersList")
    private Set<OrderDto> orders;
}