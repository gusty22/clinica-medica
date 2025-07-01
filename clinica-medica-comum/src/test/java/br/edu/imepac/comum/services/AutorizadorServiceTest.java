package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AutorizadorServiceTest {

    private final AutorizadorService service = new AutorizadorService();

    @Test
    void testCheckPermission_shouldReturnTrueForAdmin() {
        PerfilDto admin = new PerfilDto();
        admin.setNome("ADMINISTRADOR");

        assertTrue(service.checkPermission(admin, "qualquerAcao"));
    }

    @Test
    void testCheckPermission_shouldReturnFalseForNullInputs() {
        assertFalse(service.checkPermission(null, "acao"));
        assertFalse(service.checkPermission(new PerfilDto(), null));
        assertFalse(service.checkPermission(new PerfilDto(), "   "));
    }

    @Test
    void testCheckPermission_shouldReturnPermissionValue() throws NoSuchFieldException, IllegalAccessException {
        PerfilDto perfil = new PerfilDto();
        perfil.setNome("USUARIO");
        String campo = "lerFuncionario";

        var field = PerfilDto.class.getDeclaredField(campo);
        field.setAccessible(true);

        field.setBoolean(perfil, true);
        assertTrue(service.checkPermission(perfil, campo));

        field.setBoolean(perfil, false);
        assertFalse(service.checkPermission(perfil, campo));
    }

    @Test
    void testCheckPermission_shouldReturnFalseForUnknownField() {
        PerfilDto perfil = new PerfilDto();
        perfil.setNome("USUARIO");

        assertFalse(service.checkPermission(perfil, "campoInexistente"));
    }
}
