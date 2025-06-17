package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/prontuarios")
public class ProntuarioController {

    private final ProntuarioService prontuarioService;

    public ProntuarioController(ProntuarioService prontuarioService) {
        this.prontuarioService = prontuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProntuarioDto criarProntuario(@RequestBody ProntuarioRequest prontuarioRequest) {
        log.info("Criando prontuário - controller: {}", prontuarioRequest);
        return prontuarioService.adicionarProntuario(prontuarioRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioDto atualizarProntuario(@PathVariable Long id, @RequestBody ProntuarioDto prontuarioDto) {
        return prontuarioService.atualizarProntuario(id, prontuarioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerProntuario(@PathVariable Long id) {
        prontuarioService.removerProntuario(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioDto buscarProntuarioPorId(@PathVariable Long id) {
        return prontuarioService.buscarProntuarioPorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ProntuarioDto> listarProntuarios() {
        return prontuarioService.listarProntuarios();
    }
}
