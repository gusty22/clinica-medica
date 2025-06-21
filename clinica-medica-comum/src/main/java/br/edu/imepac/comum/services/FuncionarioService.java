package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FuncionarioService {

    private final ModelMapper modelMapper;
    private final IFuncionarioRepository funcionarioRepository;
    private final PerfilService perfilService;

    public FuncionarioService(ModelMapper modelMapper,
                              IFuncionarioRepository funcionarioRepository,
                              PerfilService perfilService) {
        this.modelMapper = modelMapper;
        this.funcionarioRepository = funcionarioRepository;
        this.perfilService = perfilService;
    }

    public FuncionarioDto adicionarFuncionario(FuncionarioRequest funcionarioRequest) {
        log.info("Cadastro de funcionário - service: {}", funcionarioRequest);

        Funcionario funcionario = modelMapper.map(funcionarioRequest, Funcionario.class);

        // Gera o perfil automaticamente baseado no tipo do funcionário
        Perfil perfil = perfilService.gerarPerfilPorTipo(funcionario.getTipoFuncionario());
        funcionario.setPerfil(perfil);

        funcionario = funcionarioRepository.save(funcionario);
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public FuncionarioDto atualizarFuncionario(Long id, FuncionarioDto funcionarioDto) {
        log.info("Atualizando funcionário com ID: {}", id);
        Funcionario funcionarioExistente = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));

        modelMapper.map(funcionarioDto, funcionarioExistente);

        Funcionario funcionarioAtualizado = funcionarioRepository.save(funcionarioExistente);
        return modelMapper.map(funcionarioAtualizado, FuncionarioDto.class);
    }

    public void removerFuncionario(Long id) {
        log.info("Removendo funcionário com ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        funcionarioRepository.delete(funcionario);
    }

    public FuncionarioDto buscarFuncionarioPorId(Long id) {
        log.info("Buscando funcionário com ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
        return modelMapper.map(funcionario, FuncionarioDto.class);
    }

    public List<FuncionarioDto> listarFuncionarios() {
        log.info("Listando todos os funcionários");
        List<Funcionario> funcionarios = funcionarioRepository.findAll();
        return funcionarios.stream()
                .map(funcionario -> modelMapper.map(funcionario, FuncionarioDto.class))
                .toList();
    }
}
