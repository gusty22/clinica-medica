package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.IProntuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProntuarioService {

    private final ModelMapper modelMapper;
    private final IProntuarioRepository prontuarioRepository;

    public ProntuarioService(ModelMapper modelMapper, IProntuarioRepository prontuarioRepository) {
        this.modelMapper = modelMapper;
        this.prontuarioRepository = prontuarioRepository;
    }

    public ProntuarioDto salvar(ProntuarioRequest request) {
        Prontuario prontuario = modelMapper.map(request, Prontuario.class);
        prontuario = prontuarioRepository.save(prontuario);
        return modelMapper.map(prontuario, ProntuarioDto.class);
    }

    public ProntuarioDto buscarPorId(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado com ID: " + id));
        return modelMapper.map(prontuario, ProntuarioDto.class);
    }

    public List<ProntuarioDto> listarTodos() {
        return prontuarioRepository.findAll().stream()
                .map(p -> modelMapper.map(p, ProntuarioDto.class))
                .toList();
    }

    public void deletar(Long id) {
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prontuário não encontrado com ID: " + id));
        prontuarioRepository.delete(prontuario);
    }
}

