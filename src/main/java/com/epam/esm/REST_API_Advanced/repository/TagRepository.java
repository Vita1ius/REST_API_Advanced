package com.epam.esm.REST_API_Advanced.repository;

import com.epam.esm.REST_API_Advanced.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findFirstByName(String name);

    @Query(value = "select tag_id from orders inner join gift_certificate_tag on " +
            "gift_certificate_tag.gift_certificate_id = orders.certificate_id where user_id = ?" +
            " group by tag_id order by count(tag_id)desc,max(order_cost)desc limit 1",
            nativeQuery = true)
    Long getMostWildlyUsedTag(Long idUser);
}
