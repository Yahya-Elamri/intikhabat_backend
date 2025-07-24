package com.example.demo.Controller.Jamaa;

import com.example.demo.Dto.JamaaDTO;
import com.example.demo.Dto.JamaaInputDTO;
import com.example.demo.Service.JamaaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jamaas")
@RequiredArgsConstructor
public class JamaaController {

    private final JamaaService service;

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
