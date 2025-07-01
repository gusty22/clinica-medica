package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.consulta.ConsultaDto;
import br.edu.imepac.comum.dtos.consulta.ConsultaRequest;
import br.edu.imepac.comum.exceptions.NotFoundClinicaMedicaException;
import br.edu.imepac.comum.models.*;
import br.edu.imepac.comum.repositories.IConsultaRepository;
import br.edu.imepac.comum.repositories.IConvenioRepository;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import br.edu.imepac.comum.repositories.IPacienteRepository;
import br.edu.imepac.comum.repositories.IProntuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsultaServiceTest {

    private IConsultaRepository consultaRepository;
    private IFuncionarioRepository funcionarioRepository;
    private IPacienteRepository pacienteRepository;
    private IConvenioRepository convenioRepository;
    private IProntuarioRepository prontuarioRepository;
    private ModelMapper modelMapper;
    private ConsultaService consultaService;

    @BeforeEach
    void setUp() {
        consultaRepository = mock(IConsultaRepository.class);
        funcionarioRepository = mock(IFuncionarioRepository.class);
        pacienteRepository = mock(IPacienteRepository.class);
        convenioRepository = mock(IConvenioRepository.class);
        prontuarioRepository = mock(IProntuarioRepository.class);
        modelMapper = new ModelMapper();
        consultaService = new ConsultaService(
                modelMapper,
                consultaRepository,
                funcionarioRepository,
                pacienteRepository,
                convenioRepository,
                prontuarioRepository
        );
    }

    @Test
    void adicionarConsulta_deveSalvarEMapearCorretamente() {
        ConsultaRequest request = new ConsultaRequest(
                LocalDate.of(2025, 7, 1),
                LocalTime.of(10, 0),
                false,
                true,
                "Dor de cabeça",
                1L, 2L, 3L, 4L
        );

        Funcionario funcionario = new Funcionario();
        funcionario.setId(1L);

        Paciente paciente = new Paciente();
        paciente.setId(2L);

        Convenio convenio = new Convenio();
        convenio.setId(3L);

        Prontuario prontuario = new Prontuario();
        prontuario.setId(4L);

        Consulta consultaSalva = new Consulta();
        consultaSalva.setId(10L);
        consultaSalva.setDataHorario(LocalDateTime.of(2025, 7, 1, 10, 0));
        consultaSalva.setSintomas("Dor de cabeça");
        consultaSalva.setERetorno(false);
        consultaSalva.setEstaAtiva(true);
        consultaSalva.setFuncionario(funcionario);
        consultaSalva.setPaciente(paciente);
        consultaSalva.setConvenio(convenio);
        consultaSalva.setProntuario(prontuario);

        when(funcionarioRepository.findById(1L)).thenReturn(Optional.of(funcionario));
        when(pacienteRepository.findById(2L)).thenReturn(Optional.of(paciente));
        when(convenioRepository.findById(3L)).thenReturn(Optional.of(convenio));
        when(prontuarioRepository.findById(4L)).thenReturn(Optional.of(prontuario));
        when(consultaRepository.save(any(Consulta.class))).thenReturn(consultaSalva);

        ConsultaDto result = consultaService.adicionarConsulta(request);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getFuncionario()).isNotNull();
        assertThat(result.getPaciente()).isNotNull();
        assertThat(result.getProntuario()).isNotNull();

        verify(consultaRepository, times(1)).save(any(Consulta.class));
    }

    @Test
    void buscarConsultaPorId_quandoNaoExiste_deveLancarExcecao() {
        when(consultaRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> consultaService.buscarConsultaPorId(100L))
                .isInstanceOf(NotFoundClinicaMedicaException.class)
                .hasMessageContaining("Consulta não encontrada");
    }

    @Test
    void listarConsultas_deveRetornarListaDeDtos() {
        Consulta c1 = new Consulta();
        c1.setId(1L);
        c1.setDataHorario(LocalDateTime.now());
        c1.setFuncionario(new Funcionario());
        c1.setPaciente(new Paciente());
        c1.setConvenio(new Convenio());

        Consulta c2 = new Consulta();
        c2.setId(2L);
        c2.setDataHorario(LocalDateTime.now());
        c2.setFuncionario(new Funcionario());
        c2.setPaciente(new Paciente());
        c2.setConvenio(new Convenio());

        when(consultaRepository.findAll()).thenReturn(List.of(c1, c2));

        List<ConsultaDto> result = consultaService.listarConsultas();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
    }

    @Test
    void removerConsulta_deveDeletarSeExistir() {
        Consulta consulta = new Consulta();
        consulta.setId(1L);

        when(consultaRepository.findById(1L)).thenReturn(Optional.of(consulta));

        consultaService.cancelarConsulta(1L);

        verify(consultaRepository, times(1)).delete(consulta);
    }
}
