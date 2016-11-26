package com.jm.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * com.jm.domain.WordRepository Class will handle Word Objects.
 * Created by James Millner on 20/11/2016 at 23:10.
 */
public interface WordRepository extends JpaRepository<Word, Long> {

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Word c WHERE c.search = :word and c.type = :stringType")
    boolean doesWordExist(@Param("word") String word,
                          @Param("stringType") String type);

    Word findBySearch(String search);

    List<Word> findAllBySearch(String search);
}
