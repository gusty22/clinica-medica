package br.edu.imepac.comum.services;

import br.edu.imepac.comum.dtos.perfil.PerfilDto;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field; // Import para reflexão

@Service
public class AutorizadorService {
    public boolean checkPermission(PerfilDto perfil, String acao) {
        if (perfil == null || acao == null || acao.trim().isEmpty()) {
            return false;
        }

        if ("ADMINISTRADOR".equalsIgnoreCase(perfil.getNome())) {
            return true;
        }

        try {
            Field permissionField = PerfilDto.class.getDeclaredField(acao);
            permissionField.setAccessible(true);
            return permissionField.getBoolean(perfil);
        } catch (NoSuchFieldException e) {
            System.err.println("Ação de permissão desconhecida no PerfilDto: " + acao);
            return false;
        } catch (IllegalAccessException e) {
            System.err.println("Erro de acesso ao campo de permissão no PerfilDto: " + acao + " - " + e.getMessage());
            return false;
        }
    }
}