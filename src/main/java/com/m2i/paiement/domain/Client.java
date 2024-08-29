package com.m2i.paiement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "code_agence")
    private String codeAgence;

    @Column(name = "telephone")
    private String telephone;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "sexe")
    private String sexe;

    @Column(name = "titre")
    private String titre;

    @Column(name = "piece")
    private String piece;

    @Column(name = "reference")
    private String reference;

    @Column(name = "autorite_delivre")
    private String autoriteDelivre;

    @Column(name = "type_client")
    private String typeClient;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "client" }, allowSetters = true)
    private Set<Transaction> transactions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeAgence() {
        return this.codeAgence;
    }

    public Client codeAgence(String codeAgence) {
        this.setCodeAgence(codeAgence);
        return this;
    }

    public void setCodeAgence(String codeAgence) {
        this.codeAgence = codeAgence;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Client telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNom() {
        return this.nom;
    }

    public Client nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Client prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return this.sexe;
    }

    public Client sexe(String sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTitre() {
        return this.titre;
    }

    public Client titre(String titre) {
        this.setTitre(titre);
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getPiece() {
        return this.piece;
    }

    public Client piece(String piece) {
        this.setPiece(piece);
        return this;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public String getReference() {
        return this.reference;
    }

    public Client reference(String reference) {
        this.setReference(reference);
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAutoriteDelivre() {
        return this.autoriteDelivre;
    }

    public Client autoriteDelivre(String autoriteDelivre) {
        this.setAutoriteDelivre(autoriteDelivre);
        return this;
    }

    public void setAutoriteDelivre(String autoriteDelivre) {
        this.autoriteDelivre = autoriteDelivre;
    }

    public String getTypeClient() {
        return this.typeClient;
    }

    public Client typeClient(String typeClient) {
        this.setTypeClient(typeClient);
        return this;
    }

    public void setTypeClient(String typeClient) {
        this.typeClient = typeClient;
    }

    public Set<Transaction> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        if (this.transactions != null) {
            this.transactions.forEach(i -> i.setClient(null));
        }
        if (transactions != null) {
            transactions.forEach(i -> i.setClient(this));
        }
        this.transactions = transactions;
    }

    public Client transactions(Set<Transaction> transactions) {
        this.setTransactions(transactions);
        return this;
    }

    public Client addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
        transaction.setClient(this);
        return this;
    }

    public Client removeTransaction(Transaction transaction) {
        this.transactions.remove(transaction);
        transaction.setClient(null);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Client user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && getId().equals(((Client) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
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
            "}";
    }
}
