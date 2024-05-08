package com.finder.repository;

import com.finder.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LinkRepository extends JpaRepository<Link, Long> {
    List<Link> findAllByAccount1PhoneNumber(String phoneNumber);

    Link findByAccount1PhoneNumberAndAccount2PhoneNumber(String phoneNumber1, String phoneNumber2);

    void deleteByAccount1PhoneNumberAndAccount2PhoneNumber(String phoneNumber1, String phoneNumber2);
}
