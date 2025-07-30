package com.example.demo.Controller.Montakhib;

import com.example.demo.Dto.JamaaDTO;
import com.example.demo.Dto.JamaaInputDTO;
import com.example.demo.Dto.MontakhibDTO;
import com.example.demo.Dto.MontakhibInputDTO;
import com.example.demo.Service.JamaaService;
import com.example.demo.Service.MontakhibService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class MontakhibGraphQLController {

    private final JamaaService jamaaService;
    private final MontakhibService montakhibService;

    @QueryMapping
    public MontakhibDTO getMontakhib(@Argument Long id) {
        return montakhibService.findById(id);
    }

    @QueryMapping
    public List<MontakhibDTO> getAllMontakhibin() {
        return montakhibService.findAll();
    }

    @QueryMapping
    public JamaaDTO getJamaa(@Argument Long id) {
        return jamaaService.findById(id);
    }

    @QueryMapping
    public List<MontakhibDTO> searchMontakhibs(@Argument String cin) {
        return montakhibService.searchByCin(cin);
    }


    @QueryMapping
    public List<MontakhibDTO> searchMontakhibByNomAndPrenom(@Argument String nom,@Argument String prenom) {
        return montakhibService.searchByNomAndPrenom(nom,prenom);
    }

    @QueryMapping
    public List<MontakhibDTO> getMontakhibByJamaa(@Argument String jamaa){
        return montakhibService.searchByJamaaNom(jamaa);
    }

    @MutationMapping
    public MontakhibDTO createMontakhib(@Argument MontakhibInputDTO input) {
        return montakhibService.create(input);
    }

    @MutationMapping
    public JamaaDTO createJamaa(@Argument JamaaInputDTO input) {
        return jamaaService.create(input);
    }

    @MutationMapping
    public MontakhibDTO updateMontakhib(@Argument Long id, @Argument MontakhibInputDTO input) {
        return montakhibService.update(id, input);
    }
}
