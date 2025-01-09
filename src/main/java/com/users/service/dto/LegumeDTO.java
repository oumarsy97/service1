package com.users.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.users.domain.Legume} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LegumeDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private Double price;

    @NotNull
    private Integer quantite;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LegumeDTO)) {
            return false;
        }

        LegumeDTO legumeDTO = (LegumeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, legumeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegumeDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", price=" + getPrice() +
            ", quantite=" + getQuantite() +
            "}";
    }
}
