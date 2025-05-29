package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.IConvenioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConvenioService {

    private final ModelMapper modelMapper;
    private final IConvenioRepository convenioRepository;

    public ConvenioService(ModelMapper modelMapper, IConvenioRepository convenioRepository) {
        this.modelMapper = modelMapper;
        this.convenioRepository = convenioRepository;
    }

    public ConvenioDto salvar(ConvenioRequest request) {
        Convenio convenio = modelMapper.map(request, Convenio.class);
        convenio = convenioRepository.save(convenio);
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public ConvenioDto buscarPorId(Long id) {
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado com ID: " + id));
        return modelMapper.map(convenio, ConvenioDto.class);
    }

    public List<ConvenioDto> listarTodos() {
        return convenioRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ConvenioDto.class))
                .toList();
    }

    public void deletar(Long id) {
        Convenio convenio = convenioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convênio não encontrado com ID: " + id));
        convenioRepository.delete(convenio);
    }
}

