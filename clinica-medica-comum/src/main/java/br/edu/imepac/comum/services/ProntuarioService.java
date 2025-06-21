package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.IProntuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProntuarioService {

    private final ModelMapper modelMapper;
    private final IProntuarioRepository prontuarioRepository;

    public ProntuarioService(ModelMapper modelMapper, IProntuarioRepository prontuarioRepository) {
        this.modelMapper = modelMapper;
        this.prontuarioRepository = prontuarioRepository;
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

    public void removerProntuario(Long id) {
        log.info("Removendo prontuário com ID: {}", id);
        Prontuario prontuario = prontuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Prontuário não encontrado com ID: " + id));
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
                .toList();
    }
}
