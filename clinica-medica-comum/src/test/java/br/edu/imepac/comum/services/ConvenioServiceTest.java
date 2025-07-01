package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.convenio.ConvenioDto;
import br.edu.imepac.comum.dtos.convenio.ConvenioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Convenio;
import br.edu.imepac.comum.repositories.IConvenioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConvenioServiceTest {

    private IConvenioRepository convenioRepository;
    private ModelMapper modelMapper;
    private ConvenioService convenioService;

    @BeforeEach
    void setUp() {
        convenioRepository = mock(IConvenioRepository.class);
        modelMapper = new ModelMapper();
        convenioService = new ConvenioService(modelMapper, convenioRepository);
    }

    @Test
    void adicionarConvenio_deveSalvarEMapearCorretamente() {
        // Arrange
        ConvenioRequest request = new ConvenioRequest("Unimed", "Plano de saúde regional");

        Convenio convenioSalvo = new Convenio();
        convenioSalvo.setId(1L);
        convenioSalvo.setNome("Unimed");
        convenioSalvo.setDescricao("Plano de saúde regional");

        when(convenioRepository.save(any(Convenio.class))).thenReturn(convenioSalvo);

        // Act
        ConvenioDto result = convenioService.adicionarConvenio(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("Unimed");
        verify(convenioRepository, times(1)).save(any(Convenio.class));
    }

    @Test
    void atualizarConvenio_quandoExiste_deveAtualizar() {
        // Arrange
        Convenio existente = new Convenio();
        existente.setId(1L);
        existente.setNome("Antigo Nome");
        existente.setDescricao("Antiga descrição");

        ConvenioDto dto = new ConvenioDto(1L, "Novo Nome", "Nova descrição");

        when(convenioRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(convenioRepository.save(any(Convenio.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ConvenioDto atualizado = convenioService.atualizarConvenio(1L, dto);

        // Assert
        assertThat(atualizado.getNome()).isEqualTo("Novo Nome");
        assertThat(atualizado.getDescricao()).isEqualTo("Nova descrição");
    }

    @Test
    void buscarConvenioPorId_quandoNaoExiste_deveLancarExcecao() {
        when(convenioRepository.findById(10L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> convenioService.buscarConvenioPorId(10L))
                .isInstanceOf(NotFoundClinicaMedicaException.class)
                .hasMessageContaining("Convênio não encontrado com ID: 10");
    }

    @Test
    void removerConvenio_quandoExiste_deveRemover() {
        Convenio convenio = new Convenio();
        convenio.setId(1L);

        when(convenioRepository.findById(1L)).thenReturn(Optional.of(convenio));

        convenioService.removerConvenio(1L);

        verify(convenioRepository).delete(convenio);
    }

    @Test
    void listarConvenios_deveRetornarTodos() {
        Convenio c1 = new Convenio();
        c1.setId(1L);
        c1.setNome("Unimed");

        Convenio c2 = new Convenio();
        c2.setId(2L);
        c2.setNome("Amil");

        when(convenioRepository.findAll()).thenReturn(List.of(c1, c2));

        List<ConvenioDto> result = convenioService.listarConvenios();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("Unimed");
        assertThat(result.get(1).getNome()).isEqualTo("Amil");
    }
}
