package com.example.demo.Mapper;

import com.example.demo.Dto.MontakhibDTO;
import com.example.demo.Dto.MontakhibInputDTO;
import com.example.demo.Entity.Jamaa;
import com.example.demo.Entity.Montakhib;
import org.mapstruct.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring" , uses = JamaaMapper.class)
public interface MontakhibMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "jamaa", ignore = true)
    @Mapping(target = "dateNaissance", source = "dateNaissance", qualifiedByName = "stringToDate")
    Montakhib toEntity(MontakhibInputDTO dto);

    @Mapping(target = "jamaa", ignore = true)
    Montakhib toEntity(MontakhibDTO dto);

    MontakhibDTO toDto(Montakhib entity);

    @Mapping(target = "jamaaId", source = "jamaa.id")
    MontakhibInputDTO toInputDto(Montakhib entity);

    @AfterMapping
    default void mapDate(MontakhibInputDTO dto, @MappingTarget Montakhib entity) {
        if (dto.dateNaissance() != null) {
            entity.setDateNaissance(parseDate(dto.dateNaissance()));
        }
    }

    @AfterMapping
    default void mapJamaa(MontakhibDTO dto, @MappingTarget Montakhib entity) {
        if (dto.jamaa() != null) {
            Jamaa jamaa = new Jamaa();
            jamaa.setId(dto.jamaa().id());
            jamaa.setNom(dto.jamaa().nom());
            jamaa.setLastId(dto.jamaa().lastId());
            entity.setJamaa(jamaa);
        }
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }
    private String formatDate(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd").format(date) : null;
    }

    @Named("stringToDate")
    static Date mapStringToDate(String date) {
        if (date == null) return null;
        try {
            return java.sql.Date.valueOf(date); // assumes format "yyyy-MM-dd"
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid date format. Expected yyyy-MM-dd", e);
        }
    }
}