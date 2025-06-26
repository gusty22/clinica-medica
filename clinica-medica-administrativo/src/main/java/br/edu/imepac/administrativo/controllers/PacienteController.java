package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.services.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteDto criarPaciente(@RequestBody PacienteRequest pacienteRequest) {
        log.info("Criando paciente - controller: {}", pacienteRequest);
        try {
            return pacienteService.adicionarPaciente(pacienteRequest);
        } catch (Exception e) {
            log.error("Erro ao criar paciente", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar paciente.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteDto atualizarPaciente(@PathVariable Long id, @RequestBody PacienteDto pacienteDto) {
        try {
            return pacienteService.atualizarPaciente(id, pacienteDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar paciente ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar paciente.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPaciente(@PathVariable Long id) {
        try {
            pacienteService.removerPaciente(id);
        } catch (Exception e) {
            log.error("Erro ao remover paciente ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteDto buscarPacientePorId(@PathVariable Long id) {
        try {
            return pacienteService.buscarPacientePorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar paciente ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente não encontrado.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<PacienteDto> listarPacientes() {
        try {
            return pacienteService.listarPacientes();
        } catch (Exception e) {
            log.error("Erro ao listar pacientes", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar pacientes.");
        }
    }
}
