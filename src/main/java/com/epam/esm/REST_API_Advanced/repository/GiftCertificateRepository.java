package com.epam.esm.REST_API_Advanced.repository;


import com.epam.esm.REST_API_Advanced.entity.GiftCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        JpaSpecificationExecutor<GiftCertificate> {
    Optional<GiftCertificate> findFirstByNameLike(String name);
    boolean existsByName(String name);
    @Query(value = "SELECT tag_id FROM gift_certificate_tag WHERE gift_certificate_id=?",
            nativeQuery = true)
    List<Long> getTagsId(Long id);


}
