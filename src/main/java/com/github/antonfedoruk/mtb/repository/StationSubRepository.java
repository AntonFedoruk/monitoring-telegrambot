package com.github.antonfedoruk.mtb.repository;

import com.github.antonfedoruk.mtb.repository.entity.StationSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for handling with {@link StationSub} entity.
 */
@Repository
public interface StationSubRepository extends JpaRepository<StationSub, Integer> {
}
