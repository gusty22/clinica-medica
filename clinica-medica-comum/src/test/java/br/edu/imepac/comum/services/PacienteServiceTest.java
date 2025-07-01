package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.paciente.PacienteDto;
import br.edu.imepac.comum.dtos.paciente.PacienteRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.Paciente;
import br.edu.imepac.comum.models.Paciente.Sexo;
import br.edu.imepac.comum.repositories.IPacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
public class PacienteServiceTest {
    private IPacienteRepository pacienteRepository;
    private ModelMapper modelMapper;
    private PacienteService pacienteService;

    @BeforeEach
    void setup() {
        pacienteRepository = mock(IPacienteRepository.class);
        modelMapper = new ModelMapper();
        pacienteService = new PacienteService(modelMapper, pacienteRepository);
    }

    @Test
    void adicionarPaciente_deveSalvarPacienteERetornarDto() {
        PacienteRequest request = new PacienteRequest();
        request.setNome("João Silva");
        request.setIdade(30);
        request.setSexo("MASCULINO");
        request.setCpf("12345678900");
        request.setRua("Rua A");
        request.setNumero("123");
        request.setComplemento("Apto 101");
        request.setBairro("Centro");
        request.setCidade("São Paulo");
        request.setEstado("SP");
        request.setContato("11999999999");
        request.setEmail("joao@email.com");
        request.setDataNascimento(LocalDate.of(1993, 1, 1));
        request.setConvenioId(1L);

        Paciente pacienteSalvo = new Paciente();
        pacienteSalvo.setId(1L);
        pacienteSalvo.setNome(request.getNome());
        pacienteSalvo.setIdade(request.getIdade());
        pacienteSalvo.setSexo(Sexo.valueOf(request.getSexo()));

        when(pacienteRepository.save(any(Paciente.class))).thenReturn(pacienteSalvo);

        PacienteDto result = pacienteService.adicionarPaciente(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getNome()).isEqualTo(request.getNome());
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    void buscarPacientePorId_quandoPacienteExiste_deveRetornarDto() {
        Long id = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(id);
        paciente.setNome("Maria");
        paciente.setSexo(Sexo.FEMININO);

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        PacienteDto dto = pacienteService.buscarPacientePorId(id);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(id);
        assertThat(dto.getNome()).isEqualTo("Maria");
    }

    @Test
    void buscarPacientePorId_quandoPacienteNaoExiste_deveLancarException() {
        Long id = 999L;
        when(pacienteRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> pacienteService.buscarPacientePorId(id))
                .isInstanceOf(NotFoundClinicaMedicaException.class)
                .hasMessageContaining("Paciente não encontrado");
    }

    @Test
    void removerPaciente_quandoPacienteExiste_deveChamarDelete() {
        Long id = 1L;
        Paciente paciente = new Paciente();
        paciente.setId(id);

        when(pacienteRepository.findById(id)).thenReturn(Optional.of(paciente));

        pacienteService.removerPaciente(id);

        verify(pacienteRepository, times(1)).delete(paciente);
    }

    @Test
    void listarPacientes_deveRetornarListaDeDtos() {
        Paciente paciente1 = new Paciente();
        paciente1.setId(1L);
        paciente1.setNome("Paciente 1");

        Paciente paciente2 = new Paciente();
        paciente2.setId(2L);
        paciente2.setNome("Paciente 2");

        when(pacienteRepository.findAll()).thenReturn(List.of(paciente1, paciente2));

        List<PacienteDto> pacientes = pacienteService.listarPacientes();

        assertThat(pacientes).hasSize(2);
        assertThat(pacientes.get(0).getNome()).isEqualTo("Paciente 1");
        assertThat(pacientes.get(1).getNome()).isEqualTo("Paciente 2");
    }
}
