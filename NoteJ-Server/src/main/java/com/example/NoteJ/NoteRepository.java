package com.example.NoteJ;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


interface NoteRepository extends JpaRepository<Note, Long> {
    @Query(value = "select * from Note where date between ?1 and ?2 order by date", nativeQuery = true)
    List<Note> findBetweenDate(LocalDate startDate, LocalDate endDate);
}

