package com.users.web.rest;

import com.users.repository.LegumeRepository;
import com.users.service.LegumeService;
import com.users.service.dto.LegumeDTO;
import com.users.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.users.domain.Legume}.
 */
@RestController
@RequestMapping("/api/legumes")
public class LegumeResource {

    private static final Logger LOG = LoggerFactory.getLogger(LegumeResource.class);

    private static final String ENTITY_NAME = "service1Legume";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegumeService legumeService;

    private final LegumeRepository legumeRepository;

    public LegumeResource(LegumeService legumeService, LegumeRepository legumeRepository) {
        this.legumeService = legumeService;
        this.legumeRepository = legumeRepository;
    }

    /**
     * {@code POST  /legumes} : Create a new legume.
     *
     * @param legumeDTO the legumeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new legumeDTO, or with status {@code 400 (Bad Request)} if the legume has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LegumeDTO> createLegume(@Valid @RequestBody LegumeDTO legumeDTO) throws URISyntaxException {
        LOG.debug("REST request to save Legume : {}", legumeDTO);
        if (legumeDTO.getId() != null) {
            throw new BadRequestAlertException("A new legume cannot already have an ID", ENTITY_NAME, "idexists");
        }
        legumeDTO = legumeService.save(legumeDTO);
        return ResponseEntity.created(new URI("/api/legumes/" + legumeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, legumeDTO.getId().toString()))
            .body(legumeDTO);
    }

    /**
     * {@code PUT  /legumes/:id} : Updates an existing legume.
     *
     * @param id the id of the legumeDTO to save.
     * @param legumeDTO the legumeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legumeDTO,
     * or with status {@code 400 (Bad Request)} if the legumeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the legumeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LegumeDTO> updateLegume(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LegumeDTO legumeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Legume : {}, {}", id, legumeDTO);
        if (legumeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legumeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legumeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        legumeDTO = legumeService.update(legumeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legumeDTO.getId().toString()))
            .body(legumeDTO);
    }

    /**
     * {@code PATCH  /legumes/:id} : Partial updates given fields of an existing legume, field will ignore if it is null
     *
     * @param id the id of the legumeDTO to save.
     * @param legumeDTO the legumeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated legumeDTO,
     * or with status {@code 400 (Bad Request)} if the legumeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the legumeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the legumeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LegumeDTO> partialUpdateLegume(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LegumeDTO legumeDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Legume partially : {}, {}", id, legumeDTO);
        if (legumeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, legumeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!legumeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LegumeDTO> result = legumeService.partialUpdate(legumeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, legumeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /legumes} : get all the legumes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of legumes in body.
     */
    @GetMapping("")
    public List<LegumeDTO> getAllLegumes() {
        LOG.debug("REST request to get all Legumes");
        return legumeService.findAll();
    }

    /**
     * {@code GET  /legumes/:id} : get the "id" legume.
     *
     * @param id the id of the legumeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the legumeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LegumeDTO> getLegume(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Legume : {}", id);
        Optional<LegumeDTO> legumeDTO = legumeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(legumeDTO);
    }

    /**
     * {@code DELETE  /legumes/:id} : delete the "id" legume.
     *
     * @param id the id of the legumeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLegume(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Legume : {}", id);
        legumeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
