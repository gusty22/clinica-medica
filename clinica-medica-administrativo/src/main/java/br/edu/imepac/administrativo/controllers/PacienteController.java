package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.services.PacienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // Gera automaticamente um logger para a classe, permitindo registrar mensagens de log.
@RestController // Indica que esta classe é um controlador REST, retornando dados diretamente como resposta (ex.: JSON).
@RequestMapping("/pacientes") // Define o endpoint base para todos os métodos desta classe.
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PacienteDto criarPaciente(@RequestBody PacienteRequest pacienteRequest) {
        log.info("Criando paciente - controller: {}", pacienteRequest);
        return pacienteService.adicionarPaciente(pacienteRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteDto atualizarPaciente(@PathVariable Long id, @RequestBody PacienteDto pacienteDto) {
        log.info("Atualizar paciente - controller: {}", pacienteDto);
        return pacienteService.atualizarPaciente(id, pacienteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPaciente(@PathVariable Long id) {
        log.info("Remover paciente - controller: {}", id);
        pacienteService.removerPaciente(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PacienteDto buscarPacientePorId(@PathVariable Long id) {
        log.info("Buscar paciente - controller: {}", id);
        return pacienteService.buscarPacientePorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<PacienteDto> listarPacientes() {
        log.info("Listar pacientes - controller");
        return pacienteService.listarPacientes();
    }
}
