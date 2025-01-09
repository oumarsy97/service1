package com.users.domain;

import static com.users.domain.LegumeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.users.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LegumeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Legume.class);
        Legume legume1 = getLegumeSample1();
        Legume legume2 = new Legume();
        assertThat(legume1).isNotEqualTo(legume2);

        legume2.setId(legume1.getId());
        assertThat(legume1).isEqualTo(legume2);

        legume2 = getLegumeSample2();
        assertThat(legume1).isNotEqualTo(legume2);
    }
}
