package com.m2i.paiement.service.mapper;

import com.m2i.paiement.domain.Client;
import com.m2i.paiement.domain.Transaction;
import com.m2i.paiement.domain.User;
import com.m2i.paiement.service.dto.ClientDTO;
import com.m2i.paiement.service.dto.TransactionDTO;
import com.m2i.paiement.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    TransactionDTO toDto(Transaction s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
