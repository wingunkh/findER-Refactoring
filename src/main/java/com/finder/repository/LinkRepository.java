package com.finder.repository;

import com.finder.domain.Link;
import com.finder.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<List<Link>> findAllByUser(User user);

    @Modifying
    @Query(value = "delete from link where user_id = :userId and linked_user_id = :linkedUserId", nativeQuery = true)
    void deleteById(Long userId, Long linkedUserId);
}