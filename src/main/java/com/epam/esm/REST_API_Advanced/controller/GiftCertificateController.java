package com.epam.esm.REST_API_Advanced.controller;

import com.epam.esm.REST_API_Advanced.dto.GiftCertificateDto;
import com.epam.esm.REST_API_Advanced.service.GiftCertificateService;
import jakarta.validation.Valid;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "gift_certificate")
public class GiftCertificateController {
    private final GiftCertificateService service;

    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping(params = {"page", "size"})
    public PagedModel<GiftCertificateDto> getAll(@RequestParam  int page,
                                                 @RequestParam  int size) {
        return service.getAll(page, size);
    }
    @GetMapping("/{id}")
    GiftCertificateDto getById(@PathVariable Long id){
        return service.getById(id);
    }
    @GetMapping(value = "/name/{name}")
    public GiftCertificateDto getByName(@PathVariable @Valid String name) {
        return service.getByName(name);
    }
    @PostMapping()
    public GiftCertificateDto create(@RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return service.create(giftCertificateDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable @Valid Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Gift Certificate by id " + id + " was deleted!");
    }
    @PutMapping("/{id}")
    public GiftCertificateDto update(@PathVariable @Valid Long id,
                                     @RequestBody @Valid GiftCertificateDto giftCertificateDto) {
        return service.update(giftCertificateDto, id);
    }

}