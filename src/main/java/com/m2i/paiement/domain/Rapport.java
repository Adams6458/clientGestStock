package com.m2i.paiement.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rapport.
 */
@Entity
@Table(name = "rapport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Rapport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "message")
    private String message;

    @Column(name = "code_err")
    private String codeErr;

    @Column(name = "text_expl")
    private String textExpl;

    @Column(name = "errcode")
    private String errcode;

    @Column(name = "transaction_id")
    private String transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rapport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return this.userID;
    }

    public Rapport userID(String userID) {
        this.setUserID(userID);
        return this;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return this.message;
    }

    public Rapport message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeErr() {
        return this.codeErr;
    }

    public Rapport codeErr(String codeErr) {
        this.setCodeErr(codeErr);
        return this;
    }

    public void setCodeErr(String codeErr) {
        this.codeErr = codeErr;
    }

    public String getTextExpl() {
        return this.textExpl;
    }

    public Rapport textExpl(String textExpl) {
        this.setTextExpl(textExpl);
        return this;
    }

    public void setTextExpl(String textExpl) {
        this.textExpl = textExpl;
    }

    public String getErrcode() {
        return this.errcode;
    }

    public Rapport errcode(String errcode) {
        this.setErrcode(errcode);
        return this;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public Rapport transactionId(String transactionId) {
        this.setTransactionId(transactionId);
        return this;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rapport user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rapport)) {
            return false;
        }
        return getId() != null && getId().equals(((Rapport) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rapport{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", message='" + getMessage() + "'" +
            ", codeErr='" + getCodeErr() + "'" +
            ", textExpl='" + getTextExpl() + "'" +
            ", errcode='" + getErrcode() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            "}";
    }
}
