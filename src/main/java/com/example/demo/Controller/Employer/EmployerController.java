package com.example.demo.Controller.Employer;

import com.example.demo.Dto.EmployerDTO;
import com.example.demo.Dto.EmployerInputDTO;
import com.example.demo.Service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<EmployerDTO> create(@RequestBody EmployerInputDTO inputDTO) {
        EmployerDTO created = employerService.createEmployer(inputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployerDTO> update(@PathVariable Long id, @RequestBody EmployerInputDTO inputDTO) {
        EmployerDTO updated = employerService.updateEmployer(id, inputDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.noContent().build();
    }
}
