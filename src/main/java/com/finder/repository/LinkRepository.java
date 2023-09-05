package com.finder.repository;

import com.finder.domain.Link;
import com.finder.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<List<Link>> findAllByUser(Users user);

    @Query(value = "select * from link where user_id = :userId and linked_user_id = :linkedUserId", nativeQuery = true)
    Optional<Link> findByAllId(Long userId, Long linkedUserId);

    @Modifying
    @Query(value = "delete from link where user_id = :userId and linked_user_id = :linkedUserId", nativeQuery = true)
    void deleteByAllId(Long userId, Long linkedUserId);

    @Modifying
    @Query(value = "delete from link where user_id = :id or linked_user_id = :id", nativeQuery = true)
    void deleteByUserIdOrLinkedUserId(Long id);
}