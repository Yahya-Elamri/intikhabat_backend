package com.example.demo.Service;

import com.example.demo.Dto.JamaaDTO;
import com.example.demo.Dto.JamaaInputDTO;
import com.example.demo.Entity.Jamaa;
import com.example.demo.Mapper.JamaaMapper;
import com.example.demo.Repository.JamaaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JamaaService {

    private final JamaaRepository repository;
    private final JamaaMapper mapper;

    @Transactional(readOnly = true)
    public JamaaDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Jamaa not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<JamaaDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public JamaaDTO create(JamaaInputDTO dto) {
        Jamaa entity = mapper.toEntity(dto);
        return mapper.toDto(repository.save(entity));
    }
}
