package com.users.service.impl;

import com.users.domain.Produit;
import com.users.repository.ProduitRepository;
import com.users.service.ProduitService;
import com.users.service.dto.ProduitDTO;
import com.users.service.mapper.ProduitMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.users.domain.Produit}.
 */
@Service
@Transactional
public class ProduitServiceImpl implements ProduitService {

    private static final Logger LOG = LoggerFactory.getLogger(ProduitServiceImpl.class);

    private final ProduitRepository produitRepository;

    private final ProduitMapper produitMapper;

    public ProduitServiceImpl(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    @Override
    public ProduitDTO save(ProduitDTO produitDTO) {
        LOG.debug("Request to save Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    @Override
    public ProduitDTO update(ProduitDTO produitDTO) {
        LOG.debug("Request to update Produit : {}", produitDTO);
        Produit produit = produitMapper.toEntity(produitDTO);
        produit = produitRepository.save(produit);
        return produitMapper.toDto(produit);
    }

    @Override
    public Optional<ProduitDTO> partialUpdate(ProduitDTO produitDTO) {
        LOG.debug("Request to partially update Produit : {}", produitDTO);

        return produitRepository
            .findById(produitDTO.getId())
            .map(existingProduit -> {
                produitMapper.partialUpdate(existingProduit, produitDTO);

                return existingProduit;
            })
            .map(produitRepository::save)
            .map(produitMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProduitDTO> findAll() {
        LOG.debug("Request to get all Produits");
        return produitRepository.findAll().stream().map(produitMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProduitDTO> findOne(Long id) {
        LOG.debug("Request to get Produit : {}", id);
        return produitRepository.findById(id).map(produitMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Produit : {}", id);
        produitRepository.deleteById(id);
    }
}
