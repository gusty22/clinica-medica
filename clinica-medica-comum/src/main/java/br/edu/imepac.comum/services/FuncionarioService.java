package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FuncionarioService {

    private final ModelMapper modelMapper;
    private final IFuncionarioRepository funcionarioRepository;

    public FuncionarioService(ModelMapper modelMapper, IFuncionarioRepository funcionarioRepository) {
        this.modelMapper = modelMapper;
        this.funcionarioRepository = funcionarioRepository;
    }

    public FuncionarioDto adicionarFuncionario(FuncionarioRequest request) {
        log.info("Cadastro de funcionário: {}", request);
        Funcionario funcionario = modelMapper.map(request, Funcionario.class);
        funcionario = funcionarioRepository.save(funcionario);
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public FuncionarioDto atualizarFuncionario(Long id, FuncionarioDto dto) {
        log.info("Atualizando funcionário com ID: {}", id);
        Funcionario existente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        modelMapper.map(dto, existente);
        Funcionario atualizado = funcionarioRepository.save(existente);
        return modelMapper.map(atualizado, FuncionarioDto.class);
    }

    public void removerFuncionario(Long id) {
        log.info("Removendo funcionário com ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        funcionarioRepository.delete(funcionario);
    }

    public FuncionarioDto buscarPorId(Long id) {
        log.info("Buscando funcionário com ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public List<FuncionarioDto> listarTodos() {
        return funcionarioRepository.findAll().stream()
                .map(f -> modelMapper.map(f, FuncionarioDto.class))
                .toList();
    }
}

