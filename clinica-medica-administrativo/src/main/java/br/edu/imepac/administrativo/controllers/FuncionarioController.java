package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.services.FuncionarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FuncionarioDto criarFuncionario(@RequestBody FuncionarioRequest funcionarioRequest) {
        log.info("Criando funcionário - controller: {}", funcionarioRequest);
        try {
            return funcionarioService.adicionarFuncionario(funcionarioRequest);
        } catch (Exception e) {
            log.error("Erro ao criar funcionário", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar funcionário.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioDto funcionarioDto) {
        try {
            return funcionarioService.atualizarFuncionario(id, funcionarioDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar funcionário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar funcionário.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.removerFuncionario(id);
        } catch (Exception e) {
            log.error("Erro ao remover funcionário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FuncionarioDto buscarFuncionarioPorId(@PathVariable Long id) {
        try {
            return funcionarioService.buscarFuncionarioPorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar funcionário ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Funcionário não encontrado.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<FuncionarioDto> listarFuncionarios() {
        try {
            return funcionarioService.listarFuncionarios();
        } catch (Exception e) {
            log.error("Erro ao listar funcionários", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar funcionários.");
        }
    }
}
