package com.users.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.users.domain.Produit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProduitDTO implements Serializable {

    private Long id;

    @NotNull
    private String libelle;

    @NotNull
    private Double price;

    @NotNull
    private Integer quatite;

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

    public Integer getQuatite() {
        return quatite;
    }

    public void setQuatite(Integer quatite) {
        this.quatite = quatite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProduitDTO)) {
            return false;
        }

        ProduitDTO produitDTO = (ProduitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, produitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProduitDTO{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", price=" + getPrice() +
            ", quatite=" + getQuatite() +
            "}";
    }
}
