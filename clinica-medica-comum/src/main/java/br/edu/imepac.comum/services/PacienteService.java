package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.IPacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PacienteService {

    private final ModelMapper modelMapper;
    private final IPacienteRepository pacienteRepository;

    public PacienteService(ModelMapper modelMapper, IPacienteRepository pacienteRepository) {
        this.modelMapper = modelMapper;
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteDto adicionar(PacienteRequest request) {
        log.info("Cadastro de paciente: {}", request);
        Paciente paciente = modelMapper.map(request, Paciente.class);
        paciente = pacienteRepository.save(paciente);
        return modelMapper.map(paciente, PacienteDto.class);
    }

    public PacienteDto atualizar(Long id, PacienteDto dto) {
        log.info("Atualizando paciente com ID: {}", id);
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
        modelMapper.map(dto, existente);
        return modelMapper.map(pacienteRepository.save(existente), PacienteDto.class);
    }

    public void remover(Long id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id));
        pacienteRepository.delete(paciente);
    }

    public PacienteDto buscarPorId(Long id) {
        return modelMapper.map(
                pacienteRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + id)),
                PacienteDto.class
        );
    }

    public List<PacienteDto> listarTodos() {
        return pacienteRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PacienteDto.class))
                .toList();
    }
}

