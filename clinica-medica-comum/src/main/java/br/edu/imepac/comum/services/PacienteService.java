package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.repositories.IPacienteRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PacienteService {

    private final ModelMapper modelMapper;
    private final IPacienteRepository pacienteRepository;

    public PacienteService(ModelMapper modelMapper, IPacienteRepository pacienteRepository) {
        this.modelMapper = modelMapper;
        this.pacienteRepository = pacienteRepository;
    }

    public PacienteDto adicionarPaciente(PacienteRequest pacienteRequest) {
        log.info("Cadastro de paciente - service: {}", pacienteRequest);
        Paciente paciente = modelMapper.map(pacienteRequest, Paciente.class);
        paciente = pacienteRepository.save(paciente);
        return modelMapper.map(paciente, PacienteDto.class);
    }

    public PacienteDto atualizarPaciente(Long id, PacienteDto pacienteDto) {
        log.info("Atualizando paciente com ID: {}", id);
        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com ID: " + id));
        modelMapper.map(pacienteDto, pacienteExistente);
        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        return modelMapper.map(pacienteAtualizado, PacienteDto.class);
    }

    public void removerPaciente(Long id) {
        log.info("Removendo paciente com ID: {}", id);
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com ID: " + id));
        pacienteRepository.delete(paciente);
    }

    public PacienteDto buscarPacientePorId(Long id) {
        log.info("Buscando paciente com ID: {}", id);
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com ID: " + id));
        return modelMapper.map(paciente, PacienteDto.class);
    }

    public List<PacienteDto> listarPacientes() {
        log.info("Listando todos os pacientes");
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(paciente -> modelMapper.map(paciente, PacienteDto.class))
                .toList();
    }
}
