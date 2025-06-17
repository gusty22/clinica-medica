package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.services.ConsultaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
        return consultaService.adicionarConsulta(consultaRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        return consultaService.atualizarConsulta(id, consultaDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerConsulta(@PathVariable Long id) {
        consultaService.removerConsulta(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaDto buscarConsultaPorId(@PathVariable Long id) {
        return consultaService.buscarConsultaPorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ConsultaDto> listarConsultas() {
        return consultaService.listarConsultas();
    }
}
