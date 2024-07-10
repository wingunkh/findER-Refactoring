package com.finder.repository;

import com.finder.domain.ER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ERRepository extends JpaRepository<ER, String> {}
