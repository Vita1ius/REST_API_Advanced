package com.epam.esm.REST_API_Advanced.controller;

import com.epam.esm.REST_API_Advanced.dto.TagDto;
import com.epam.esm.REST_API_Advanced.service.TagService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "tag")
public class TagController {
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }
    @GetMapping(params = {"page", "size"})
    public PagedModel<TagDto> getAll(@RequestParam @Valid int page,
                                     @RequestParam @Valid int size,
                                     UriComponentsBuilder uriComponentsBuilder,
                                     HttpServletResponse httpServletResponse) {
        return service.getAll(page, size);
    }
    @GetMapping("/{id}")
    TagDto getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Tag by id " + id + " was deleted!");
    }
    @PostMapping()
    public TagDto create(@RequestBody @Valid TagDto tagDto) {
        return service.create(tagDto);
    }
    @GetMapping("/usedTag/{id}")
    public TagDto getMostWidelyUsedTag(@PathVariable Long id) {
        return service.getMostWidelyUsedTag(id);
    }
}
