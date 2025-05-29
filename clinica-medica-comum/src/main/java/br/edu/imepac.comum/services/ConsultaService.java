package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.models.Consulta;
import br.edu.imepac.comum.repositories.IConsultaRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ConsultaService {

    private final ModelMapper modelMapper;
    private final IConsultaRepository consultaRepository;

    public ConsultaService(ModelMapper modelMapper, IConsultaRepository consultaRepository) {
        this.modelMapper = modelMapper;
        this.consultaRepository = consultaRepository;
    }

    public ConsultaDto agendarConsulta(ConsultaRequest request) {
        Consulta consulta = modelMapper.map(request, Consulta.class);
        consulta = consultaRepository.save(consulta);
        return modelMapper.map(consulta, ConsultaDto.class);
    }

    public List<ConsultaDto> listarConsultas() {
        return consultaRepository.findAll().stream()
                .map(c -> modelMapper.map(c, ConsultaDto.class))
                .toList();
    }
}

