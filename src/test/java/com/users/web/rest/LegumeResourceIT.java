package com.users.web.rest;

import static com.users.domain.LegumeAsserts.*;
import static com.users.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.IntegrationTest;
import com.users.domain.Legume;
import com.users.repository.LegumeRepository;
import com.users.service.dto.LegumeDTO;
import com.users.service.mapper.LegumeMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LegumeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LegumeResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_QUANTITE = 1;
    private static final Integer UPDATED_QUANTITE = 2;

    private static final String ENTITY_API_URL = "/api/legumes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LegumeRepository legumeRepository;

    @Autowired
    private LegumeMapper legumeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLegumeMockMvc;

    private Legume legume;

    private Legume insertedLegume;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Legume createEntity() {
        return new Legume().libelle(DEFAULT_LIBELLE).price(DEFAULT_PRICE).quantite(DEFAULT_QUANTITE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Legume createUpdatedEntity() {
        return new Legume().libelle(UPDATED_LIBELLE).price(UPDATED_PRICE).quantite(UPDATED_QUANTITE);
    }

    @BeforeEach
    public void initTest() {
        legume = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedLegume != null) {
            legumeRepository.delete(insertedLegume);
            insertedLegume = null;
        }
    }

    @Test
    @Transactional
    void createLegume() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);
        var returnedLegumeDTO = om.readValue(
            restLegumeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            LegumeDTO.class
        );

        // Validate the Legume in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedLegume = legumeMapper.toEntity(returnedLegumeDTO);
        assertLegumeUpdatableFieldsEquals(returnedLegume, getPersistedLegume(returnedLegume));

        insertedLegume = returnedLegume;
    }

    @Test
    @Transactional
    void createLegumeWithExistingId() throws Exception {
        // Create the Legume with an existing ID
        legume.setId(1L);
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegumeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLibelleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        legume.setLibelle(null);

        // Create the Legume, which fails.
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        restLegumeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        legume.setPrice(null);

        // Create the Legume, which fails.
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        restLegumeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantiteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        legume.setQuantite(null);

        // Create the Legume, which fails.
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        restLegumeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLegumes() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        // Get all the legumeList
        restLegumeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legume.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE)));
    }

    @Test
    @Transactional
    void getLegume() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        // Get the legume
        restLegumeMockMvc
            .perform(get(ENTITY_API_URL_ID, legume.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(legume.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE));
    }

    @Test
    @Transactional
    void getNonExistingLegume() throws Exception {
        // Get the legume
        restLegumeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLegume() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legume
        Legume updatedLegume = legumeRepository.findById(legume.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedLegume are not directly saved in db
        em.detach(updatedLegume);
        updatedLegume.libelle(UPDATED_LIBELLE).price(UPDATED_PRICE).quantite(UPDATED_QUANTITE);
        LegumeDTO legumeDTO = legumeMapper.toDto(updatedLegume);

        restLegumeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, legumeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedLegumeToMatchAllProperties(updatedLegume);
    }

    @Test
    @Transactional
    void putNonExistingLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, legumeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(legumeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLegumeWithPatch() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legume using partial update
        Legume partialUpdatedLegume = new Legume();
        partialUpdatedLegume.setId(legume.getId());

        partialUpdatedLegume.price(UPDATED_PRICE);

        restLegumeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegume.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLegume))
            )
            .andExpect(status().isOk());

        // Validate the Legume in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLegumeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedLegume, legume), getPersistedLegume(legume));
    }

    @Test
    @Transactional
    void fullUpdateLegumeWithPatch() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the legume using partial update
        Legume partialUpdatedLegume = new Legume();
        partialUpdatedLegume.setId(legume.getId());

        partialUpdatedLegume.libelle(UPDATED_LIBELLE).price(UPDATED_PRICE).quantite(UPDATED_QUANTITE);

        restLegumeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLegume.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedLegume))
            )
            .andExpect(status().isOk());

        // Validate the Legume in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertLegumeUpdatableFieldsEquals(partialUpdatedLegume, getPersistedLegume(partialUpdatedLegume));
    }

    @Test
    @Transactional
    void patchNonExistingLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, legumeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(legumeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(legumeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLegume() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        legume.setId(longCount.incrementAndGet());

        // Create the Legume
        LegumeDTO legumeDTO = legumeMapper.toDto(legume);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLegumeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(legumeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Legume in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLegume() throws Exception {
        // Initialize the database
        insertedLegume = legumeRepository.saveAndFlush(legume);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the legume
        restLegumeMockMvc
            .perform(delete(ENTITY_API_URL_ID, legume.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return legumeRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Legume getPersistedLegume(Legume legume) {
        return legumeRepository.findById(legume.getId()).orElseThrow();
    }

    protected void assertPersistedLegumeToMatchAllProperties(Legume expectedLegume) {
        assertLegumeAllPropertiesEquals(expectedLegume, getPersistedLegume(expectedLegume));
    }

    protected void assertPersistedLegumeToMatchUpdatableProperties(Legume expectedLegume) {
        assertLegumeAllUpdatablePropertiesEquals(expectedLegume, getPersistedLegume(expectedLegume));
    }
}
