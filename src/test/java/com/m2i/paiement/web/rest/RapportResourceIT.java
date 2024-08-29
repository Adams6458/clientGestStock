package com.m2i.paiement.web.rest;

import static com.m2i.paiement.domain.RapportAsserts.*;
import static com.m2i.paiement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2i.paiement.IntegrationTest;
import com.m2i.paiement.domain.Rapport;
import com.m2i.paiement.repository.RapportRepository;
import com.m2i.paiement.service.dto.RapportDTO;
import com.m2i.paiement.service.mapper.RapportMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RapportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RapportResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_ERR = "AAAAAAAAAA";
    private static final String UPDATED_CODE_ERR = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_EXPL = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_EXPL = "BBBBBBBBBB";

    private static final String DEFAULT_ERRCODE = "AAAAAAAAAA";
    private static final String UPDATED_ERRCODE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/rapports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RapportRepository rapportRepository;

    @Autowired
    private RapportMapper rapportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRapportMockMvc;

    private Rapport rapport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .userID(DEFAULT_USER_ID)
            .message(DEFAULT_MESSAGE)
            .codeErr(DEFAULT_CODE_ERR)
            .textExpl(DEFAULT_TEXT_EXPL)
            .errcode(DEFAULT_ERRCODE)
            .transactionId(DEFAULT_TRANSACTION_ID);
        return rapport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rapport createUpdatedEntity(EntityManager em) {
        Rapport rapport = new Rapport()
            .userID(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .codeErr(UPDATED_CODE_ERR)
            .textExpl(UPDATED_TEXT_EXPL)
            .errcode(UPDATED_ERRCODE)
            .transactionId(UPDATED_TRANSACTION_ID);
        return rapport;
    }

    @BeforeEach
    public void initTest() {
        rapport = createEntity(em);
    }

    @Test
    @Transactional
    void createRapport() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);
        var returnedRapportDTO = om.readValue(
            restRapportMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RapportDTO.class
        );

        // Validate the Rapport in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRapport = rapportMapper.toEntity(returnedRapportDTO);
        assertRapportUpdatableFieldsEquals(returnedRapport, getPersistedRapport(returnedRapport));
    }

    @Test
    @Transactional
    void createRapportWithExistingId() throws Exception {
        // Create the Rapport with an existing ID
        rapport.setId(1L);
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRapportMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRapports() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get all the rapportList
        restRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].codeErr").value(hasItem(DEFAULT_CODE_ERR)))
            .andExpect(jsonPath("$.[*].textExpl").value(hasItem(DEFAULT_TEXT_EXPL)))
            .andExpect(jsonPath("$.[*].errcode").value(hasItem(DEFAULT_ERRCODE)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)));
    }

    @Test
    @Transactional
    void getRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        // Get the rapport
        restRapportMockMvc
            .perform(get(ENTITY_API_URL_ID, rapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rapport.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.codeErr").value(DEFAULT_CODE_ERR))
            .andExpect(jsonPath("$.textExpl").value(DEFAULT_TEXT_EXPL))
            .andExpect(jsonPath("$.errcode").value(DEFAULT_ERRCODE))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID));
    }

    @Test
    @Transactional
    void getNonExistingRapport() throws Exception {
        // Get the rapport
        restRapportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapport
        Rapport updatedRapport = rapportRepository.findById(rapport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRapport are not directly saved in db
        em.detach(updatedRapport);
        updatedRapport
            .userID(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .codeErr(UPDATED_CODE_ERR)
            .textExpl(UPDATED_TEXT_EXPL)
            .errcode(UPDATED_ERRCODE)
            .transactionId(UPDATED_TRANSACTION_ID);
        RapportDTO rapportDTO = rapportMapper.toDto(updatedRapport);

        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rapportDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRapportToMatchAllProperties(updatedRapport);
    }

    @Test
    @Transactional
    void putNonExistingRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rapportDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rapportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRapportWithPatch() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapport using partial update
        Rapport partialUpdatedRapport = new Rapport();
        partialUpdatedRapport.setId(rapport.getId());

        partialUpdatedRapport.errcode(UPDATED_ERRCODE);

        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRapport))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRapportUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedRapport, rapport), getPersistedRapport(rapport));
    }

    @Test
    @Transactional
    void fullUpdateRapportWithPatch() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rapport using partial update
        Rapport partialUpdatedRapport = new Rapport();
        partialUpdatedRapport.setId(rapport.getId());

        partialUpdatedRapport
            .userID(UPDATED_USER_ID)
            .message(UPDATED_MESSAGE)
            .codeErr(UPDATED_CODE_ERR)
            .textExpl(UPDATED_TEXT_EXPL)
            .errcode(UPDATED_ERRCODE)
            .transactionId(UPDATED_TRANSACTION_ID);

        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRapport.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRapport))
            )
            .andExpect(status().isOk());

        // Validate the Rapport in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRapportUpdatableFieldsEquals(partialUpdatedRapport, getPersistedRapport(partialUpdatedRapport));
    }

    @Test
    @Transactional
    void patchNonExistingRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rapportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRapport() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rapport.setId(longCount.incrementAndGet());

        // Create the Rapport
        RapportDTO rapportDTO = rapportMapper.toDto(rapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRapportMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rapportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rapport in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRapport() throws Exception {
        // Initialize the database
        rapportRepository.saveAndFlush(rapport);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rapport
        restRapportMockMvc
            .perform(delete(ENTITY_API_URL_ID, rapport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rapportRepository.count();
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

    protected Rapport getPersistedRapport(Rapport rapport) {
        return rapportRepository.findById(rapport.getId()).orElseThrow();
    }

    protected void assertPersistedRapportToMatchAllProperties(Rapport expectedRapport) {
        assertRapportAllPropertiesEquals(expectedRapport, getPersistedRapport(expectedRapport));
    }

    protected void assertPersistedRapportToMatchUpdatableProperties(Rapport expectedRapport) {
        assertRapportAllUpdatablePropertiesEquals(expectedRapport, getPersistedRapport(expectedRapport));
    }
}
