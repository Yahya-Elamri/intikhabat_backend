package com.example.demo.Service;

import com.example.demo.Dto.MontakhibDTO;
import com.example.demo.Dto.MontakhibInputDTO;
import com.example.demo.Entity.*;
import com.example.demo.Mapper.MontakhibMapper;
import com.example.demo.Repository.JamaaRepository;
import com.example.demo.Repository.MontakhibRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;


@Service
@RequiredArgsConstructor
public class MontakhibService {

    private final MontakhibRepository repository;
    private final JamaaRepository jamaaRepository;
    private final MontakhibMapper mapper;

    @Transactional(readOnly = true)
    public MontakhibDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Montakhib not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<MontakhibDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MontakhibDTO> searchByCin(String cin) {
        return repository.findByCinContainingIgnoreCase(cin)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MontakhibDTO>searchByNomAndPrenom(String nom,String prenom){
        return repository.searchByNomAndPrenom(nom,prenom)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Transactional
    public MontakhibDTO create(MontakhibInputDTO dto) {
        if (repository.existsByCin(dto.cin())) {
            throw new DataIntegrityViolationException("CIN must be unique");
        }

        Jamaa jamaa = jamaaRepository.findById(dto.jamaaId())
                .orElseThrow(() -> new EntityNotFoundException("Jamaa not found with id: " + dto.jamaaId()));

        Montakhib entity = mapper.toEntity(dto);
        entity.setJamaa(jamaa);
        return mapper.toDto(repository.save(entity));
    }

    @Transactional
    public MontakhibDTO update(Long id, MontakhibInputDTO dto) {
        Montakhib existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Montakhib not found with id: " + id));

        if (!existing.getCin().equals(dto.cin()) && repository.existsByCin(dto.cin())) {
            throw new DataIntegrityViolationException("CIN must be unique");
        }

        Jamaa jamaa = jamaaRepository.findById(dto.jamaaId())
                .orElseThrow(() -> new EntityNotFoundException("Jamaa not found with id: " + dto.jamaaId()));

        mapper.toEntity(dto); // Updates date mapping
        existing.setNom(dto.nom());
        existing.setPrenom(dto.prenom());
        existing.setCin(dto.cin());
        existing.setLieuNaissance(dto.lieuNaissance());
        existing.setAdresse(dto.adresse());
        existing.setSex(dto.sex());
        existing.setEducation(dto.education());
        existing.setSituationFamiliale(dto.situationFamiliale());
        existing.setJamaa(jamaa);

        return mapper.toDto(repository.save(existing));
    }
}
