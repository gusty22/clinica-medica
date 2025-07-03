package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.IConsultaRepository;
import br.edu.imepac.comum.repositories.IProntuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProntuarioService {

    private final ModelMapper modelMapper;
    private final IProntuarioRepository prontuarioRepository;
    private final IConsultaRepository consultaRepository;

    public ProntuarioService(ModelMapper modelMapper, IProntuarioRepository prontuarioRepository, IConsultaRepository consultaRepository) {
        this.modelMapper = modelMapper;
        this.prontuarioRepository = prontuarioRepository;
        this.consultaRepository = consultaRepository;
    }

    public ProntuarioDto adicionarProntuario(ProntuarioRequest prontuarioRequest) {
        log.info("Cadastro de prontuário - service: {}", prontuarioRequest);
        Prontuario prontuario = modelMapper.map(prontuarioRequest, Prontuario.class);
        prontuario = prontuarioRepository.save(prontuario);
        return modelMapper.map(prontuario, ProntuarioDto.class);
    }

    public ProntuarioDto atualizarProntuario(Long id, ProntuarioDto prontuarioDto) {
        log.info("Atualizando prontuário com ID: {}", id);
        Prontuario prontuarioExistente = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com ID: " + id));
        modelMapper.map(prontuarioDto, prontuarioExistente);
        Prontuario prontuarioAtualizado = prontuarioRepository.save(prontuarioExistente);
        return modelMapper.map(prontuarioAtualizado, ProntuarioDto.class);
    }

    @Transactional
    public void removerProntuario(Long id) {
        log.info("Removendo prontuário com ID: {}", id);
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com ID: " + id));

        List<Consulta> consultas = prontuario.getConsultas();
        if (consultas != null && !consultas.isEmpty()) {
            for (Consulta consulta : consultas) {
                consulta.setProntuario(null);
                consultaRepository.save(consulta);
            }
        }

        prontuarioRepository.delete(prontuario);
    }

    public ProntuarioDto buscarProntuarioPorId(Long id) {
        log.info("Buscando prontuário com ID: {}", id);
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com ID: " + id));
        return modelMapper.map(prontuario, ProntuarioDto.class);
    }

    public List<ProntuarioDto> listarProntuarios() {
        log.info("Listando todos os prontuários");
        List<Prontuario> prontuarios = prontuarioRepository.findAll();
        return prontuarios.stream()
                .map(prontuario -> modelMapper.map(prontuario, ProntuarioDto.class))
                .collect(Collectors.toList());
    }
}