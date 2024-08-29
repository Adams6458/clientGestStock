package com.m2i.paiement.service.mapper;

import com.m2i.paiement.domain.Client;
import com.m2i.paiement.domain.User;
import com.m2i.paiement.service.dto.ClientDTO;
import com.m2i.paiement.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Client} and its DTO {@link ClientDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    ClientDTO toDto(Client s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
