package com.example.demo.Controller.Jamaa;

import com.example.demo.Dto.JamaaDTO;
import com.example.demo.Dto.JamaaInputDTO;
import com.example.demo.Service.JamaaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/jamaas")
@RequiredArgsConstructor
public class JamaaController {

    private final JamaaService service;

    @GetMapping
    public List<JamaaDTO> getByAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public JamaaDTO getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JamaaDTO create(@RequestBody JamaaInputDTO dto) {
        return service.create(dto);
    }
}
