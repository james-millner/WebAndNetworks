package com.jm.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * com.jm.domain.MeaningRepository Class will handle Meaning Objects.
 * Created by James Millner on 20/11/2016 at 23:10.
 */
public interface MeaningRepository extends JpaRepository<Meaning, Long> {
}
