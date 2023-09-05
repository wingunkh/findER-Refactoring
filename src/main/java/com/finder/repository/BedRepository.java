package com.finder.repository;

import com.finder.domain.Bed;
import com.finder.idClass.BedId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BedRepository extends JpaRepository<Bed, BedId> {

    @Query("SELECT b FROM Bed b WHERE b.name = :name " +
            "AND EXTRACT(YEAR FROM b.localDateTime) = EXTRACT(YEAR FROM :time) " +
            "AND EXTRACT(MONTH FROM b.localDateTime) = EXTRACT(MONTH FROM :time) " +
            "AND EXTRACT(DAY FROM b.localDateTime) = EXTRACT(DAY FROM :time) " +
            "AND EXTRACT(HOUR FROM b.localDateTime) = EXTRACT(HOUR FROM :time) " +
            "AND EXTRACT(MINUTE FROM b.localDateTime) = EXTRACT(MINUTE FROM :time)")
    Bed findByNameAndTime(String name, LocalDateTime time);

    @Query("select b " +
            "from Bed b " +
            "where b.name = :name and " +
            "b.localDateTime between :beforeTime and :currentTime")
    List<Bed> findByRecent(String name, LocalDateTime beforeTime, LocalDateTime currentTime);

//    @Query("select b " +
//            "from Bed b " +
//            "where b.name = :name and " +
//            "b.time between :beforeTime and :currentTime " +
//            "and FUNCTION('MINUTE', b.time) IN (0, 15, 40, 45)")
    @Query(value = "SELECT * " +
        "FROM bed " +
        "WHERE name = :name " +
        "AND localDateTime BETWEEN :beforeTime AND :currentTime " +
        "AND MOD(TIMESTAMPDIFF(MINUTE, :beforeTime, time), 15) = 0", nativeQuery = true)
    List<Bed> findByNewRecent(String name, LocalDateTime beforeTime, LocalDateTime currentTime);
}
