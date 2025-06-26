package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.services.PerfilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilDto criarPerfil(@RequestBody PerfilRequest perfilRequest) {
        log.info("Criando perfil - controller: {}", perfilRequest);
        try {
            return perfilService.adicionarPerfil(perfilRequest);
        } catch (Exception e) {
            log.error("Erro ao criar perfil", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao criar perfil.");
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerfilDto atualizarPerfil(@PathVariable Long id, @RequestBody PerfilDto perfilDto) {
        try {
            return perfilService.atualizarPerfil(id, perfilDto);
        } catch (Exception e) {
            log.error("Erro ao atualizar perfil ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao atualizar perfil.");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPerfil(@PathVariable Long id) {
        try {
            perfilService.removerPerfil(id);
        } catch (Exception e) {
            log.error("Erro ao remover perfil ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado.");
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerfilDto buscarPerfilPorId(@PathVariable Long id) {
        try {
            return perfilService.buscarPerfilPorId(id);
        } catch (Exception e) {
            log.error("Erro ao buscar perfil ID {}: {}", id, e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Perfil não encontrado.");
        }
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<PerfilDto> listarPerfis() {
        try {
            return perfilService.listarPerfis();
        } catch (Exception e) {
            log.error("Erro ao listar perfis", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao listar perfis.");
        }
    }
}
