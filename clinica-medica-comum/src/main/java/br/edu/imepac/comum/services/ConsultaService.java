package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.repositories.IConsultaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConsultaService {

    private final ModelMapper modelMapper;
    private final IConsultaRepository consultaRepository;

    public ConsultaService(ModelMapper modelMapper, IConsultaRepository consultaRepository) {
        this.modelMapper = modelMapper;
        this.consultaRepository = consultaRepository;
    }

    public ConsultaDto adicionarConsulta(ConsultaRequest consultaRequest) {
        log.info("Cadastro de consulta - service: {}", consultaRequest);
        Consulta consulta = modelMapper.map(consultaRequest, Consulta.class);
        consulta = consultaRepository.save(consulta);
        return modelMapper.map(consulta, ConsultaDto.class);
    }

    public ConsultaDto atualizarConsulta(Long id, ConsultaDto consultaDto) {
        log.info("Atualizando consulta com ID: {}", id);
        Consulta consultaExistente = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));
        modelMapper.map(consultaDto, consultaExistente);
        Consulta consultaAtualizada = consultaRepository.save(consultaExistente);
        return modelMapper.map(consultaAtualizada, ConsultaDto.class);
    }

    public void removerConsulta(Long id) {
        log.info("Removendo consulta com ID: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));
        consultaRepository.delete(consulta);
    }

    public ConsultaDto buscarConsultaPorId(Long id) {
        log.info("Buscando consulta com ID: {}", id);
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Consulta não encontrada com ID: " + id));
        return modelMapper.map(consulta, ConsultaDto.class);
    }

    public List<ConsultaDto> listarConsultas() {
        log.info("Listando todas as consultas");
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(consulta -> modelMapper.map(consulta, ConsultaDto.class))
                .toList();
    }
}
