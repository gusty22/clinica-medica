package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.especialidade.EspecialidadeDto;
import br.edu.imepac.comum.dtos.especialidade.EspecialidadeRequest;
import br.edu.imepac.comum.services.EspecialidadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    public EspecialidadeController(EspecialidadeService especialidadeService) {
        this.especialidadeService = especialidadeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EspecialidadeDto criarEspecialidade(@RequestBody EspecialidadeRequest especialidadeRequest) {
        log.info("Criando especialidade - controller: {}", especialidadeRequest);
        try {
            return especialidadeService.adicionarEspecialidade(especialidadeRequest);
        } catch (Exception e) {
            log.error("Erro ao criar especialidade", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar especialidade.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EspecialidadeDto atualizarEspecialidade(@PathVariable Long id, @RequestBody EspecialidadeDto especialidadeDto) {
        try {
            return especialidadeService.atualizarEspecialidade(id, especialidadeDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar especialidade ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar especialidade.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerEspecialidade(@PathVariable Long id) {
        try {
            especialidadeService.removerEspecialidade(id);
        } catch (Exception e) {
            log.error("Erro ao remover especialidade ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EspecialidadeDto buscarEspecialidadePorId(@PathVariable Long id) {
        try {
            return especialidadeService.buscarEspecialidadePorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar especialidade ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidade não encontrada.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<EspecialidadeDto> listarEspecialidades() {
        try {
            return especialidadeService.listarEspecialidades();
        } catch (Exception e) {
            log.error("Erro ao listar especialidades", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar especialidades.");
        }
    }
}