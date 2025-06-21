package br.edu.imepac.administrativo.controllers;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.services.PerfilService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/perfis")
public class PerfilController {

    private final PerfilService perfilService;
    private final ModelMapper modelMapper;

    // Construtor do controller
    public PerfilController(PerfilService perfilService, ModelMapper modelMapper) {
        this.perfilService = perfilService;
        this.modelMapper = modelMapper;
    }

    // Método para criar um perfil
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilDto criarPerfil(@RequestBody PerfilRequest perfilRequest) {
        log.info("Criando perfil - controller: {}", perfilRequest);
        // Converte o PerfilRequest para Perfil
        var perfil = modelMapper.map(perfilRequest, br.edu.imepac.comum.models.Perfil.class);
        var perfilCriado = perfilService.adicionarPerfil(perfil);
        return modelMapper.map(perfilCriado, PerfilDto.class);
    }

    // Método para atualizar um perfil
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerfilDto atualizarPerfil(@PathVariable Long id, @RequestBody PerfilRequest perfilRequest) {
        log.info("Atualizando perfil - controller: {}", perfilRequest);
        var perfilAtualizado = modelMapper.map(perfilRequest, br.edu.imepac.comum.models.Perfil.class);
        var perfil = perfilService.atualizarPerfil(id, perfilAtualizado);
        return modelMapper.map(perfil, PerfilDto.class);
    }

    // Método para remover um perfil
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerPerfil(@PathVariable Long id) {
        log.info("Removendo perfil com ID: {}", id);
        perfilService.removerPerfil(id);
    }

    // Método para buscar um perfil por ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerfilDto buscarPerfilPorId(@PathVariable Long id) {
        log.info("Buscando perfil com ID: {}", id);
        var perfil = perfilService.buscarPerfilPorId(id);
        return modelMapper.map(perfil, PerfilDto.class);
    }

    // Método para listar todos os perfis
    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<PerfilDto> listarPerfis() {
        log.info("Listando todos os perfis");
        return perfilService.listarPerfis().stream()
                .map(perfil -> modelMapper.map(perfil, PerfilDto.class))
                .collect(Collectors.toList());
    }
}
