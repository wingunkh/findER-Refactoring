package com.finder.repository;

import com.finder.domain.Link;
import com.finder.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
    Optional<List<Link>> findAllByUser(User user);
}