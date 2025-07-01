package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.funcionario.FuncionarioDto;
import br.edu.imepac.comum.dtos.funcionario.FuncionarioRequest;
import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioServiceTest {

    private FuncionarioService funcionarioService;
    private IFuncionarioRepository funcionarioRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        funcionarioRepository = mock(IFuncionarioRepository.class);
        modelMapper = new ModelMapper();
        funcionarioService = new FuncionarioService(modelMapper, funcionarioRepository);
    }

    @Test
    void adicionarFuncionario_deveSalvarEConverterParaDto() {
        FuncionarioRequest request = new FuncionarioRequest();
        request.setNome("João Silva");
        request.setUsuario("joaosilva");
        request.setSenha("123456");
        request.setIdade(30);
        request.setSexo("M");
        request.setCpf("12345678900");
        request.setRua("Rua A");
        request.setNumero("100");
        request.setComplemento("Apto 1");
        request.setBairro("Centro");
        request.setCidade("Cidade X");
        request.setEstado("Estado Y");
        request.setContato("999999999");
        request.setEmail("joao@example.com");
        request.setDataNascimento(LocalDate.of(1995, 5, 20));
        request.setTipoFuncionario(null); // Ou algum EnumTipoFuncionario válido

        Funcionario funcionarioEntity = modelMapper.map(request, Funcionario.class);
        funcionarioEntity.setId(1L);

        when(funcionarioRepository.save(any(Funcionario.class))).thenReturn(funcionarioEntity);

        FuncionarioDto dto = funcionarioService.adicionarFuncionario(request);

        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("João Silva", dto.getNome());
        verify(funcionarioRepository, times(1)).save(any(Funcionario.class));
    }

    @Test
    void buscarFuncionarioPorId_deveRetornarFuncionarioQuandoExistir() {
        // Arrange
        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("Maria");

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));

        // Act
        FuncionarioDto dto = funcionarioService.buscarFuncionarioPorId(1L);

        // Assert
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("Maria", dto.getNome());
    }

    @Test
    void buscarFuncionarioPorId_deveLancarExceptionQuandoNaoEncontrar() {
        // Arrange
        when(funcionarioRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            funcionarioService.buscarFuncionarioPorId(1L);
        });

        assertTrue(exception.getMessage().contains("Funcionário não encontrado"));
    }
}
