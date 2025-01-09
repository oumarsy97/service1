package com.users.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.users.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LegumeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LegumeDTO.class);
        LegumeDTO legumeDTO1 = new LegumeDTO();
        legumeDTO1.setId(1L);
        LegumeDTO legumeDTO2 = new LegumeDTO();
        assertThat(legumeDTO1).isNotEqualTo(legumeDTO2);
        legumeDTO2.setId(legumeDTO1.getId());
        assertThat(legumeDTO1).isEqualTo(legumeDTO2);
        legumeDTO2.setId(2L);
        assertThat(legumeDTO1).isNotEqualTo(legumeDTO2);
        legumeDTO1.setId(null);
        assertThat(legumeDTO1).isNotEqualTo(legumeDTO2);
    }
}
