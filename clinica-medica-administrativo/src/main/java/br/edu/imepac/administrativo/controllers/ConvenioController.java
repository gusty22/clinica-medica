package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.services.ConvenioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/convenios")
public class ConvenioController {

    private final ConvenioService convenioService;

    public ConvenioController(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConvenioDto criarConvenio(@RequestBody ConvenioRequest convenioRequest) {
        log.info("Criando convênio - controller: {}", convenioRequest);
        try {
            return convenioService.adicionarConvenio(convenioRequest);
        } catch (Exception e) {
            log.error("Erro ao criar convênio", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar convênio.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConvenioDto atualizarConvenio(@PathVariable Long id, @RequestBody ConvenioDto convenioDto) {
        try {
            return convenioService.atualizarConvenio(id, convenioDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar convênio ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar convênio.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerConvenio(@PathVariable Long id) {
        try {
            convenioService.removerConvenio(id);
        } catch (Exception e) {
            log.error("Erro ao remover convênio ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Convênio não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConvenioDto buscarConvenioPorId(@PathVariable Long id) {
        try {
            return convenioService.buscarConvenioPorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar convênio ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Convênio não encontrado.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ConvenioDto> listarConvenios() {
        try {
            return convenioService.listarConvenios();
        } catch (Exception e) {
            log.error("Erro ao listar convênios", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar convênios.");
        }
    }
}
