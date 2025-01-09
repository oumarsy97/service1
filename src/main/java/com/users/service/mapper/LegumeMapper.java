package com.users.service.mapper;

import com.users.domain.Legume;
import com.users.service.dto.LegumeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Legume} and its DTO {@link LegumeDTO}.
 */
@Mapper(componentModel = "spring")
public interface LegumeMapper extends EntityMapper<LegumeDTO, Legume> {}
