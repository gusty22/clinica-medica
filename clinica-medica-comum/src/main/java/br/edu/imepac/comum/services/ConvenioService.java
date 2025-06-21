package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.IConvenioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConvenioService {

    private final ModelMapper modelMapper;
    private final IConvenioRepository convenioRepository;

    public ConvenioService(ModelMapper modelMapper, IConvenioRepository convenioRepository) {
        this.modelMapper = modelMapper;
        this.convenioRepository = convenioRepository;
    }

    public ConvenioDto adicionarConvenio(ConvenioRequest convenioRequest) {
        log.info("Cadastro de convênio - service: {}", convenioRequest);
        Convenio convenio = modelMapper.map(convenioRequest, Convenio.class);
        convenio = convenioRepository.save(convenio);
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public ConvenioDto atualizarConvenio(Long id, ConvenioDto convenioDto) {
        log.info("Atualizando convênio com ID: {}", id);
        Convenio convenioExistente = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado com ID: " + id));
        modelMapper.map(convenioDto, convenioExistente);
        Convenio convenioAtualizado = convenioRepository.save(convenioExistente);
        return modelMapper.map(convenioAtualizado, ConvenioDto.class);
    }

    public void removerConvenio(Long id) {
        log.info("Removendo convênio com ID: {}", id);
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado com ID: " + id));
        convenioRepository.delete(convenio);
    }

    public ConvenioDto buscarConvenioPorId(Long id) {
        log.info("Buscando convênio com ID: {}", id);
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new NotFoundClinicaMedicaException("Convênio não encontrado com ID: " + id));
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public List<ConvenioDto> listarConvenios() {
        log.info("Listando todos os convênios");
        List<Convenio> convenios = convenioRepository.findAll();
        return convenios.stream()
                .map(convenio -> modelMapper.map(convenio, ConvenioDto.class))
                .toList();
    }
}
