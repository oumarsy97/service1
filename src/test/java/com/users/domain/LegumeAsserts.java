package com.users.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class LegumeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLegumeAllPropertiesEquals(Legume expected, Legume actual) {
        assertLegumeAutoGeneratedPropertiesEquals(expected, actual);
        assertLegumeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLegumeAllUpdatablePropertiesEquals(Legume expected, Legume actual) {
        assertLegumeUpdatableFieldsEquals(expected, actual);
        assertLegumeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLegumeAutoGeneratedPropertiesEquals(Legume expected, Legume actual) {
        assertThat(expected)
            .as("Verify Legume auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLegumeUpdatableFieldsEquals(Legume expected, Legume actual) {
        assertThat(expected)
            .as("Verify Legume relevant properties")
            .satisfies(e -> assertThat(e.getLibelle()).as("check libelle").isEqualTo(actual.getLibelle()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").isEqualTo(actual.getPrice()))
            .satisfies(e -> assertThat(e.getQuantite()).as("check quantite").isEqualTo(actual.getQuantite()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertLegumeUpdatableRelationshipsEquals(Legume expected, Legume actual) {
        // empty method
    }
}
