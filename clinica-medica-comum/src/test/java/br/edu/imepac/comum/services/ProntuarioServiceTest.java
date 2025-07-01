package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.prontuario.ProntuarioDto;
import br.edu.imepac.comum.dtos.prontuario.ProntuarioRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.models.Prontuario;
import br.edu.imepac.comum.repositories.IProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProntuarioServiceTest {

    private IProntuarioRepository prontuarioRepository;
    private ModelMapper modelMapper;
    private ProntuarioService prontuarioService;

    @BeforeEach
    void setUp() {
        prontuarioRepository = mock(IProntuarioRepository.class);
        modelMapper = new ModelMapper();
        prontuarioService = new ProntuarioService(modelMapper, prontuarioRepository);
    }

    @Test
    void adicionarProntuario_deveSalvarERetornarDto() {
        ProntuarioRequest request = new ProntuarioRequest("Dipirona 500mg", "Exame de sangue", "Paciente com febre", 1L);

        Prontuario entity = new Prontuario();
        entity.setId(1L);
        entity.setReceituario("Dipirona 500mg");
        entity.setExames("Exame de sangue");
        entity.setObservacoes("Paciente com febre");
        entity.setPaciente(new Paciente());

        when(prontuarioRepository.save(any(Prontuario.class))).thenReturn(entity);

        ProntuarioDto result = prontuarioService.adicionarProntuario(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getReceituario()).isEqualTo("Dipirona 500mg");
        verify(prontuarioRepository, times(1)).save(any(Prontuario.class));
    }

    @Test
    void buscarProntuarioPorId_quandoExiste_deveRetornarDto() {
        Prontuario prontuario = new Prontuario();
        prontuario.setId(1L);
        prontuario.setReceituario("Antibiótico");
        prontuario.setPaciente(new Paciente());

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));

        ProntuarioDto resultado = prontuarioService.buscarProntuarioPorId(1L);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getReceituario()).isEqualTo("Antibiótico");
    }

    @Test
    void buscarProntuarioPorId_quandoNaoExiste_deveLancarExcecao() {
        when(prontuarioRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> prontuarioService.buscarProntuarioPorId(999L))
                .isInstanceOf(NotFoundClinicaMedicaException.class)
                .hasMessageContaining("Prontuário não encontrado");
    }

    @Test
    void listarProntuarios_deveRetornarListaDeDtos() {
        Prontuario p1 = new Prontuario();
        p1.setId(1L);
        p1.setReceituario("Receita A");
        p1.setPaciente(new Paciente());

        Prontuario p2 = new Prontuario();
        p2.setId(2L);
        p2.setReceituario("Receita B");
        p2.setPaciente(new Paciente());

        when(prontuarioRepository.findAll()).thenReturn(List.of(p1, p2));

        List<ProntuarioDto> lista = prontuarioService.listarProntuarios();

        assertThat(lista).hasSize(2);
        assertThat(lista.get(0).getReceituario()).isEqualTo("Receita A");
        assertThat(lista.get(1).getReceituario()).isEqualTo("Receita B");
    }

    @Test
    void removerProntuario_quandoExiste_deveRemover() {
        Prontuario prontuario = new Prontuario();
        prontuario.setId(1L);

        when(prontuarioRepository.findById(1L)).thenReturn(Optional.of(prontuario));

        prontuarioService.removerProntuario(1L);

        verify(prontuarioRepository, times(1)).delete(prontuario);
    }

    @Test
    void atualizarProntuario_quandoExiste_deveAtualizarERetornarDto() {
        Long id = 1L;
        Prontuario prontuarioExistente = new Prontuario();
        prontuarioExistente.setId(id);
        prontuarioExistente.setReceituario("Velha receita");

        ProntuarioDto dtoAtualizado = new ProntuarioDto();
        dtoAtualizado.setId(id);
        dtoAtualizado.setReceituario("Nova receita");

        when(prontuarioRepository.findById(id)).thenReturn(Optional.of(prontuarioExistente));
        when(prontuarioRepository.save(any(Prontuario.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProntuarioDto resultado = prontuarioService.atualizarProntuario(id, dtoAtualizado);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getReceituario()).isEqualTo("Nova receita");
    }
}
