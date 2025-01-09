package com.users.service.impl;

import com.users.domain.Legume;
import com.users.repository.LegumeRepository;
import com.users.service.LegumeService;
import com.users.service.dto.LegumeDTO;
import com.users.service.mapper.LegumeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.users.domain.Legume}.
 */
@Service
@Transactional
public class LegumeServiceImpl implements LegumeService {

    private static final Logger LOG = LoggerFactory.getLogger(LegumeServiceImpl.class);

    private final LegumeRepository legumeRepository;

    private final LegumeMapper legumeMapper;

    public LegumeServiceImpl(LegumeRepository legumeRepository, LegumeMapper legumeMapper) {
        this.legumeRepository = legumeRepository;
        this.legumeMapper = legumeMapper;
    }

    @Override
    public LegumeDTO save(LegumeDTO legumeDTO) {
        LOG.debug("Request to save Legume : {}", legumeDTO);
        Legume legume = legumeMapper.toEntity(legumeDTO);
        legume = legumeRepository.save(legume);
        return legumeMapper.toDto(legume);
    }

    @Override
    public LegumeDTO update(LegumeDTO legumeDTO) {
        LOG.debug("Request to update Legume : {}", legumeDTO);
        Legume legume = legumeMapper.toEntity(legumeDTO);
        legume = legumeRepository.save(legume);
        return legumeMapper.toDto(legume);
    }

    @Override
    public Optional<LegumeDTO> partialUpdate(LegumeDTO legumeDTO) {
        LOG.debug("Request to partially update Legume : {}", legumeDTO);

        return legumeRepository
            .findById(legumeDTO.getId())
            .map(existingLegume -> {
                legumeMapper.partialUpdate(existingLegume, legumeDTO);

                return existingLegume;
            })
            .map(legumeRepository::save)
            .map(legumeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LegumeDTO> findAll() {
        LOG.debug("Request to get all Legumes");
        return legumeRepository.findAll().stream().map(legumeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LegumeDTO> findOne(Long id) {
        LOG.debug("Request to get Legume : {}", id);
        return legumeRepository.findById(id).map(legumeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Legume : {}", id);
        legumeRepository.deleteById(id);
    }
}
