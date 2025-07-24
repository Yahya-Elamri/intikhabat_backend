package com.example.demo.Mapper;

import com.example.demo.Dto.EmployerDTO;
import com.example.demo.Dto.EmployerInputDTO;
import com.example.demo.Dto.MontakhibDTO;
import com.example.demo.Dto.MontakhibInputDTO;
import com.example.demo.Entity.Employer;
import com.example.demo.Entity.Jamaa;
import com.example.demo.Entity.Montakhib;
import com.example.demo.Entity.Role;
import org.mapstruct.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployerMapper {
    // Entity to DTO
    EmployerDTO toDTO(Employer employer);

    // DTO to Entity
    Employer toEntity(EmployerDTO dto);

    // InputDTO to Entity
    @Mapping(target = "id", ignore = true)
    Employer toEntityFromInput(EmployerInputDTO input);

    // List mapping
    List<EmployerDTO> toDTOList(List<Employer> employers);

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

    default Set<Role> map(String roles) {
        if (roles == null || roles.isEmpty()) return new HashSet<>();
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    default String map(Set<Role> roles) {
        if (roles == null || roles.isEmpty()) return "";
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }
}
