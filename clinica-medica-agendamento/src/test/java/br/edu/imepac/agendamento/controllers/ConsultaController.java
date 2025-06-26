package br.edu.imepac.agendamento.controllers;

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

    /* Irá entrar no controller e percorrer até o Service, perguntando se essa consulta ja foi marcada ou nao*/

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultaDto createConsulta(@RequestBody ConsultaRequest consultaRequest) {
       /*log.info anotação do Sl4J que é usado para verificar o fluxo*/
        log.info("Criando Consulta - controller:  {}", consultaRequest);

        return consultaService.adicionarConsulta(consultaRequest);
    }

    /* Agora vamos usar o método Put, que será chamado para requisições HTTP PUT no
    endpoint com um ID, ele vai retornar o status HTTP 200 (OK). */

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public ConsultaDto atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaDto consultaDto) {
        log.info("Atualizando Consulta - controller:  {}", consultaDto);
        return consultaService.atualizarConsulta(id, consultaDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)

    public void cancelarConsulta(@PathVariable Long id) {
        log.info("Cancelar Consulta - controller:  {}", id);
        consultaService.removerConsulta(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public ConsultaDto getConsulta(@PathVariable Long id) {
        log.info("Consulta - controller:  {}", id);

        return consultaService.buscarConsultaPorId(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)

    public List<ConsultaDto> listarConsultas() {
        log.info("Listar Consultas - controller");
        return consultaService.listarConsultas();
    }
}
