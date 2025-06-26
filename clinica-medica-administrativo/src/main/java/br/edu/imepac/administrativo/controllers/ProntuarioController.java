package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.services.ProntuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            return prontuarioService.adicionarProntuario(prontuarioRequest);
        } catch (Exception e) {
            log.error("Erro ao criar prontuário", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar prontuário.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioDto atualizarProntuario(@PathVariable Long id, @RequestBody ProntuarioDto prontuarioDto) {
        try {
            return prontuarioService.atualizarProntuario(id, prontuarioDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar prontuário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar prontuário.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerProntuario(@PathVariable Long id) {
        try {
            prontuarioService.removerProntuario(id);
        } catch (Exception e) {
            log.error("Erro ao remover prontuário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prontuário não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProntuarioDto buscarProntuarioPorId(@PathVariable Long id) {
        try {
            return prontuarioService.buscarProntuarioPorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar prontuário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Prontuário não encontrado.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ProntuarioDto> listarProntuarios() {
        try {
            return prontuarioService.listarProntuarios();
        } catch (Exception e) {
            log.error("Erro ao listar prontuários", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar prontuários.");
        }
    }
}
