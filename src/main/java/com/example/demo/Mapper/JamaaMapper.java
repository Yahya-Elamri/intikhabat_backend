package com.example.demo.Mapper;

import com.example.demo.Dto.JamaaDTO;
import com.example.demo.Dto.JamaaInputDTO;
import com.example.demo.Entity.Jamaa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JamaaMapper {
    JamaaDTO toDto(Jamaa entity);

    @Mapping(target = "id", ignore = true)
    Jamaa toEntity(JamaaDTO dto);

    Jamaa toEntity(JamaaInputDTO dto);
}
