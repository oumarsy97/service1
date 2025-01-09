package com.users.service;

import com.users.service.dto.LegumeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.users.domain.Legume}.
 */
public interface LegumeService {
    /**
     * Save a legume.
     *
     * @param legumeDTO the entity to save.
     * @return the persisted entity.
     */
    LegumeDTO save(LegumeDTO legumeDTO);

    /**
     * Updates a legume.
     *
     * @param legumeDTO the entity to update.
     * @return the persisted entity.
     */
    LegumeDTO update(LegumeDTO legumeDTO);

    /**
     * Partially updates a legume.
     *
     * @param legumeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LegumeDTO> partialUpdate(LegumeDTO legumeDTO);

    /**
     * Get all the legumes.
     *
     * @return the list of entities.
     */
    List<LegumeDTO> findAll();

    /**
     * Get the "id" legume.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LegumeDTO> findOne(Long id);

    /**
     * Delete the "id" legume.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
