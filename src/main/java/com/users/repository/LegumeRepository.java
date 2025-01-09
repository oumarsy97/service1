package com.users.repository;

import com.users.domain.Legume;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Legume entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LegumeRepository extends JpaRepository<Legume, Long> {}
