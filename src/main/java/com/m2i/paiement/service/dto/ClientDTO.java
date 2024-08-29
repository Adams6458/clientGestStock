package com.m2i.paiement.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.m2i.paiement.domain.Client} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClientDTO implements Serializable {

    private Long id;

    private String codeAgence;

    private String telephone;

    private String nom;

    private String prenom;

    private String sexe;

    private String titre;

    private String piece;

    private String reference;

    private String autoriteDelivre;

    private String typeClient;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgence() {
        return codeAgence;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAutoriteDelivre() {
        return autoriteDelivre;
    }

    public void setAutoriteDelivre(String autoriteDelivre) {
        this.autoriteDelivre = autoriteDelivre;
    }

    public String getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id=" + getId() +
            ", codeAgence='" + getCodeAgence() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", titre='" + getTitre() + "'" +
            ", piece='" + getPiece() + "'" +
            ", reference='" + getReference() + "'" +
            ", autoriteDelivre='" + getAutoriteDelivre() + "'" +
            ", typeClient='" + getTypeClient() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
