package com.users.service.mapper;

import static com.users.domain.ProduitAsserts.*;
import static com.users.domain.ProduitTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProduitMapperTest {

    private ProduitMapper produitMapper;

    @BeforeEach
    void setUp() {
        produitMapper = new ProduitMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getProduitSample1();
        var actual = produitMapper.toEntity(produitMapper.toDto(expected));
        assertProduitAllPropertiesEquals(expected, actual);
    }
}
