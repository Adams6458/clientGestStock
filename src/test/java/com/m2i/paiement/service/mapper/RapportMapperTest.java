package com.m2i.paiement.service.mapper;

import static com.m2i.paiement.domain.RapportAsserts.*;
import static com.m2i.paiement.domain.RapportTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RapportMapperTest {

    private RapportMapper rapportMapper;

    @BeforeEach
    void setUp() {
        rapportMapper = new RapportMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRapportSample1();
        var actual = rapportMapper.toEntity(rapportMapper.toDto(expected));
        assertRapportAllPropertiesEquals(expected, actual);
    }
}
