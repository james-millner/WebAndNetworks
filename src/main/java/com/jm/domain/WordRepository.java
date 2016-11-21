package com.jm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * com.jm.domain.WordRepository Class will handle Word Objects.
 * Created by James Millner on 20/11/2016 at 23:10.
 */
public interface WordRepository extends JpaRepository<Word, Long> {

    Word findBySearch(String search);

    List<Word> findAllBySearch(String search);
}
