package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.services.ConvenioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/convenios")
@Slf4j
public class ConvenioController {

    private final ConvenioService convenioService;

    public ConvenioController(ConvenioService convenioService) {
        this.convenioService = convenioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConvenioDto criar(@RequestBody ConvenioRequest request) {
        log.info("Criando convênio - controller: {}", request);
        return convenioService.adicionarConvenio(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConvenioDto atualizar(@PathVariable Long id, @RequestBody ConvenioDto dto) {
        log.info("Atualizar convênio - controller: {}", dto);
        return convenioService.atualizarConvenio(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        log.info("Remover convênio - controller: {}", id);
        convenioService.removerConvenio(id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConvenioDto buscarPorId(@PathVariable Long id) {
        log.info("Buscar convênio - controller: {}", id);
        return convenioService.buscarConvenioPorId(id);
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<ConvenioDto> listarTodos() {
        log.info("Listar convênios - controller");
        return convenioService.listarConvenios();
    }
}