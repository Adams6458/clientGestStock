package com.m2i.paiement.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.m2i.paiement.domain.Rapport} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RapportDTO implements Serializable {

    private Long id;

    private String userID;

    private String message;

    private String codeErr;

    private String textExpl;

    private String errcode;

    private String transactionId;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCodeErr() {
        return codeErr;
    }

    public void setCodeErr(String codeErr) {
        this.codeErr = codeErr;
    }

    public String getTextExpl() {
        return textExpl;
    }

    public void setTextExpl(String textExpl) {
        this.textExpl = textExpl;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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
        if (!(o instanceof RapportDTO)) {
            return false;
        }

        RapportDTO rapportDTO = (RapportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rapportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RapportDTO{" +
            "id=" + getId() +
            ", userID='" + getUserID() + "'" +
            ", message='" + getMessage() + "'" +
            ", codeErr='" + getCodeErr() + "'" +
            ", textExpl='" + getTextExpl() + "'" +
            ", errcode='" + getErrcode() + "'" +
            ", transactionId='" + getTransactionId() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
