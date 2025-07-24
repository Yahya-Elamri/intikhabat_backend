package com.example.demo.Service;

import com.example.demo.Dto.EmployerDTO;
import com.example.demo.Dto.EmployerInputDTO;
import com.example.demo.Entity.Employer;
import com.example.demo.Mapper.EmployerMapper;
import com.example.demo.Repository.EmployerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerService{

    private final EmployerRepository employerRepository;
    private final EmployerMapper employerMapper;

    @Transactional
    public EmployerDTO createEmployer(EmployerInputDTO inputDTO) {
        Employer employer = employerMapper.toEntityFromInput(inputDTO);
        Employer saved = employerRepository.save(employer);
        return employerMapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<EmployerDTO> getAllEmployers() {
        return employerMapper.toDTOList(employerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public EmployerDTO getEmployerById(Long id) {
        Employer employer = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found with id: " + id));
        return employerMapper.toDTO(employer);
    }

    @Transactional(readOnly = true)
    public EmployerDTO findByCin(String cin) {
        Employer employer = employerRepository.findByCin(cin)
                .orElseThrow(() -> new RuntimeException("Employer not found with CIN: " + cin));
        return employerMapper.toDTO(employer);
    }

    @Transactional(readOnly = true)
    public List<EmployerDTO> findByNomAndPrenom(String nom, String prenom) {
        List<Employer> employers = employerRepository.searchByNomAndPrenom(nom, prenom);
        return employerMapper.toDTOList(employers);
    }

    @Transactional
    public EmployerDTO updateEmployer(Long id, EmployerInputDTO inputDTO) {
        Employer existing = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found with id: " + id));

        // update manually mapped fields
        existing.setNom(inputDTO.nom());
        existing.setPrenom(inputDTO.prenom());
        existing.setDateNaissance(inputDTO.dateNaissance());
        existing.setUsername(inputDTO.username());
        existing.setCin(inputDTO.cin());
        existing.setTelephone(inputDTO.telephone());
        existing.setRoles(inputDTO.roles());
        // password is typically encoded before setting if needed

        Employer updated = employerRepository.save(existing);
        return employerMapper.toDTO(updated);
    }

    @Transactional
    public void deleteEmployer(Long id) {
        Employer existing = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found with id: " + id));
        employerRepository.delete(existing);
    }
}