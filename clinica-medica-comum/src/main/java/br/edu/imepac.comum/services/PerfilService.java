package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.IPerfilRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PerfilService {

    private final ModelMapper modelMapper;
    private final IPerfilRepository perfilRepository;

    public PerfilService(ModelMapper modelMapper, IPerfilRepository perfilRepository) {
        this.modelMapper = modelMapper;
        this.perfilRepository = perfilRepository;
    }

    public PerfilDto salvar(PerfilRequest request) {
        Perfil perfil = modelMapper.map(request, Perfil.class);
        perfil = perfilRepository.save(perfil);
        return modelMapper.map(perfil, PerfilDto.class);
    }

    public PerfilDto buscarPorId(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        return modelMapper.map(perfil, PerfilDto.class);
    }

    public List<PerfilDto> listarTodos() {
        return perfilRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PerfilDto.class))
                .toList();
    }

    public void deletar(Long id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
        perfilRepository.delete(perfil);
    }
}
