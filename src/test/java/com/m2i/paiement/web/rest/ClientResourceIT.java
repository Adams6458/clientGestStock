package com.m2i.paiement.web.rest;

import static com.m2i.paiement.domain.ClientAsserts.*;
import static com.m2i.paiement.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.m2i.paiement.IntegrationTest;
import com.m2i.paiement.domain.Client;
import com.m2i.paiement.repository.ClientRepository;
import com.m2i.paiement.service.dto.ClientDTO;
import com.m2i.paiement.service.mapper.ClientMapper;
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
 * Integration tests for the {@link ClientResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClientResourceIT {

    private static final String DEFAULT_CODE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_SEXE = "AAAAAAAAAA";
    private static final String UPDATED_SEXE = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_PIECE = "AAAAAAAAAA";
    private static final String UPDATED_PIECE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTORITE_DELIVRE = "AAAAAAAAAA";
    private static final String UPDATED_AUTORITE_DELIVRE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_CLIENT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/clients";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClientMockMvc;

    private Client client;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createEntity(EntityManager em) {
        Client client = new Client()
            .codeAgence(DEFAULT_CODE_AGENCE)
            .telephone(DEFAULT_TELEPHONE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .sexe(DEFAULT_SEXE)
            .titre(DEFAULT_TITRE)
            .piece(DEFAULT_PIECE)
            .reference(DEFAULT_REFERENCE)
            .autoriteDelivre(DEFAULT_AUTORITE_DELIVRE)
            .typeClient(DEFAULT_TYPE_CLIENT);
        return client;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Client createUpdatedEntity(EntityManager em) {
        Client client = new Client()
            .codeAgence(UPDATED_CODE_AGENCE)
            .telephone(UPDATED_TELEPHONE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .sexe(UPDATED_SEXE)
            .titre(UPDATED_TITRE)
            .piece(UPDATED_PIECE)
            .reference(UPDATED_REFERENCE)
            .autoriteDelivre(UPDATED_AUTORITE_DELIVRE)
            .typeClient(UPDATED_TYPE_CLIENT);
        return client;
    }

    @BeforeEach
    public void initTest() {
        client = createEntity(em);
    }

    @Test
    @Transactional
    void createClient() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);
        var returnedClientDTO = om.readValue(
            restClientMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ClientDTO.class
        );

        // Validate the Client in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedClient = clientMapper.toEntity(returnedClientDTO);
        assertClientUpdatableFieldsEquals(returnedClient, getPersistedClient(returnedClient));
    }

    @Test
    @Transactional
    void createClientWithExistingId() throws Exception {
        // Create the Client with an existing ID
        client.setId(1L);
        ClientDTO clientDTO = clientMapper.toDto(client);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClients() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get all the clientList
        restClientMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(client.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeAgence").value(hasItem(DEFAULT_CODE_AGENCE)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].piece").value(hasItem(DEFAULT_PIECE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].autoriteDelivre").value(hasItem(DEFAULT_AUTORITE_DELIVRE)))
            .andExpect(jsonPath("$.[*].typeClient").value(hasItem(DEFAULT_TYPE_CLIENT)));
    }

    @Test
    @Transactional
    void getClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        // Get the client
        restClientMockMvc
            .perform(get(ENTITY_API_URL_ID, client.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(client.getId().intValue()))
            .andExpect(jsonPath("$.codeAgence").value(DEFAULT_CODE_AGENCE))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.piece").value(DEFAULT_PIECE))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.autoriteDelivre").value(DEFAULT_AUTORITE_DELIVRE))
            .andExpect(jsonPath("$.typeClient").value(DEFAULT_TYPE_CLIENT));
    }

    @Test
    @Transactional
    void getNonExistingClient() throws Exception {
        // Get the client
        restClientMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the client
        Client updatedClient = clientRepository.findById(client.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClient are not directly saved in db
        em.detach(updatedClient);
        updatedClient
            .codeAgence(UPDATED_CODE_AGENCE)
            .telephone(UPDATED_TELEPHONE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .sexe(UPDATED_SEXE)
            .titre(UPDATED_TITRE)
            .piece(UPDATED_PIECE)
            .reference(UPDATED_REFERENCE)
            .autoriteDelivre(UPDATED_AUTORITE_DELIVRE)
            .typeClient(UPDATED_TYPE_CLIENT);
        ClientDTO clientDTO = clientMapper.toDto(updatedClient);

        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientDTO))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedClientToMatchAllProperties(updatedClient);
    }

    @Test
    @Transactional
    void putNonExistingClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clientDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(clientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .codeAgence(UPDATED_CODE_AGENCE)
            .telephone(UPDATED_TELEPHONE)
            .prenom(UPDATED_PRENOM)
            .reference(UPDATED_REFERENCE);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClientUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedClient, client), getPersistedClient(client));
    }

    @Test
    @Transactional
    void fullUpdateClientWithPatch() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the client using partial update
        Client partialUpdatedClient = new Client();
        partialUpdatedClient.setId(client.getId());

        partialUpdatedClient
            .codeAgence(UPDATED_CODE_AGENCE)
            .telephone(UPDATED_TELEPHONE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .sexe(UPDATED_SEXE)
            .titre(UPDATED_TITRE)
            .piece(UPDATED_PIECE)
            .reference(UPDATED_REFERENCE)
            .autoriteDelivre(UPDATED_AUTORITE_DELIVRE)
            .typeClient(UPDATED_TYPE_CLIENT);

        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClient.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedClient))
            )
            .andExpect(status().isOk());

        // Validate the Client in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertClientUpdatableFieldsEquals(partialUpdatedClient, getPersistedClient(partialUpdatedClient));
    }

    @Test
    @Transactional
    void patchNonExistingClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clientDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(clientDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClient() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        client.setId(longCount.incrementAndGet());

        // Create the Client
        ClientDTO clientDTO = clientMapper.toDto(client);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClientMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(clientDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Client in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClient() throws Exception {
        // Initialize the database
        clientRepository.saveAndFlush(client);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the client
        restClientMockMvc
            .perform(delete(ENTITY_API_URL_ID, client.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return clientRepository.count();
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

    protected Client getPersistedClient(Client client) {
        return clientRepository.findById(client.getId()).orElseThrow();
    }

    protected void assertPersistedClientToMatchAllProperties(Client expectedClient) {
        assertClientAllPropertiesEquals(expectedClient, getPersistedClient(expectedClient));
    }

    protected void assertPersistedClientToMatchUpdatableProperties(Client expectedClient) {
        assertClientAllUpdatablePropertiesEquals(expectedClient, getPersistedClient(expectedClient));
    }
}
