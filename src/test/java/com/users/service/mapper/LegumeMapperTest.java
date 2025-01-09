package com.users.service.mapper;

import static com.users.domain.LegumeAsserts.*;
import static com.users.domain.LegumeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LegumeMapperTest {

    private LegumeMapper legumeMapper;

    @BeforeEach
    void setUp() {
        legumeMapper = new LegumeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getLegumeSample1();
        var actual = legumeMapper.toEntity(legumeMapper.toDto(expected));
        assertLegumeAllPropertiesEquals(expected, actual);
    }
}
