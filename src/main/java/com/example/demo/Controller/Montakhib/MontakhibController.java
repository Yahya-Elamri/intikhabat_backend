package com.example.demo.Controller.Montakhib;

import com.example.demo.Dto.MontakhibDTO;
import com.example.demo.Dto.MontakhibInputDTO;
import com.example.demo.Service.MontakhibService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/add/montakhibs")
@RequiredArgsConstructor
public class MontakhibController {

    private final MontakhibService service;

    @GetMapping("/{id}")
    public MontakhibDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/search")
    public List<MontakhibDTO> search(@RequestParam String cin) {
        return service.searchByCin(cin);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MontakhibDTO create(@RequestBody MontakhibInputDTO dto) {
        System.out.println("wslna " + dto.toString());
        return service.create(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Manage')")
    public MontakhibDTO update(@PathVariable Long id, @RequestBody MontakhibInputDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('Manage')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteMontakhib(id);
        return ResponseEntity.noContent().build();
    }
}
