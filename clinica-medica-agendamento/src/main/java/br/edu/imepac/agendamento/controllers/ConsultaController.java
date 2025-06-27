package br.edu.imepac.agendamento.controllers;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.services.ConsultaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
@Slf4j
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDto adicionarConsulta(@RequestBody ConsultaRequest request) {
        log.info("Criando consulta - controller: {}", request);
        return consultaService.adicionarConsulta(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto dto) {
        log.info("Atualizar consulta - controller: {}", dto);
        return consultaService.atualizarConsulta(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelarConsulta(@PathVariable Long id) {
        log.info("Cancelar consulta - controller: {}", id);
        consultaService.cancelarConsulta(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto buscarConsultaPorId(@PathVariable Long id) {
        log.info("Buscar consulta - controller: {}", id);
        return consultaService.buscarConsultaPorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaDto> listarConsultas() {
        log.info("Listar consultas - controller");
        return consultaService.listarConsultas();
    }
}
