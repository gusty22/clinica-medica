package br.edu.imepac.comum.services;

import br.edu.imepac.comum.models.Funcionario;
import br.edu.imepac.comum.repositories.IFuncionarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticadorServiceTest {

    @Mock
    private IFuncionarioRepository funcionarioRepository;

    @Mock
    private FuncionarioService funcionarioService; // só se usar dentro do service

    @InjectMocks
    private AutenticadorService autenticadorService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAutenticar_whenUserExists_shouldReturnFuncionario() {
        String usuario = "user";
        String senha = "123";
        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario(usuario);
        funcionario.setSenha(senha);

        when(funcionarioRepository.findByUsuarioAndSenha(usuario, senha)).thenReturn(Optional.of(funcionario));

        Optional<Funcionario> result = autenticadorService.autenticar(usuario, senha);

        assertTrue(result.isPresent());
        assertEquals(usuario, result.get().getUsuario());
    }

    @Test
    void testAutenticar_whenUserNotExists_shouldReturnEmpty() {
        when(funcionarioRepository.findByUsuarioAndSenha(anyString(), anyString())).thenReturn(Optional.empty());

        Optional<Funcionario> result = autenticadorService.autenticar("inexistente", "senha");

        assertFalse(result.isPresent());
    }
}
