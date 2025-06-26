package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.services.ConsultaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDto criarConsulta(@RequestBody ConsultaRequest consultaRequest) {
        log.info("Criando consulta - controller: {}", consultaRequest);
        try {
            return consultaService.adicionarConsulta(consultaRequest);
        } catch (Exception e) {
            log.error("Erro ao criar consulta", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar consulta.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        try {
            return consultaService.atualizarConsulta(id, consultaDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar consulta ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar consulta.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerConsulta(@PathVariable Long id) {
        try {
            consultaService.removerConsulta(id);
        } catch (Exception e) {
            log.error("Erro ao remover consulta ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto buscarConsultaPorId(@PathVariable Long id) {
        try {
            return consultaService.buscarConsultaPorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar consulta ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Consulta não encontrada.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaDto> listarConsultas() {
        try {
            return consultaService.listarConsultas();
        } catch (Exception e) {
            log.error("Erro ao listar consultas", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar consultas.");
        }
    }
}
