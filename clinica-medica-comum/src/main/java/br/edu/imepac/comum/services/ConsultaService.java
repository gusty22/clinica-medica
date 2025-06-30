package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.*;
import br.edu.imepac.comum.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class ConsultaService {

    private final ModelMapper modelMapper;
    private final IConsultaRepository consultaRepository;
    private final IFuncionarioRepository funcionarioRepository;
    private final IPacienteRepository pacienteRepository;
    private final IConvenioRepository convenioRepository;
    private final IProntuarioRepository prontuarioRepository;

    public ConsultaService(
            ModelMapper modelMapper,
            IConsultaRepository consultaRepository,
            IFuncionarioRepository funcionarioRepository,
            IPacienteRepository pacienteRepository,
            IConvenioRepository convenioRepository,
            IProntuarioRepository prontuarioRepository) {
        this.modelMapper = modelMapper;
        this.consultaRepository = consultaRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.pacienteRepository = pacienteRepository;
        this.convenioRepository = convenioRepository;
        this.prontuarioRepository = prontuarioRepository;
    }

    public ConsultaDto adicionarConsulta(ConsultaRequest consultaRequest) {
        log.info("Cadastro de consulta - service: {}", consultaRequest);

        Consulta consulta = new Consulta();
        consulta.setDataHorario(LocalDateTime.of(consultaRequest.getData(), consultaRequest.getHorario()));
        consulta.setERetorno(consultaRequest.getERetorno() != null ? consultaRequest.getERetorno() : false);
        consulta.setEstaAtiva(consultaRequest.getEstaAtiva() != null ? consultaRequest.getEstaAtiva() : true);
        consulta.setSintomas(consultaRequest.getSintomas());

        Funcionario funcionario = funcionarioRepository.findById(consultaRequest.getFuncionarioId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Funcionário não encontrado com ID: " + consultaRequest.getFuncionarioId()));
        Paciente paciente = pacienteRepository.findById(consultaRequest.getPacienteId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Paciente não encontrado com ID: " + consultaRequest.getPacienteId()));
        Convenio convenio = convenioRepository.findById(consultaRequest.getConvenioId())
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado com ID: " + consultaRequest.getConvenioId()));

        consulta.setFuncionario(funcionario);
        consulta.setPaciente(paciente);
        consulta.setConvenio(convenio);

        if (consultaRequest.getProntuarioId() != null) {
            Prontuario prontuario = prontuarioRepository.findById(consultaRequest.getProntuarioId())
                    .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com ID: " + consultaRequest.getProntuarioId()));
            consulta.setProntuario(prontuario);
        } else {
            consulta.setProntuario(null);
        }

        consulta = consultaRepository.save(consulta);

        return mapToDto(consulta);
    }


    public ConsultaDto atualizarConsulta(Long id, ConsultaDto consultaDto) {
        log.info("Atualizando consulta com ID: {}", id);
        Consulta consultaExistente = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));

        consultaExistente.setDataHorario(LocalDateTime.of(consultaDto.getData(), consultaDto.getHorario()));
        Consulta consultaAtualizada = consultaRepository.save(consultaExistente);
        return mapToDto(consultaAtualizada);
    }

    public void cancelarConsulta(Long id) {
        log.info("Cancelando consulta com ID: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));
        consultaRepository.delete(consulta);
    }

    public ConsultaDto buscarConsultaPorId(Long id) {
        log.info("Buscando consulta com ID: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));
        return mapToDto(consulta);
    }

    public List<ConsultaDto> listarConsultas() {
        log.info("Listando todas as consultas");
        return consultaRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    private ConsultaDto mapToDto(Consulta consulta) {
        return new ConsultaDto(
                (long) consulta.getId(),
                consulta.getDataHorario() != null ? consulta.getDataHorario().toLocalDate() : null,
                consulta.getDataHorario() != null ? consulta.getDataHorario().toLocalTime() : null,
                modelMapper.map(consulta.getFuncionario(), br.edu.imepac.comum.dtos.funcionario.FuncionarioDto.class),
                modelMapper.map(consulta.getPaciente(), br.edu.imepac.comum.dtos.paciente.PacienteDto.class),
                consulta.getProntuario() != null ? modelMapper.map(consulta.getProntuario(), br.edu.imepac.comum.dtos.prontuario.ProntuarioDto.class) : null
        );
    }
}
