package com.m2i.paiement.service;

import com.m2i.paiement.domain.Rapport;
import com.m2i.paiement.repository.RapportRepository;
import com.m2i.paiement.service.dto.RapportDTO;
import com.m2i.paiement.service.mapper.RapportMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.m2i.paiement.domain.Rapport}.
 */
@Service
@Transactional
public class RapportService {

    private final Logger log = LoggerFactory.getLogger(RapportService.class);

    private final RapportRepository rapportRepository;

    private final RapportMapper rapportMapper;

    public RapportService(RapportRepository rapportRepository, RapportMapper rapportMapper) {
        this.rapportRepository = rapportRepository;
        this.rapportMapper = rapportMapper;
    }

    /**
     * Save a rapport.
     *
     * @param rapportDTO the entity to save.
     * @return the persisted entity.
     */
    public RapportDTO save(RapportDTO rapportDTO) {
        log.debug("Request to save Rapport : {}", rapportDTO);
        Rapport rapport = rapportMapper.toEntity(rapportDTO);
        rapport = rapportRepository.save(rapport);
        return rapportMapper.toDto(rapport);
    }

    /**
     * Update a rapport.
     *
     * @param rapportDTO the entity to save.
     * @return the persisted entity.
     */
    public RapportDTO update(RapportDTO rapportDTO) {
        log.debug("Request to update Rapport : {}", rapportDTO);
        Rapport rapport = rapportMapper.toEntity(rapportDTO);
        rapport = rapportRepository.save(rapport);
        return rapportMapper.toDto(rapport);
    }

    /**
     * Partially update a rapport.
     *
     * @param rapportDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RapportDTO> partialUpdate(RapportDTO rapportDTO) {
        log.debug("Request to partially update Rapport : {}", rapportDTO);

        return rapportRepository
            .findById(rapportDTO.getId())
            .map(existingRapport -> {
                rapportMapper.partialUpdate(existingRapport, rapportDTO);

                return existingRapport;
            })
            .map(rapportRepository::save)
            .map(rapportMapper::toDto);
    }

    /**
     * Get all the rapports.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<RapportDTO> findAll() {
        log.debug("Request to get all Rapports");
        return rapportRepository.findAll().stream().map(rapportMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one rapport by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RapportDTO> findOne(Long id) {
        log.debug("Request to get Rapport : {}", id);
        return rapportRepository.findById(id).map(rapportMapper::toDto);
    }

    /**
     * Delete the rapport by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Rapport : {}", id);
        rapportRepository.deleteById(id);
    }
}
