package com.m2i.paiement.service.mapper;

import com.m2i.paiement.domain.Rapport;
import com.m2i.paiement.domain.User;
import com.m2i.paiement.service.dto.RapportDTO;
import com.m2i.paiement.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Rapport} and its DTO {@link RapportDTO}.
 */
@Mapper(componentModel = "spring")
public interface RapportMapper extends EntityMapper<RapportDTO, Rapport> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    RapportDTO toDto(Rapport s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
