package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import br.edu.imepac.comum.dtos.perfil.PerfilRequest;
import br.edu.imepac.comum.models.Perfil;
import br.edu.imepac.comum.repositories.IPerfilRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PerfilServiceTest {

    private PerfilService perfilService;
    private IPerfilRepository perfilRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        perfilRepository = mock(IPerfilRepository.class);
        modelMapper = new ModelMapper();
        perfilService = new PerfilService(modelMapper, perfilRepository);
    }

    @Test
    void adicionarPerfil_deveSalvarEretornarPerfilDto() {
        // Arrange
        PerfilRequest request = new PerfilRequest();
        request.setNome("Administrador");
        request.setCadastrarFuncionario(true);

        Perfil perfilSalvo = new Perfil();
        perfilSalvo.setId(1);
        perfilSalvo.setNome(request.getNome());
        perfilSalvo.setCadastrarFuncionario(true);

        when(perfilRepository.save(any(Perfil.class))).thenReturn(perfilSalvo);

        // Act
        PerfilDto resultado = perfilService.adicionarPerfil(request);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Administrador");
        assertThat(resultado.isCadastrarFuncionario()).isTrue();
        verify(perfilRepository, times(1)).save(any(Perfil.class));
    }

    @Test
    void buscarPerfilPorId_quandoExiste_deveRetornarPerfilDto() {
        // Arrange
        Perfil perfil = new Perfil();
        perfil.setId(1);
        perfil.setNome("Leitura");

        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));

        // Act
        PerfilDto resultado = perfilService.buscarPerfilPorId(1L);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Leitura");
    }

    @Test
    void buscarPerfilPorId_quandoNaoExiste_deveLancarException() {
        // Arrange
        when(perfilRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> perfilService.buscarPerfilPorId(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Perfil não encontrado");
    }

    @Test
    void listarPerfis_deveRetornarListaDePerfilDtos() {
        // Arrange
        Perfil p1 = new Perfil();
        p1.setId(1);
        p1.setNome("Perfil A");

        Perfil p2 = new Perfil();
        p2.setId(2);
        p2.setNome("Perfil B");

        when(perfilRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<PerfilDto> perfis = perfilService.listarPerfis();

        // Assert
        assertThat(perfis).hasSize(2);
        assertThat(perfis.get(0).getNome()).isEqualTo("Perfil A");
        assertThat(perfis.get(1).getNome()).isEqualTo("Perfil B");
    }

    @Test
    void removerPerfil_deveDeletarQuandoExiste() {
        // Arrange
        Perfil perfil = new Perfil();
        perfil.setId(1);

        when(perfilRepository.findById(1L)).thenReturn(Optional.of(perfil));

        // Act
        perfilService.removerPerfil(1L);

        // Assert
        verify(perfilRepository, times(1)).delete(perfil);
    }

    @Test
    void atualizarPerfil_quandoExiste_deveAtualizarEretornarDto() {
        // Arrange
        Long id = 1L;
        Perfil perfilExistente = new Perfil();
        perfilExistente.setId(1);
        perfilExistente.setNome("Antigo");

        PerfilDto dtoAtualizado = new PerfilDto();
        dtoAtualizado.setId(1);
        dtoAtualizado.setNome("Novo Nome");

        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfilExistente));
        when(perfilRepository.save(any(Perfil.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PerfilDto resultado = perfilService.atualizarPerfil(id, dtoAtualizado);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getNome()).isEqualTo("Novo Nome");
    }
}
